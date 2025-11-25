package co.edu.uptc.access_control_service.infrastructure.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessRequestEntity;
import java.util.Optional;

public interface JpaAccessRequestRepository extends JpaRepository<AccessRequestEntity, Long> {
    Optional<AccessRequestEntity> findByRequestId(Long requestId);
    
    @Query("SELECT COUNT(a) FROM AccessRequestEntity a WHERE a.employeeId = :employeeId AND a.status = 'PENDING'")
    int countPendingRequestsByEmployeeId(@Param("employeeId") String employeeId);
}