package my.code.practice_one.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FlightDto {
    Long id;
    String description;
}
