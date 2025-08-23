package my.code.practice_one.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
