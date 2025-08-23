package my.code.practice_one.dto;

import lombok.Builder;
import lombok.Value;
import my.code.practice_one.entity.Gender;
import my.code.practice_one.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class ResponseUserDto {
    Integer id;
    String name;
    String email;
    LocalDate birthday;
    String image;
    Role role;
    Gender gender;
}
