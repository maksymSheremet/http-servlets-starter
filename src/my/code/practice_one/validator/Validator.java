package my.code.practice_one.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
