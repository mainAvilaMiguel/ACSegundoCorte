package co.edu.uptc.access_control_service.domain.ports.out;

public interface EventPublisherPort {
    void sendUserCheckInEvent(String employeeId, String accessdatetime);
    void sendUserCheckOutEvent(String employeeId, String accessdatetime);
    void sendAccessRequestEvent(Long requestId, String employeeId, String accessdatetime, String requestType);
}