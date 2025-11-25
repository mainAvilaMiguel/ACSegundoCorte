package co.edu.uptc.access_control_service.application.useCases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import co.edu.uptc.access_control_service.domain.ports.in.AccessRequestUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponseRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.domain.ports.out.GenericAccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.RequestResponse;

@Service
@Primary
public class AccessRequestUseCaseImpl implements AccessRequestUseCase {
    private final AccessResponseRepositoryPort accessRequestRepository;
    private final EventPublisherPort eventPublisher;
    private final RegisterIncomeUseCase registerIncomeUseCase; 
    private final CheckOutUseCase checkOutUseCase;

    public AccessRequestUseCaseImpl(AccessResponseRepositoryPort accessRequestRepository,
                                   EventPublisherPort eventPublisher, RegisterIncomeUseCase registerIncomeUseCase, CheckOutUseCase checkOutUseCase) {
        this.accessRequestRepository = accessRequestRepository;
        this.eventPublisher = eventPublisher;
        this.registerIncomeUseCase = registerIncomeUseCase;
        this.checkOutUseCase = checkOutUseCase;
    }

    @Override
    public GenericAccessResponse createAccessRequest(String employeeId, String accessdatetime, String requestType) {
        String standardizedType = requestType.toUpperCase();
        
        if(this.validateRequest(employeeId, requestType)) {
            AccessResponse accessRequest = new AccessResponse(employeeId, accessdatetime, standardizedType);
            AccessResponse savedRequest = accessRequestRepository.save(accessRequest);
            GenericAccessResponse request = new GenericAccessResponse(savedRequest.getRequestId());
            
            eventPublisher.sendAccessRequestEvent(
                savedRequest.getRequestId(), 
                employeeId, 
                accessdatetime, 
                standardizedType
            );
            
            return request;
        }
        return null;
    }

    @Override
    public AccessResponse updateAccessRequestStatus(Long requestId, String status) {
        Optional<AccessResponse> optionalRequest = accessRequestRepository.findById(requestId);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Petición no encontrada: " + requestId);
        }

        AccessResponse request = optionalRequest.get();
        request.setStatus(status);
        return accessRequestRepository.save(request);
    }

    @Override
    public RequestResponse getAccessRequestById(Long requestId) {
        AccessResponse initialResponse = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Petición no encontrada: " + requestId));
        return new RequestResponse(initialResponse.getStatus());
    }

    private boolean validateRequest(String employeeId, String requestType) {
        if(requestType.equals("INCOME") && !registerIncomeUseCase.validIncome(employeeId)){
            eventPublisher.sendUserCheckInEvent(employeeId, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            throw new IllegalStateException("El empleado ya ha registrado un ingreso, no puede registrar otro");
        }else if(requestType.equals("OUTCOME") && !checkOutUseCase.validCheckOut(employeeId)){
            eventPublisher.sendUserCheckOutEvent(employeeId, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            throw new IllegalStateException("El empleado no ha registrado ingreso aun, no puede registrar una salida");
        }
        return true;
    }
}