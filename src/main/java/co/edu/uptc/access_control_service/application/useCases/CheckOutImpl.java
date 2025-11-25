package co.edu.uptc.access_control_service.application.useCases;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class CheckOutImpl implements CheckOutUseCase {

    private final AccessControlRepositoryPort accessControlRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public CheckOutImpl(AccessControlRepositoryPort accessControlRepositoryPort,
            EventPublisherPort eventPublisherPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    public AccessControl checkOut(String employeeId, String accessDate) {
        EmployeeId idVO = new EmployeeId(employeeId);
        AccessDateTime dateVO = new AccessDateTime(accessDate);
        int totalAccess = accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
        if (totalAccess % 2 == 0) {
            eventPublisherPort.sendUserCheckOutEvent(employeeId, accessDate);
            throw new IllegalStateException("El empleado no ha registrado ingreso aun, no puede registrarse la salida");
        }
        AccessControl saved =accessControlRepositoryPort.saveAccess(new AccessControl(idVO, dateVO));
        return saved;
    }

    @Override
    public boolean validCheckOut(String employeeId) {
        int totalAccess = accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
        if (totalAccess % 2 == 0) {
            return false;
        }
        return true;
    }

}
