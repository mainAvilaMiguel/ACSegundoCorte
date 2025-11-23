package co.edu.uptc.access_control_service.domain.ports.out;

import java.util.List;
import java.util.Map;

import co.edu.uptc.access_control_service.domain.models.AccessControl;

public interface  AccessControlRepositoryPort {
    AccessControl saveAccess(AccessControl accessControl);
    List<AccessControl> accessPerEmployee(String employeeId,String startDate, String endDate);
    List<Map<String, String>> employessByDate(String date);
    int countAccessByEmployeeId(String employeeId);
}
