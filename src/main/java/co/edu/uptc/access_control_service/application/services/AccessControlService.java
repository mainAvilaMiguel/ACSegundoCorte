package co.edu.uptc.access_control_service.application.services;

import org.springframework.stereotype.Service;
import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.*;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.GenericAccessResponse;
import co.edu.uptc.access_control_service.domain.ports.out.RequestResponse;

import java.util.List;
import java.util.Map;

@Service
public class AccessControlService implements AccessPerEmployeeUseCase, CheckOutUseCase, 
                                            EmployeesByDateUseCase, RegisterIncomeUseCase,
                                            AccessRequestUseCase {

    private final AccessPerEmployeeUseCase accessPerEmployeeUseCase;
    private final CheckOutUseCase checkOutUseCase;
    private final EmployeesByDateUseCase employeesByDateUseCase;
    private final RegisterIncomeUseCase registerIncomeUseCase;
    private final AccessRequestUseCase accessRequestUseCase;

    public AccessControlService(AccessPerEmployeeUseCase accessPerEmployeeUseCase,
                              CheckOutUseCase checkOutUseCase, 
                              EmployeesByDateUseCase employeesByDateUseCase,
                              RegisterIncomeUseCase registerIncomeUseCase,
                              AccessRequestUseCase accessRequestUseCase) {
        this.accessPerEmployeeUseCase = accessPerEmployeeUseCase;
        this.checkOutUseCase = checkOutUseCase;
        this.employeesByDateUseCase = employeesByDateUseCase;
        this.registerIncomeUseCase = registerIncomeUseCase;
        this.accessRequestUseCase = accessRequestUseCase;
    }

    @Override
    public AccessControl registerIncome(String employeeId, String dateTime) {
        return registerIncomeUseCase.registerIncome(employeeId, dateTime);
    }

    @Override
    public AccessControl checkOut(String employeeId, String dateTime) {
        return checkOutUseCase.checkOut(employeeId, dateTime);
    }

    @Override
    public List<AccessControl> accessPerEmployee(String employeeId, String startDate, String endDate) {
        return accessPerEmployeeUseCase.accessPerEmployee(employeeId, startDate, endDate);
    }

    @Override
    public int countAccessByEmployeeId(String employeeId) {
        return accessPerEmployeeUseCase.countAccessByEmployeeId(employeeId);
    }

    @Override
    public List<Map<String, String>> employessByDate(String date) {
        return employeesByDateUseCase.employessByDate(date);
    }

    @Override
    public GenericAccessResponse createAccessRequest(String employeeId, String accessdatetime, String requestType) {
        return accessRequestUseCase.createAccessRequest(employeeId, accessdatetime, requestType);
    }

    @Override
    public AccessResponse updateAccessRequestStatus(Long requestId, String status) {
        return accessRequestUseCase.updateAccessRequestStatus(requestId, status);
    }

    @Override
    public RequestResponse getAccessRequestById(Long requestId) {
        return accessRequestUseCase.getAccessRequestById(requestId);
    }

    @Override
    public boolean validIncome(String employeeId) {
        return registerIncomeUseCase.validIncome(employeeId);
    }

    @Override
    public boolean validCheckOut(String employeeId) {
        return checkOutUseCase.validCheckOut(employeeId);
    }
}