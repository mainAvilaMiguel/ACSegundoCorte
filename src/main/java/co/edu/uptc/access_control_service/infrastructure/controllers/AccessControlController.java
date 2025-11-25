package co.edu.uptc.access_control_service.infrastructure.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uptc.access_control_service.application.services.AccessControlService;
import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.AccessControlResponse;
import co.edu.uptc.access_control_service.domain.ports.out.RequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;

@RestController
@RequestMapping("/access")
@Tag(name = "Access Control", description = "Control de registros de acceso de empleados")
public class AccessControlController {

   @Autowired
   private final AccessControlService accessControlService;

   public AccessControlController(AccessControlService accessControlService) {
      this.accessControlService = accessControlService;
   }

   // ======================================================
   // 1. CHECK-IN
   // ======================================================
   @Operation(
         summary = "Registrar entrada",
         description = "Registra un evento de ENTRADA (INCOME) para un empleado"
   )
   @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Entrada registrada correctamente"),
         @ApiResponse(responseCode = "400", description = "Empleado inválido")
   })
   @PostMapping("/usercheckin/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckIn(
         @PathVariable @Schema(description = "Documento del empleado", example = "10203040") 
         String employeeId) {
      try {
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Petición de acceso registrada exitosamente",
               "data", accessControlService.createAccessRequest(
                     employeeId,
                     LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                     "INCOME")
         ));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()
         ));
      }
   }

   // ======================================================
   // 2. CHECK-OUT
   // ======================================================
   @Operation(
         summary = "Registrar salida",
         description = "Registra un evento de SALIDA (OUTCOME) para un empleado"
   )
   @PostMapping("/usercheckout/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckOut(
         @PathVariable @Schema(description = "Documento del empleado", example = "10203040") 
         String employeeId) {
      try {
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Petición de salida registrada exitosamente",
               "data", accessControlService.createAccessRequest(
                     employeeId,
                     LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                     "OUTCOME")
         ));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()
         ));
      }
   }

   // ======================================================
   // 3. EMPLEADOS POR FECHA
   // ======================================================
   @Operation(
         summary = "Accesos por fecha",
         description = "Lista todos los accesos de empleados registrados en una fecha específica"
   )
   @GetMapping("/allemployeesbydate/{accessdate}")
   public ResponseEntity<Map<String, Object>> allEmployeesByDate(
         @PathVariable @Schema(description = "Fecha de consulta (YYYY-MM-DD)", example = "2025-01-15") 
         String accessdate) {
      try {
         List<Map<String, String>> employees = accessControlService.employessByDate(accessdate);

         if (employees.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en la fecha indicada"
            ));
         }

         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Informe generado exitosamente",
               "data", employees
         ));
      } catch (Exception e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", "Error en la solicitud: " + e.getMessage()
         ));
      }
   }

   // ======================================================
   // 4. ACCESOS POR RANGO DE FECHAS Y EMPLEADO
   // ======================================================
   @Operation(
         summary = "Accesos por rango de fechas",
         description = "Obtiene los accesos realizados por un empleado dentro de un rango de fechas"
   )
   @GetMapping("/employeebydates/{startDate}/{endDate}/{employeeId}")
   public ResponseEntity<Map<String, Object>> employeeByDates(
         @PathVariable @Schema(description = "Fecha de inicio", example = "2025-01-01") String startDate,
         @PathVariable @Schema(description = "Fecha de fin", example = "2025-01-31") String endDate,
         @PathVariable @Schema(description = "Documento del empleado", example = "10203040") String employeeId) {

      try {
         List<AccessControl> domainAccessControls =
               accessControlService.accessPerEmployee(employeeId, startDate, endDate);

         if (domainAccessControls.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en el rango indicado"
            ));
         }

         List<AccessControlResponse> responseList = domainAccessControls
               .stream()
               .map(AccessControlResponse::fromDomain)
               .toList();

         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Informe generado exitosamente",
               "data", responseList
         ));

      } catch (IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()
         ));
      }
   }

   // ======================================================
   // 5. CONSULTAR ESTADO DE SOLICITUD
   // ======================================================
   @Operation(
         summary = "Consultar estado de solicitud",
         description = "Consulta si una solicitud de acceso fue aceptada, rechazada o está en proceso"
   )
   @GetMapping("/request/{requestId}")
   public ResponseEntity<Map<String, Object>> getRequestStatus(
         @PathVariable @Schema(description = "ID de la solicitud", example = "1001") 
         Long requestId) {
      try {
         RequestResponse response = accessControlService.getAccessRequestById(requestId);

         if (response.getStatus().equals("REJECTED")) {
            return ResponseEntity.ok(Map.of(
                  "success", false,
                  "message", "El empleado no existe o se encuentra desactivado",
                  "data", response
            ));
         }

         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Operación realizada correctamente",
               "data", response
         ));

      } catch (IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()
         ));
      }
   }
}