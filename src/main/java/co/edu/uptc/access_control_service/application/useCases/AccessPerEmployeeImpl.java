package co.edu.uptc.access_control_service.application.useCases;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.AccessPerEmployeeUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class AccessPerEmployeeImpl implements AccessPerEmployeeUseCase {

    private final AccessControlRepositoryPort accessControlRepositoryPort;

    public AccessPerEmployeeImpl(AccessControlRepositoryPort accessControlRepositoryPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
    }

    @Override
    public List<AccessControl> accessPerEmployee(String employeeId, String startDate, String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            if (start.isAfter(end)) {
                throw new IllegalArgumentException("La fecha inicial no puede ser mayor que la fecha final");
            }
            return accessControlRepositoryPort.accessPerEmployee(employeeId, startDate, endDate);
        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException("El formato de fecha debe ser YYYY-MM-DD");
        }
    }

    @Override
    public int countAccessByEmployeeId(String employeeId) {
        new EmployeeId(employeeId);
        return accessControlRepositoryPort.countAccessByEmployeeId(employeeId);
    }

}
