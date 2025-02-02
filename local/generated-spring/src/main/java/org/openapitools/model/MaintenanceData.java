package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * MaintenanceData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-03T09:43:42.413646-03:00[America/Sao_Paulo]", comments = "Generator version: 7.11.0")
public class MaintenanceData {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime timestamp;

  private @Nullable Integer machineId;

  private @Nullable String component;

  private @Nullable String maintenanceType;

  public MaintenanceData timestamp(OffsetDateTime timestamp) {
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

  public MaintenanceData machineId(Integer machineId) {
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

  public MaintenanceData component(String component) {
    this.component = component;
    return this;
  }

  /**
   * Get component
   * @return component
   */
  
  @Schema(name = "component", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("component")
  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  public MaintenanceData maintenanceType(String maintenanceType) {
    this.maintenanceType = maintenanceType;
    return this;
  }

  /**
   * Get maintenanceType
   * @return maintenanceType
   */
  
  @Schema(name = "maintenanceType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maintenanceType")
  public String getMaintenanceType() {
    return maintenanceType;
  }

  public void setMaintenanceType(String maintenanceType) {
    this.maintenanceType = maintenanceType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaintenanceData maintenanceData = (MaintenanceData) o;
    return Objects.equals(this.timestamp, maintenanceData.timestamp) &&
        Objects.equals(this.machineId, maintenanceData.machineId) &&
        Objects.equals(this.component, maintenanceData.component) &&
        Objects.equals(this.maintenanceType, maintenanceData.maintenanceType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, machineId, component, maintenanceType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaintenanceData {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    machineId: ").append(toIndentedString(machineId)).append("\n");
    sb.append("    component: ").append(toIndentedString(component)).append("\n");
    sb.append("    maintenanceType: ").append(toIndentedString(maintenanceType)).append("\n");
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

