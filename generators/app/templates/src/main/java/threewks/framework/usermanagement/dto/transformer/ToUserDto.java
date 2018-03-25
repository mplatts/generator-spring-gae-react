package threewks.framework.usermanagement.dto.transformer;

import threewks.framework.BaseDto;
import threewks.framework.Transformer;
import threewks.framework.usermanagement.dto.UserDto;
import threewks.framework.usermanagement.model.User;

public class ToUserDto implements Transformer<User, UserDto> {

    public static final ToUserDto INSTANCE = new ToUserDto();

    @Override
    public UserDto apply(User user) {
        UserDto dto = BaseDto.fromEntity(new UserDto(), user);
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setName(user.getName());
        dto.getRoles().addAll(user.getRoles());
        return dto;
    }
}
