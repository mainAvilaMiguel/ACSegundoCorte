package co.edu.uptc.access_control_service.infrastructure.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Access")
public class AccessControlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeId;
    private String accessdatetime;

    public AccessControlEntity() {
    }
    public AccessControlEntity(String employeeId, String accessdatetime) {
        this.employeeId = employeeId;
        this.accessdatetime = accessdatetime;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getAccessdatetime() {
        return accessdatetime;
    }
    public void setAccessdatetime(String accessdatetime) {
        this.accessdatetime = accessdatetime;
    }
    
}
