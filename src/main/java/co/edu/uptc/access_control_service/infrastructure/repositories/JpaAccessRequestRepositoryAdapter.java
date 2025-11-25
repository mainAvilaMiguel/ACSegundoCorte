package co.edu.uptc.access_control_service.infrastructure.repositories;

import org.springframework.stereotype.Component;

import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponseRepositoryPort;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessRequestEntity;
import co.edu.uptc.access_control_service.infrastructure.mappers.AccessRequestMapper;
import java.util.Optional;

@Component
public class JpaAccessRequestRepositoryAdapter implements AccessResponseRepositoryPort {
    private final JpaAccessRequestRepository jpaRepository;
    private final AccessRequestMapper mapper;

    public JpaAccessRequestRepositoryAdapter(JpaAccessRequestRepository jpaRepository, 
                                           AccessRequestMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AccessResponse save(AccessResponse accessRequest) {
        AccessRequestEntity entity = mapper.toEntity(accessRequest);
        AccessRequestEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<AccessResponse> findById(Long requestId) {
        return jpaRepository.findByRequestId(requestId)
                .map(mapper::toDomain);
    }

    @Override
    public int countPendingRequestsByEmployeeId(String employeeId) {
        return jpaRepository.countPendingRequestsByEmployeeId(employeeId);
    }
}