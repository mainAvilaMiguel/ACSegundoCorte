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
    private static final String TOPIC_ACCESS_REQUEST = "access_request_events";
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonUtils jsonUtils;

    public KafkaEventProducerAdapter(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
    }

    @Override
    public void sendUserCheckInEvent(String employeeId, String accessdatetime) {
        Map<String, String> eventPayload = Map.of(
            "employeeId", employeeId,
            "accessdatetime", accessdatetime,
            "eventType", "EMPLOYEE_ALREADY_ENTERED"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_INCOME, json);
        System.out.println("Evento CHECK_IN enviado: " + json);
    }

    @Override
    public void sendUserCheckOutEvent(String employeeId, String accessdatetime) {
        Map<String, String> eventPayload = Map.of(
            "employeeId", employeeId,
            "accessdatetime", accessdatetime,
            "eventType", "EMPLOYEE_ALREADY_LEFT"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_CHECKOUT, json);
        System.out.println("Evento CHECK_OUT enviado: " + json);
    }

    @Override
    public void sendAccessRequestEvent(Long requestId, String employeeId, String accessdatetime, String requestType) {
        Map<String, Object> eventPayload = Map.of(
            "requestId", requestId,
            "employeeId", employeeId,
            "accessdatetime", accessdatetime,
            "requestType", requestType,
            "eventType", "ACCESS_REQUEST_CREATED"
        );
        String json = jsonUtils.toJson(eventPayload);
        kafkaTemplate.send(TOPIC_ACCESS_REQUEST, json);
        System.out.println("Evento ACCESS_REQUEST enviado: " + json);
    }
}