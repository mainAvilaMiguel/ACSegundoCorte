package co.edu.uptc.access_control_service.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_requests")
public class AccessRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    
    private String employeeId;
    private String accessdatetime;
    private String requestType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccessRequestEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public AccessRequestEntity(String employeeId, String accessdatetime, String requestType) {
        this();
        this.employeeId = employeeId;
        this.accessdatetime = accessdatetime;
        this.requestType = requestType;
        this.status = "PENDING";
    }

    // Getters y Setters
    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getAccessdatetime() { return accessdatetime; }
    public void setAccessdatetime(String accessdatetime) { this.accessdatetime = accessdatetime; }
    
    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}