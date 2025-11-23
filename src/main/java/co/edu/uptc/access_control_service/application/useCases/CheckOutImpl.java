package co.edu.uptc.access_control_service.application.useCases;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class CheckOutImpl implements CheckOutUseCase {

    private final AccessControlRepositoryPort accessControlRepositoryPort;

    public CheckOutImpl(AccessControlRepositoryPort accessControlRepositoryPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
    }

    @Override
    public AccessControl checkOut(String employeeId, String accessDate) {
        EmployeeId idVO = new EmployeeId(employeeId);
        AccessDateTime dateVO = new AccessDateTime(accessDate);
        int totalAccess = accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
        if (totalAccess % 2 == 0) {
            throw new IllegalStateException("El empleado no ha registrado entrada, no puede salir");
        }
        return accessControlRepositoryPort.saveAccess(new AccessControl(idVO, dateVO));
    }

}
