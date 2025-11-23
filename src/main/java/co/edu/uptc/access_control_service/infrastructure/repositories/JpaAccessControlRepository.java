package co.edu.uptc.access_control_service.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uptc.access_control_service.infrastructure.dto.AccessPerEmployeeDTO;
import co.edu.uptc.access_control_service.infrastructure.dto.EmployeesByDateDTO;
import co.edu.uptc.access_control_service.infrastructure.entities.AccessControlEntity;

@Repository
public interface JpaAccessControlRepository extends JpaRepository<AccessControlEntity, Long> {
    @Query("SELECT COUNT(a) FROM AccessControlEntity a WHERE a.employeeId = :employeeId")
    int countAccessByEmployeeId(@Param("employeeId") String employeeId);

    @Query("""
                SELECT new co.edu.uptc.access_control_service.infrastructure.dto.EmployeesByDateDTO(
                    a.employeeId,
                    SUBSTRING(a.accessdatetime, 12, 8) 
                )
                FROM AccessControlEntity a
                WHERE a.accessdatetime LIKE CONCAT(:date, '%')
            """)
    List<EmployeesByDateDTO> findEmployeesByDate(@Param("date") String date);

    @Query("""
                SELECT new co.edu.uptc.access_control_service.infrastructure.dto.AccessPerEmployeeDTO(
                    a.employeeId,
                    SUBSTRING(a.accessdatetime, 1, 10), 
                    SUBSTRING(a.accessdatetime, 12, 8)  
                )
                FROM AccessControlEntity a
                WHERE a.employeeId = :employeeId
                  AND a.accessdatetime BETWEEN CONCAT(:startDate, 'T00:00:00')
                                           AND CONCAT(:endDate, 'T23:59:59')
            """)
    List<AccessPerEmployeeDTO> findAccessByEmployeeBetweenDates(
            @Param("employeeId") String employeeId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
}
