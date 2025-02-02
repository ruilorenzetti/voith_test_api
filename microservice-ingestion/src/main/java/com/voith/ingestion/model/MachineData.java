package com.voith.ingestion.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;



/**
 * MachineData
 */

public class MachineData {

    private @Nullable Integer machineId;

    private @Nullable String model;

    private @Nullable Integer age;

    /**
     * Gets or Sets status
     */
    public enum StatusEnum {
        ACTIVE("active"),

        INACTIVE("inactive"),

        MAINTENANCE("maintenance");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String value) {
            for (StatusEnum b : StatusEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    private @Nullable StatusEnum status;

    public MachineData machineId(Integer machineId) {
        this.machineId = machineId;
        return this;
    }

    /**
     * Get machineId
     * @return machineId
     */

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public MachineData model(String model) {
        this.model = model;
        return this;
    }

    /**
     * Get model
     * @return model
     */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public MachineData age(Integer age) {
        this.age = age;
        return this;
    }

    /**
     * Get age
     * @return age
     */

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MachineData status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     * @return status
     */


    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MachineData machineData = (MachineData) o;
        return Objects.equals(this.machineId, machineData.machineId) &&
                Objects.equals(this.model, machineData.model) &&
                Objects.equals(this.age, machineData.age) &&
                Objects.equals(this.status, machineData.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineId, model, age, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MachineData {\n");
        sb.append("    machineId: ").append(toIndentedString(machineId)).append("\n");
        sb.append("    model: ").append(toIndentedString(model)).append("\n");
        sb.append("    age: ").append(toIndentedString(age)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

