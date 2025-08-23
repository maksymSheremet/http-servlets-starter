package my.code.practice_one.mapper;

import my.code.practice_one.dto.CreateUserDto;
import my.code.practice_one.entity.Gender;
import my.code.practice_one.entity.Role;
import my.code.practice_one.entity.User;
import my.code.practice_one.util.LocalDateFormater;

public class CreaterUserMapper implements Mapper<CreateUserDto, User> {
    private static final String IMAGE_FOLDER = "users/";

    private static final CreaterUserMapper INSTANCE = new CreaterUserMapper();

    public static CreaterUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormater.format(object.getBirthday()))
                .email(object.getEmail())
                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .role(Role.valueOf(object.getRole()))
                .build();
    }
}
