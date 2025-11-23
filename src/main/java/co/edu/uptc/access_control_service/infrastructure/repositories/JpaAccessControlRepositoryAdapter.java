package co.edu.uptc.access_control_service.infrastructure.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.infrastructure.dto.AccessPerEmployeeDTO;
import co.edu.uptc.access_control_service.infrastructure.dto.EmployeesByDateDTO;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessControlEntity;
import co.edu.uptc.access_control_service.infrastructure.mappers.AccessControlMapper;

@Component
public class JpaAccessControlRepositoryAdapter implements AccessControlRepositoryPort {
        private final JpaAccessControlRepository jpaAccessControlRepository;
        private final AccessControlMapper accessControlMapper;

        public JpaAccessControlRepositoryAdapter(JpaAccessControlRepository jpaAccessControlRepository,
                        AccessControlMapper accessControlMapper) {
                this.jpaAccessControlRepository = jpaAccessControlRepository;
                this.accessControlMapper = accessControlMapper;
        }

        @Override
        public AccessControl saveAccess(AccessControl accessControl) {
                AccessControlEntity entity = accessControlMapper.toEntity(accessControl);
                AccessControlEntity savedEntity = jpaAccessControlRepository.save(entity);
                return accessControlMapper.toDomain(savedEntity);
        }

        @Override
        public List<AccessControl> accessPerEmployee(String employeeId, String startDate, String endDate) {
                List<AccessPerEmployeeDTO> employeesAccess = jpaAccessControlRepository
                                .findAccessByEmployeeBetweenDates(employeeId, startDate, endDate);
                return employeesAccess.stream()
                                .map(accessControlMapper::toDomain).toList();
        }

        @Override
        public List<Map<String, String>> employessByDate(String date) {
                List<EmployeesByDateDTO> data = jpaAccessControlRepository.findEmployeesByDate(date);
                return data.stream()
                                .map(accessControlMapper::toMap)
                                .toList();
        }

        @Override
        public int countAccessByEmployeeId(String employeeId) {
                return jpaAccessControlRepository.countAccessByEmployeeId(employeeId);
        }
}
