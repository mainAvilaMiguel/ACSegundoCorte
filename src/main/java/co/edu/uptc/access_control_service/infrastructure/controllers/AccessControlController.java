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

@RestController
@RequestMapping("/access")
@CrossOrigin(origins = "http://localhost:9093")
public class AccessControlController {
   @Autowired
   private final AccessControlService accessControlService;

   public AccessControlController(AccessControlService accessControlService) {
      this.accessControlService = accessControlService;
   }

   @PostMapping("/usercheckin/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckIn(@PathVariable String employeeId) {
      try {
         accessControlService.registerIncome(employeeId,
               LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Acceso registrado exitosamente"));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

   @PostMapping("/usercheckout/{employeeId}")
   public ResponseEntity<Map<String, Object>> userCheckOut(@PathVariable String employeeId) {
      try {
         accessControlService.checkOut(employeeId, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
         return ResponseEntity.ok(Map.of(
               "success", true,
               "message", "Salida registrada exitosamente"));
      } catch (IllegalStateException | IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

   @GetMapping("/allemployeesbydate/{accessdate}")
   public ResponseEntity<Map<String, Object>> allEmployeesByDate(@PathVariable String accessdate) {
      try {
         List<Map<String, String>> employees = accessControlService.employessByDate(accessdate);
         if (employees.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en la fecha indicada"));
         } else {
            return ResponseEntity.ok(Map.of(
                  "success", true,
                  "message", "Empleados que registraron acceso en la fecha indicada",
                  "data", employees));
         }
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(Map.of(
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
            return ResponseEntity.badRequest().body(Map.of(
                  "success", false,
                  "message", "No se registraron accesos en el rango indicado"));
         } 
         List<AccessControlResponse> responseList = domainAccessControls.stream()
                    .map(AccessControlResponse::fromDomain) 
                    .toList();
            return ResponseEntity.ok(Map.of(
                  "success", true,
                  "message", "Accesos del empleado en el rango indicado",
                  "data", responseList));
         
      } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(Map.of(
               "success", false,
               "message", e.getMessage()));
      }
   }

}
