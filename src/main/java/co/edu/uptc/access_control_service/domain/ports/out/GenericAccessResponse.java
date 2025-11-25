package co.edu.uptc.access_control_service.domain.ports.out;

public class GenericAccessResponse {
  private Long requestId;

    public GenericAccessResponse(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestId() { return requestId; }
    
}
