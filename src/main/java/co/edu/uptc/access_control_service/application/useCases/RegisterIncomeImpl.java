package co.edu.uptc.access_control_service.application.useCases;
import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class RegisterIncomeImpl implements RegisterIncomeUseCase{
    private final AccessControlRepositoryPort accessControlRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public RegisterIncomeImpl(AccessControlRepositoryPort accessControlRepositoryPort,
                              EventPublisherPort eventPublisherPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }
    @Override
    public AccessControl registerIncome(String employeeId, String accessDate) {
       EmployeeId idVO = new EmployeeId(employeeId);
        AccessDateTime dateVO = new AccessDateTime(accessDate);
         int totalAccess = accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
        if (totalAccess % 2 != 0) {
            eventPublisherPort.sendUserCheckInEvent(employeeId, accessDate);
            throw new IllegalStateException("El empleado no puede entrar porque ya ha entrado");
        }
        AccessControl saved =accessControlRepositoryPort.saveAccess(new AccessControl(idVO, dateVO));
        return saved;
    }
   
}
