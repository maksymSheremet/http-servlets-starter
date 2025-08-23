package my.code.practice_one.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
