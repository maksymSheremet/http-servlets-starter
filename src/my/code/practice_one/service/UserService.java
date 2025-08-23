package my.code.practice_one.service;

import my.code.practice_one.dao.UserDao;
import my.code.practice_one.dto.CreateUserDto;
import my.code.practice_one.exception.ValidationException;
import my.code.practice_one.mapper.CreaterUserMapper;
import my.code.practice_one.validator.CreateUserValidator;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreaterUserMapper createrUserMapper = CreaterUserMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createrUserMapper.mapFrom(createUserDto);
        var save = userDao.save(user);
        return save.getId();
    }
}
