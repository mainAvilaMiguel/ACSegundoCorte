package co.edu.uptc.access_control_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.uptc.access_control_service.application.services.AccessControlService;
import co.edu.uptc.access_control_service.application.useCases.AccessPerEmployeeImpl;
import co.edu.uptc.access_control_service.application.useCases.CheckOutImpl;
import co.edu.uptc.access_control_service.application.useCases.EmployeesByDateImpl;
import co.edu.uptc.access_control_service.application.useCases.RegisterIncomeImpl;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.infrastructure.repositories.JpaAccessControlRepositoryAdapter;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccessControlService accessControlService(AccessControlRepositoryPort accessControlRepositoryPort,
            EventPublisherPort eventPublisherPort) {
        return new AccessControlService(new AccessPerEmployeeImpl(accessControlRepositoryPort, eventPublisherPort),
                new CheckOutImpl(accessControlRepositoryPort, eventPublisherPort),
                new EmployeesByDateImpl(accessControlRepositoryPort, eventPublisherPort),
                new RegisterIncomeImpl(accessControlRepositoryPort, eventPublisherPort));
    }

    @Bean
    public AccessControlRepositoryPort accessControlRepositoryPort(
            JpaAccessControlRepositoryAdapter jpaAccessControlRepositoryAdapter) {
        return jpaAccessControlRepositoryAdapter;
    }
}
