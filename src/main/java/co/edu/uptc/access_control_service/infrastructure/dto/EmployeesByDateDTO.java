package co.edu.uptc.access_control_service.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Registro que representa un empleado y la hora en que realizó un acceso en una fecha determinada")
public class EmployeesByDateDTO {

    @Schema(
        description = "Identificador único del empleado",
        example = "EMP90821"
    )
    private String employeeId;

    @Schema(
        description = "Hora del acceso realizado por el empleado (formato HH:mm:ss)",
        example = "17:45:12"
    )
    private String hour;

    public EmployeesByDateDTO() {}

    public EmployeesByDateDTO(String employeeId, String hour) {
        this.employeeId = employeeId;
        this.hour = hour;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
}
