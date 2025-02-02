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
 * FailureData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-03T09:43:42.413646-03:00[America/Sao_Paulo]", comments = "Generator version: 7.11.0")
public class FailureData {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime timestamp;

  private @Nullable Integer machineId;

  private @Nullable String failureType;

  public FailureData timestamp(OffsetDateTime timestamp) {
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

  public FailureData machineId(Integer machineId) {
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

  public FailureData failureType(String failureType) {
    this.failureType = failureType;
    return this;
  }

  /**
   * Get failureType
   * @return failureType
   */
  
  @Schema(name = "failureType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("failureType")
  public String getFailureType() {
    return failureType;
  }

  public void setFailureType(String failureType) {
    this.failureType = failureType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FailureData failureData = (FailureData) o;
    return Objects.equals(this.timestamp, failureData.timestamp) &&
        Objects.equals(this.machineId, failureData.machineId) &&
        Objects.equals(this.failureType, failureData.failureType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, machineId, failureType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FailureData {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    machineId: ").append(toIndentedString(machineId)).append("\n");
    sb.append("    failureType: ").append(toIndentedString(failureType)).append("\n");
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

