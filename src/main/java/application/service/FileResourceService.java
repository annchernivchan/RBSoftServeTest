package application.service;

import application.model.FileResource;
import application.model.User;

import java.util.List;

public interface FileResourceService {
    List<FileResource> findFilesByNickname(String nickname);
    FileResource findFileById(String id);
    String getUploadRootPath();
    void deleteFileById(String fileId);
    void saveFile(User user, byte[] fileBytes, String filename);
}
