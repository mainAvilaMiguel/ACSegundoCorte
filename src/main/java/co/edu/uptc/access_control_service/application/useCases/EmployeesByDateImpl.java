package co.edu.uptc.access_control_service.application.useCases;

import java.util.List;
import java.util.Map;

import co.edu.uptc.access_control_service.domain.ports.in.EmployeesByDateUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;

public class EmployeesByDateImpl implements EmployeesByDateUseCase {
    private final AccessControlRepositoryPort accessControlRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public EmployeesByDateImpl(AccessControlRepositoryPort accessControlRepositoryPort,
            EventPublisherPort evenPublisherPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
        this.eventPublisherPort = evenPublisherPort;
    }
    @Override
    public List<Map<String, String>> employessByDate(String dateInput) {
        List<Map<String, String>> employees = accessControlRepositoryPort.employessByDate(dateInput);
        eventPublisherPort.sendAllEmployeesEvent(dateInput);
        return employees;
    }
    
}
