package co.edu.uptc.access_control_service.application.useCases;
import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class RegisterIncomeImpl implements RegisterIncomeUseCase{
    private final AccessControlRepositoryPort accessControlRepositoryPort;

    public RegisterIncomeImpl(AccessControlRepositoryPort accessControlRepositoryPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
    }
    @Override
    public AccessControl registerIncome(String employeeId, String accessDate) {
       EmployeeId idVO = new EmployeeId(employeeId);
        AccessDateTime dateVO = new AccessDateTime(accessDate);
         int totalAccess = accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
        if (totalAccess % 2 != 0) {
            throw new IllegalStateException("El empleado no puede entrar porque ya ha entrado");
        }
        return accessControlRepositoryPort.saveAccess(new AccessControl(idVO, dateVO));
    }
   
}
