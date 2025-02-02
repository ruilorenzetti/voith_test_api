package com.voith.ingestion.model;

import lombok.Data;

@Data
public class FailureData {
    private String timestamp;
    private int machineId;
    private String failureType;
}
