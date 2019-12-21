package pl.beata.springbootrestapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends ResourceSupport {

    private long carId;
    private String mark;
    private String model;
    private String color;
}
