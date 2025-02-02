package com.voith.ingestion.model;

import lombok.Data;

@Data
public class ErrorData {
    private String timestamp;
    private int machineId;
    private String errorType;
}
