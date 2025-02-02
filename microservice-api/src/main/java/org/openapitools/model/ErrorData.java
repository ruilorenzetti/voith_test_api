package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.processing.Generated;
import java.util.*;

/**
 * ErrorData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-03T09:43:42.413646-03:00[America/Sao_Paulo]", comments = "Generator version: 7.11.0")
public class ErrorData {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime timestamp;

  private @Nullable Integer machineId;

  private @Nullable String errorType;

  public ErrorData timestamp(OffsetDateTime timestamp) {
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

  public ErrorData machineId(Integer machineId) {
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

  public ErrorData errorType(String errorType) {
    this.errorType = errorType;
    return this;
  }

  /**
   * Get errorType
   * @return errorType
   */
  
  @Schema(name = "errorType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorType")
  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorData errorData = (ErrorData) o;
    return Objects.equals(this.timestamp, errorData.timestamp) &&
        Objects.equals(this.machineId, errorData.machineId) &&
        Objects.equals(this.errorType, errorData.errorType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, machineId, errorType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorData {\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    machineId: ").append(toIndentedString(machineId)).append("\n");
    sb.append("    errorType: ").append(toIndentedString(errorType)).append("\n");
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

