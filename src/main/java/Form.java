import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Form {
    private String value;

    private String attribute;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return Objects.equals(value, form.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, attribute);
    }
}
