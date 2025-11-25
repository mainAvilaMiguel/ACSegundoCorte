package co.edu.uptc.access_control_service.domain.ports.out;

public class RequestResponse {
    private String status;

    public RequestResponse(String status) {
        this.status = status;
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
    }
    
}