package co.edu.uptc.access_control_service.domain.ports.out;

public interface  EventPublisherPort {
    void sendUserCheckInEvent(String employeeId, String accessdatetime);
    void sendUserCheckOutEvent(String employeeId, String accessdatetime);
    void sendAllEmployeesEvent(String accessdatetime);
    void sendAccessPerEmployeeEvent(String employeId, String startDate, String endDate);
}
