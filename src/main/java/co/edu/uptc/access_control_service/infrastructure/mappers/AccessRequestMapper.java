package co.edu.uptc.access_control_service.infrastructure.mappers;

import org.springframework.stereotype.Component;

import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessRequestEntity;

@Component
public class AccessRequestMapper {
    public AccessRequestEntity toEntity(AccessResponse domain) {
        if (domain == null) return null;
        
        AccessRequestEntity entity = new AccessRequestEntity();
        entity.setRequestId(domain.getRequestId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setAccessdatetime(domain.getAccessdatetime());
        entity.setRequestType(domain.getRequestType());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        
        return entity;
    }

    public AccessResponse toDomain(AccessRequestEntity entity) {
        if (entity == null) return null;
        
        return new AccessResponse(
            entity.getRequestId(),
            entity.getEmployeeId(),
            entity.getAccessdatetime(),
            entity.getRequestType(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}