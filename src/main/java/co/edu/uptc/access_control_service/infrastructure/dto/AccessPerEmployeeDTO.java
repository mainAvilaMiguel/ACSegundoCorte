package co.edu.uptc.access_control_service.infrastructure.dto;

public class AccessPerEmployeeDTO {
    private String employeeId;
    private String date;
    private String hour;

    public AccessPerEmployeeDTO() {
    }
    public AccessPerEmployeeDTO(String employeeId, String date, String hour) {
        this.employeeId = employeeId;
        this.date = date;
        this.hour = hour;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
}
