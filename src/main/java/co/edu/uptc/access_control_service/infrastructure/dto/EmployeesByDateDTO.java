package co.edu.uptc.access_control_service.infrastructure.dto;

public class EmployeesByDateDTO {
    private String employeeId;
    private String hour;

    public EmployeesByDateDTO() {
    }
    public EmployeesByDateDTO(String employeeId, String hour) {
        this.employeeId = employeeId;
        this.hour = hour;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
}