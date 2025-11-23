package co.edu.uptc.access_control_service.domain.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;


public class AccessDateTime {
    private final String value;
    public AccessDateTime(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.parse(value);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessDateTime that = (AccessDateTime) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AccessDateTime{" +
                "value='" + value + '\'' +
                '}';
    }
}
