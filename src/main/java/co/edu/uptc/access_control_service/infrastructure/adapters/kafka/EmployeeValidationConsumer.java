package co.edu.uptc.access_control_service.infrastructure.adapters.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import co.edu.uptc.access_control_service.domain.ports.in.AccessRequestUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.infrastructure.utils.JsonUtils;
import java.util.Map;

@Component
public class EmployeeValidationConsumer {

    private final AccessRequestUseCase accessRequestUseCase;
    private final RegisterIncomeUseCase registerIncomeUseCase;
    private final CheckOutUseCase checkOutUseCase;
    private final JsonUtils jsonUtils;

    public EmployeeValidationConsumer(AccessRequestUseCase accessRequestUseCase,
                                    RegisterIncomeUseCase registerIncomeUseCase,
                                    CheckOutUseCase checkOutUseCase,
                                    JsonUtils jsonUtils) {
        this.accessRequestUseCase = accessRequestUseCase;
        this.registerIncomeUseCase = registerIncomeUseCase;
        this.checkOutUseCase = checkOutUseCase;
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(topics = "employee_validated_events")
    public void consumeValidationResult(String message) {
        try {
            System.out.println("📥 MENSAJE RECIBIDO EN ACCESS-CONTROL: " + message);
            Map<String, Object> event = jsonUtils.fromJson(message, Map.class);
            
            Long requestId = Long.parseLong(event.get("requestId").toString());
            String employeeId = event.get("employeeId").toString();
            String accessdatetime = event.get("accessdatetime").toString();
            String requestType = event.get("requestType").toString();
            String status = event.get("status").toString();
            
            System.out.println("Procesando resultado de validación para petición: " + requestId);
            
            accessRequestUseCase.updateAccessRequestStatus(requestId, status);
            
            if ("APPROVED".equals(status)) {
                if ("INCOME".equals(requestType)) {
                    registerIncomeUseCase.registerIncome(employeeId, accessdatetime);
                } else if ("OUTCOME".equals(requestType)) {
                    checkOutUseCase.checkOut(employeeId, accessdatetime);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando resultado de validación: " + e.getMessage());
        }
    }
}