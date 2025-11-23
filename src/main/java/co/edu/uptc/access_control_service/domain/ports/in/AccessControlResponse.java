package co.edu.uptc.access_control_service.domain.ports.in;

import co.edu.uptc.access_control_service.domain.models.AccessControl;

public class AccessControlResponse {
    private String employeeId;
    private String accessdatetime;

    public AccessControlResponse(String employeeId, String accessdatetime) {
        this.employeeId = employeeId;
        this.accessdatetime = accessdatetime;
    }
    public AccessControlResponse() {
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public String getAccessdatetime() {
        return accessdatetime;
    }
    public static AccessControlResponse fromDomain(AccessControl domain) {
        return new AccessControlResponse(
            domain.getEmployeeId().getValue(),  
            domain.getAccessDateTime().getValue()
        );
    }

}
