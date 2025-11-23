package co.edu.uptc.access_control_service.domain.ports.in;

import co.edu.uptc.access_control_service.domain.models.AccessControl;

public interface  CheckOutUseCase {
    AccessControl checkOut(String employeeId, String accessDate);
}
