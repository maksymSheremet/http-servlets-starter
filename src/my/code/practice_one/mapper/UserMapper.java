package my.code.practice_one.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.code.practice_one.dto.ResponseUserDto;
import my.code.practice_one.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, ResponseUserDto> {
    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseUserDto mapFrom(User object) {
        return ResponseUserDto.builder()
                .id(object.getId())
                .email(object.getEmail())
                .name(object.getName())
                .image(object.getImage())
                .birthday(object.getBirthday())
                .gender(object.getGender())
                .role(object.getRole())
                .build();
    }
}
