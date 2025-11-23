package co.edu.uptc.access_control_service.application.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.AccessPerEmployeeUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.CheckOutUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.EmployeesByDateUseCase;
import co.edu.uptc.access_control_service.domain.ports.in.RegisterIncomeUseCase;

@Service
public class AccessControlService implements  AccessPerEmployeeUseCase, CheckOutUseCase, EmployeesByDateUseCase, RegisterIncomeUseCase {

    private final AccessPerEmployeeUseCase accessPerEmployeeUseCase;
    private final CheckOutUseCase checkOutUseCase;
    private final EmployeesByDateUseCase employeesByDateUseCase;
    private final RegisterIncomeUseCase registerIncomeUseCase;

    public AccessControlService(AccessPerEmployeeUseCase accessPerEmployeeUseCase,
            CheckOutUseCase checkOutUseCase, EmployeesByDateUseCase employeesByDateUseCase,
            RegisterIncomeUseCase registerIncomeUseCase) {
        this.accessPerEmployeeUseCase = accessPerEmployeeUseCase;
        this.checkOutUseCase = checkOutUseCase;
        this.employeesByDateUseCase = employeesByDateUseCase;
        this.registerIncomeUseCase = registerIncomeUseCase;
    }
    @Override
    public AccessControl registerIncome(String employeeId, String dateTime) {
        return registerIncomeUseCase.registerIncome( employeeId, dateTime);
    }

    @Override
    public AccessControl checkOut(String employeeId, String dateTime) {
       return checkOutUseCase.checkOut( employeeId, dateTime);
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


}
