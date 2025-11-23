package co.edu.uptc.access_control_service.domain.ports.in;

import java.util.List;

import co.edu.uptc.access_control_service.domain.models.AccessControl;

public interface  AccessPerEmployeeUseCase {
    List<AccessControl> accessPerEmployee(String employeeId,String startDate, String endDate);
    int countAccessByEmployeeId(String employeeId);
}
