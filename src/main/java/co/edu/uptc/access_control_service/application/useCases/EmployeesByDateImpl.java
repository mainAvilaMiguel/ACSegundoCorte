package co.edu.uptc.access_control_service.application.useCases;

import java.util.List;
import java.util.Map;

import co.edu.uptc.access_control_service.domain.ports.in.EmployeesByDateUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;

public class EmployeesByDateImpl implements EmployeesByDateUseCase {
    private final AccessControlRepositoryPort accessControlRepositoryPort;

    public EmployeesByDateImpl(AccessControlRepositoryPort accessControlRepositoryPort) {
        this.accessControlRepositoryPort = accessControlRepositoryPort;
    }
    @Override
    public List<Map<String, String>> employessByDate(String dateInput) {
        return accessControlRepositoryPort.employessByDate(dateInput);
    }
    
}
