package co.edu.uptc.access_control_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.edu.uptc.access_control_service.application.services.AccessControlService;
import co.edu.uptc.access_control_service.application.useCases.AccessPerEmployeeImpl;
import co.edu.uptc.access_control_service.application.useCases.AccessRequestUseCaseImpl;
import co.edu.uptc.access_control_service.application.useCases.CheckOutImpl;
import co.edu.uptc.access_control_service.application.useCases.EmployeesByDateImpl;
import co.edu.uptc.access_control_service.application.useCases.RegisterIncomeImpl;
import co.edu.uptc.access_control_service.domain.ports.in.AccessPerEmployeeUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.AccessRequestUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.EmployeesByDateUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;
import co.edu.uptc.access_control_service.domain.ports.out.AccessControlRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponseRepositoryPort;
import co.edu.uptc.access_control_service.domain.ports.out.EventPublisherPort;
import co.edu.uptc.access_control_service.infrastructure.repositories.JpaAccessControlRepositoryAdapter;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccessPerEmployeeUseCase accessPerEmployeeUseCase(AccessControlRepositoryPort accessControlRepositoryPort) {
        return new AccessPerEmployeeImpl(accessControlRepositoryPort);
    }

    @Bean
    public CheckOutUseCase checkOutUseCase(AccessControlRepositoryPort accessControlRepositoryPort, EventPublisherPort eventPublisherPort) {
        return new CheckOutImpl(accessControlRepositoryPort, eventPublisherPort);
    }

    @Bean
    public EmployeesByDateUseCase employeesByDateUseCase(AccessControlRepositoryPort accessControlRepositoryPort) {
        return new EmployeesByDateImpl(accessControlRepositoryPort);
    }

    @Bean
    public RegisterIncomeUseCase registerIncomeUseCase(AccessControlRepositoryPort accessControlRepositoryPort, EventPublisherPort eventPublisherPort) {
        return new RegisterIncomeImpl(accessControlRepositoryPort, eventPublisherPort);
    }

    @Bean
    public AccessRequestUseCase accessRequestUseCase(AccessResponseRepositoryPort accessResponseRepositoryPort, 
                                                   EventPublisherPort eventPublisherPort,
                                                   RegisterIncomeUseCase registerIncomeUseCase,
                                                   CheckOutUseCase checkOutUseCase) {
        return new AccessRequestUseCaseImpl(accessResponseRepositoryPort, eventPublisherPort, registerIncomeUseCase, checkOutUseCase);
    }

    @Bean
    public AccessControlService accessControlService(AccessPerEmployeeUseCase accessPerEmployeeUseCase,
                                                   CheckOutUseCase checkOutUseCase,
                                                   EmployeesByDateUseCase employeesByDateUseCase,
                                                   RegisterIncomeUseCase registerIncomeUseCase,
                                                   AccessRequestUseCase accessRequestUseCase) {
        return new AccessControlService(
            accessPerEmployeeUseCase,
            checkOutUseCase,
            employeesByDateUseCase,
            registerIncomeUseCase,
            accessRequestUseCase
        );
    }

    @Bean
    public AccessControlRepositoryPort accessControlRepositoryPort(
            JpaAccessControlRepositoryAdapter jpaAccessControlRepositoryAdapter) {
        return jpaAccessControlRepositoryAdapter;
    }
}