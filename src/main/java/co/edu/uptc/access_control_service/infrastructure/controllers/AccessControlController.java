package co.edu.uptc.access_control_service.infrastructure.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.access_control_service.application.services.AccessControlService;
import co.edu.uptc.access_control_service.domain.models.AccessControl;
import co.edu.uptc.access_control_service.domain.ports.in.AccessControlResponse;
import co.edu.uptc.access_control_service.domain.ports.out.AccessResponse;

@RestController
@RequestMapping("/access")
@CrossOrigin(origins = {
    "http://localhost:9093",
    "http://localhost:4200"
})
public class AccessControlController {
   @Autowired
   private final AccessControlService accessControlService;

   public AccessControlController(AccessControlService accessControlService) {
      this.accessControlService = accessControlService;
   }

   @PostMapping("/usercheckin/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckIn(@PathVariable String employeeId) {
      try {
         
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Petición de acceso registrada exitosamente",
               "data", accessControlService.createAccessRequest(employeeId,
               LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "INCOME")));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

   @PostMapping("/usercheckout/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckOut(@PathVariable String employeeId) {
      try {
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Petición de salida registrada exitosamente",
               "data", accessControlService.createAccessRequest(employeeId,
               LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "OUTCOME")));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

   @GetMapping("/allemployeesbydate/{accessdate}")
   public ResponseEntity<Map<String, Object>> allEmployeesByDate(@PathVariable String accessdate) {
      try {
         List<Map<String, String>> employees = accessControlService.employessByDate(accessdate);
         if (employees.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en la fecha indicada"));
         } else {
            return ResponseEntity.ok(Map.of(
                  "success", true,
                  "message", "Informe generado exitosamente",
                  "data", employees));
         }
      } catch (Exception e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", "Error en la solicitud: " + e.getMessage()));
      }
   }

   @GetMapping("/employeebydates/{startDate}/{endDate}/{employeeId}")
   public ResponseEntity<Map<String, Object>> employeeByDates(@PathVariable String startDate,
         @PathVariable String endDate,
         @PathVariable String employeeId) {
      try {
         List<AccessControl> domainAccessControls = accessControlService.accessPerEmployee(
               employeeId, startDate, endDate);

         if (domainAccessControls.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en el rango indicado"));
         } 
         List<AccessControlResponse> responseList = domainAccessControls.stream()
                    .map(AccessControlResponse::fromDomain) 
                    .toList();
            return ResponseEntity.ok(Map.of(
                  "success", true,
                  "message", "Informe generado exitosamente",
                  "data", responseList));
         
      } catch (IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

   @GetMapping("/request/{requestId}")
   public ResponseEntity<Map<String, Object>> getRequestStatus(@PathVariable Long requestId) {
      try {
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Operación realizada correctamente",
               "data", accessControlService.getAccessRequestById(requestId)
         ));
      } catch (IllegalArgumentException e) {
         return ResponseEntity.ok(Map.of(
               "success", false,
               "message", e.getMessage()
         ));
      }
}

}
