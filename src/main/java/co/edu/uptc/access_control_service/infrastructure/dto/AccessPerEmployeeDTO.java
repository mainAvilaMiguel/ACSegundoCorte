package co.edu.uptc.access_control_service.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Registro detallado de acceso realizado por un empleado en una fecha específica")
public class AccessPerEmployeeDTO {

    @Schema(
        description = "Identificador único del empleado",
        example = "EMP12345"
    )
    private String employeeId;

    @Schema(
        description = "Fecha en la que se registró el acceso (formato YYYY-MM-DD)",
        example = "2025-01-15"
    )
    private String date;

    @Schema(
        description = "Hora en la que ocurrió el acceso (formato HH:mm:ss)",
        example = "08:30:54"
    )
    private String hour;

    public AccessPerEmployeeDTO() {}

    public AccessPerEmployeeDTO(String employeeId, String date, String hour) {
        this.employeeId = employeeId;
        this.date = date;
        this.hour = hour;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
}
