package org.openapitools.api;

import org.openapitools.model.*;
import org.openapitools.service.InfluxDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping("${openapi.voith.base-path:}")
public class ApiApiController implements ApiApi {

    private final InfluxDBService influxDBService;

    @Autowired
    public ApiApiController(InfluxDBService influxDBService) {
        this.influxDBService = influxDBService;
    }

    @GetMapping("/api/errors")
    public ResponseEntity<List<ErrorData>> getErrorsByTimeRange(
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) {

        return ResponseEntity.ok(influxDBService.getErrors(start, end));
    }


    @GetMapping("/api/failures")
    public ResponseEntity<List<FailureData>> getFailuresByTimeRange(
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) {

        return ResponseEntity.ok(influxDBService.getFailures(start, end));
    }


    /**
     * Retrieves all machine data from InfluxDB.
     */
    @GetMapping("/api/machines")
    public ResponseEntity<List<MachineData>> apiMachinesGet() {
        List<MachineData> machines = influxDBService.getAllMachines();
        return ResponseEntity.ok(machines);
    }

    @GetMapping("/api/maintenance")
    public ResponseEntity<List<MaintenanceData>> getMaintenanceByTimeRange(
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) {

        return ResponseEntity.ok(influxDBService.getMaintenanceRecords(start, end));
    }


    @GetMapping("/api/telemetry")
    public ResponseEntity<List<TelemetryData>> getTelemetryByTimeRange(
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) {

        return ResponseEntity.ok(influxDBService.getTelemetryData(start, end));
    }

}
