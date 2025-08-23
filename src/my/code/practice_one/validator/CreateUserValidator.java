package my.code.practice_one.validator;

import my.code.practice_one.dto.CreateUserDto;
import my.code.practice_one.entity.Gender;
import my.code.practice_one.util.LocalDateFormater;

public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();

        if (!LocalDateFormater.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }

        if (object.getGender() == null || Gender.find(object.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }

        return validationResult;
    }
}
