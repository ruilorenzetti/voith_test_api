package com.voith.ingestion.service;

import com.voith.ingestion.producer.*;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final TelemetryProducer telemetryProducer;
    private final ErrorProducer errorProducer;
    private final FailureProducer failureProducer;
    private final MaintenanceProducer maintenanceProducer;
    private final MachineProducer machineProducer;

    public ProducerService(TelemetryProducer telemetryProducer, ErrorProducer errorProducer,
                           FailureProducer failureProducer, MaintenanceProducer maintenanceProducer,
                           MachineProducer machineProducer) {
        this.telemetryProducer = telemetryProducer;
        this.errorProducer = errorProducer;
        this.failureProducer = failureProducer;
        this.maintenanceProducer = maintenanceProducer;
        this.machineProducer = machineProducer;
    }

    public void startAllProducers(int interval) {
        telemetryProducer.startProducing(interval);
        errorProducer.startProducing(interval);
        failureProducer.startProducing(interval);
        maintenanceProducer.startProducing(interval);
        machineProducer.startProducing(interval);
    }

    public void stopAllProducers() {
        telemetryProducer.stopProducing();
        errorProducer.stopProducing();
        failureProducer.stopProducing();
        maintenanceProducer.stopProducing();
        machineProducer.stopProducing();
    }
}
