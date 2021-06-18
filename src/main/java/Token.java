import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    //种别码
    private Integer type;

    private String value;

    //偏移地址
    private Integer entry;

    @Override
    public String toString() {
        return "Token<" + type +
                "," + value +
                "," + entry +
                '>';
    }
}
