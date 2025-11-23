package co.edu.uptc.access_control_service.domain.ports.in;

import java.util.List;
import java.util.Map;

public interface  EmployeesByDateUseCase {
    List<Map<String, String>> employessByDate(String date);
}
