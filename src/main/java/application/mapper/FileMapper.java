package application.mapper;

import application.dto.FileDto;
import application.model.FileResource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    FileDto toDto(FileResource fileResource);
    List<FileDto> toDto(List<FileResource> fileResources);

    @InheritInverseConfiguration
    FileResource fromDto(FileDto fileDto);

    @InheritInverseConfiguration
    List<FileResource> fromDto(List<FileDto> fileDtos);

}
