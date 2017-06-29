package application.mapper;

import application.dto.RegistrationUserDto;
import application.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface RegistrationUserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.nickname")
    })
    RegistrationUserDto toDto(User user);

    @InheritInverseConfiguration
    User fromDto(RegistrationUserDto registrationUserDto);

}
