package co.edu.uptc.access_control_service.domain.models;

import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;

public class AccessControl {
    private final EmployeeId employeeId;
    private final AccessDateTime accessdatetime;

    public AccessControl(EmployeeId employeeId, AccessDateTime accessdatetime) {
        this.employeeId = employeeId;
        this.accessdatetime = accessdatetime;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }
    public AccessDateTime getAccessDateTime() {
        return accessdatetime;
    }
    
}
