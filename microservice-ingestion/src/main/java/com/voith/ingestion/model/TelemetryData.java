package com.voith.ingestion.model;

import lombok.Data;

@Data
public class TelemetryData {
    private String timestamp;
    private int machineId;
    private double voltage;
    private double rotation;
    private double pressure;
    private double vibration;
}
