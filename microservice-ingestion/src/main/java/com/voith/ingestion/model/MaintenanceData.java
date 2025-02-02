package com.voith.ingestion.model;

import lombok.Data;

@Data
public class MaintenanceData {
    private String timestamp;
    private int machineId;
    private String component;
    private String maintenanceType;
}
