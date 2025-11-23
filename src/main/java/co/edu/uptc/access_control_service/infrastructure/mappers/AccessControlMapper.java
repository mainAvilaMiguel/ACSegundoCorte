package co.edu.uptc.access_control_service.infrastructure.mappers;

import java.util.Map;

import org.springframework.stereotype.Component;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.valueobjects.AccessDateTime;
import co.edu.uptc.access_control_service.domain.valueobjects.EmployeeId;
import co.edu.uptc.access_control_service.infrastructure.dto.AccessPerEmployeeDTO;
import co.edu.uptc.access_control_service.infrastructure.dto.EmployeesByDateDTO;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessControlEntity;

@Component
public class AccessControlMapper {

    public AccessControlEntity toEntity(AccessControl accessControl) {
        return new AccessControlEntity(accessControl.getEmployeeId().getValue(),
                accessControl.getAccessDateTime().getValue());
    }

    public AccessControl toDomain(AccessControlEntity entity) {
        return new AccessControl(
                new EmployeeId(entity.getEmployeeId()),
                new AccessDateTime(entity.getAccessdatetime()));
    }

    public AccessControl toDomain(AccessPerEmployeeDTO dto) {
        String rawDateTime = dto.getDate() + "T" + dto.getHour();
        return new AccessControl(
                new EmployeeId(dto.getEmployeeId()),
                new AccessDateTime(rawDateTime));
    }

    public Map<String, String> toMap(EmployeesByDateDTO dto) {
        return Map.of(
                "employeeId", dto.getEmployeeId(),
                "hour", dto.getHour());
    }
}
