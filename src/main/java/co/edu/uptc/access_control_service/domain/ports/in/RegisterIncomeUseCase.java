package co.edu.uptc.access_control_service.domain.ports.in;

import co.edu.uptc.access_control_service.domain.models.AccessControl;

public interface RegisterIncomeUseCase {
    AccessControl registerIncome(String employeeId, String accessDate);
    boolean validIncome(String employeeId);
}
