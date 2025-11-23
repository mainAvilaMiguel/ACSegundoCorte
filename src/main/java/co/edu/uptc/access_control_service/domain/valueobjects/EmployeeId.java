package co.edu.uptc.access_control_service.domain.valueobjects;

public class EmployeeId {
    private final String value;

    public EmployeeId(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeId that = (EmployeeId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "EmployeeId[" + value + "]";
    }
}
