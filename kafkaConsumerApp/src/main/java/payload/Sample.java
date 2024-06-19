package payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sample {
    private String name;
    private Integer age;
    private Float rating;
    private Boolean active;
}