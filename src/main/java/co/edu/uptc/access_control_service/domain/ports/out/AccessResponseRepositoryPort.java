package co.edu.uptc.access_control_service.domain.ports.out;

import java.util.Optional;

public interface AccessResponseRepositoryPort {
    AccessResponse save(AccessResponse accessRequest);
    Optional<AccessResponse> findById(Long requestId);
    int countPendingRequestsByEmployeeId(String employeeId);
}
