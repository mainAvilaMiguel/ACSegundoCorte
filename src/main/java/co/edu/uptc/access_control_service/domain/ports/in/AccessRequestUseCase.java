package co.edu.uptc.access_control_service.domain.ports.in;

import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.GenericAccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.RequestResponse;

public interface AccessRequestUseCase {
    GenericAccessResponse createAccessRequest(String employeeId, String accessdatetime, String requestType);
    AccessResponse updateAccessRequestStatus(Long requestId, String status);
    RequestResponse getAccessRequestById(Long requestId);
}