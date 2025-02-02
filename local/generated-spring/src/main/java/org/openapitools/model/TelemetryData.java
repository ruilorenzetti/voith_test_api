package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * TelemetryData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-03T09:43:42.413646-03:00[America/Sao_Paulo]", comments = "Generator version: 7.11.0")
public class TelemetryData {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime timestamp;

  private @Nullable Integer machineId;

  private @Nullable BigDecimal voltage;

  private @Nullable BigDecimal rotation;

  private @Nullable BigDecimal pressure;

  private @Nullable BigDecimal vibration;

  public TelemetryData timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   */
  @Valid 
  @Schema(name = "timestamp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timestamp")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public TelemetryData machineId(Integer machineId) {
    this.machineId = machineId;
    return this;
  }

  /**
   * Get machineId
   * @return machineId
   */
  
  @Schema(name = "machineId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("machineId")
  public Integer getMachineId() {
    return machineId;
  }

  public void setMachineId(Integer machineId) {
    this.machineId = machineId;
  }

  public TelemetryData voltage(BigDecimal voltage) {
    this.voltage = voltage;
    return this;
  }

  /**
   * Get voltage
   * @return voltage
   */
  @Valid 
  @Schema(name = "voltage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("voltage")
  public BigDecimal getVoltage() {
    return voltage;
  }

  public void setVoltage(BigDecimal voltage) {
    this.voltage = voltage;
  }

  public TelemetryData rotation(BigDecimal rotation) {
    this.rotation = rotation;
    return this;
  }

  /**
   * Get rotation
   * @return rotation
   */
  @Valid 
  @Schema(name = "rotation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rotation")
  public BigDecimal getRotation() {
    return rotation;
  }

  public void setRotation(BigDecimal rotation) {
    this.rotation = rotation;
  }

  public TelemetryData pressure(BigDecimal pressure) {
    this.pressure = pressure;
    return this;
  }

  /**
   * Get pressure
   * @return pressure
   */
  @Valid 
  @Schema(name = "pressure", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pressure")
  public BigDecimal getPressure() {
    return pressure;
  }

  public void setPressure(BigDecimal pressure) {
    this.pressure = pressure;
  }

  public TelemetryData vibration(BigDecimal vibration) {
    this.vibration = vibration;
    return this;
  }

  /**
   * Get vibration
   * @return vibration
   */
  @Valid 
  @Schema(name = "vibration", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vibration")
  public BigDecimal getVibration() {
    return vibration;
  }

  public void setVibration(BigDecimal vibration) {
    this.vibration = vibration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TelemetryData telemetryData = (TelemetryData) o;
    return Objects.equals(this.timestamp, telemetryData.timestamp) &&
        Objects.equals(this.machineId, telemetryData.machineId) &&
        Objects.equals(this.voltage, telemetryData.voltage) &&
        Objects.equals(this.rotation, telemetryData.rotation) &&
        Objects.equals(this.pressure, telemetryData.pressure) &&
        Objects.equals(this.vibration, telemetryData.vibration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, machineId, voltage, rotation, pressure, vibration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TelemetryData {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    machineId: ").append(toIndentedString(machineId)).append("\n");
    sb.append("    voltage: ").append(toIndentedString(voltage)).append("\n");
    sb.append("    rotation: ").append(toIndentedString(rotation)).append("\n");
    sb.append("    pressure: ").append(toIndentedString(pressure)).append("\n");
    sb.append("    vibration: ").append(toIndentedString(vibration)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

