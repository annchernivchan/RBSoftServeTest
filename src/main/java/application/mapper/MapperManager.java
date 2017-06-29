package application.mapper;

import org.mapstruct.factory.Mappers;

public interface MapperManager {
    RegistrationUserMapper REGISTRATION_USER = Mappers.getMapper(RegistrationUserMapper.class);
    FileMapper FILE_MAPPER = Mappers.getMapper(FileMapper.class);
}