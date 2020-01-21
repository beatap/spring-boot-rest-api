package pl.beata.springbootrestapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {

    private long carId;
    private String mark;
    private String model;
    private String color;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate year;
}
