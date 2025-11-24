package co.edu.uptc.access_control_service.infrastructure.adapters.kafka;

import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.infrastructure.utils.JsonUtils;

@Component
public class KafkaEventProducerAdapter implements EventPublisherPort {
    private static final String TOPIC_INCOME = "usercheckin_events";
    private static final String TOPIC_CHECKOUT = "usercheckout_events";
    private static final String TOPIC_ALL_EMPLOYEES = "allemployeesbydate_events";
    private static final String TOPIC_EMPLOYEE_ACCESS = "employeebydates_events";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonUtils jsonUtils;

    public KafkaEventProducerAdapter(
        KafkaTemplate<String, String> kafkaTemplate,
        JsonUtils jsonUtils
    ) {
        this.kafkaTemplate= kafkaTemplate;
        this.jsonUtils = jsonUtils;
    }
    @Override
    public void sendUserCheckInEvent(String employeeId, String accessdatetime) {
        Map<String, String> eventPayload = Map.of(
            "employeeId", employeeId,
            "accessdatetime", accessdatetime,
            "eventType", "CHECK_IN"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_INCOME, json);
        System.out.println("Evento CHECK_IN enviado" + json);
    }
    @Override
    public void sendUserCheckOutEvent(String employeeId, String accessdatetime) {
        Map<String, String> eventPayload = Map.of(
            "employeeId", employeeId,
            "accessdatetime", accessdatetime,
            "eventType", "CHECK_OUT"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_CHECKOUT, json);
        System.out.println("Evento CHECK_OUT enviado" + json);
    }
    @Override
    public void sendAllEmployeesEvent(String accessdatetime) {
        Map<String, String> eventPayload = Map.of(
            "accessdatetime", accessdatetime,
            "eventType", "ALL_EMPLOYEES"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_ALL_EMPLOYEES, json);
        System.out.println("Evento ALL_EMPLOYEES enviado" + json);
    }
    @Override
    public void sendAccessPerEmployeeEvent(String employeId, String startDate, String endDate) {
        Map<String, String> eventPayload = Map.of(
            "employeId", employeId,
            "startDate", startDate,
            "endDate", endDate,
            "eventType", "ACCESS_PER_EMPLOYEE"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_EMPLOYEE_ACCESS, json);
        System.out.println("Evento ACCESS_PER_EMPLOYEE enviado" + json);
    }
}
