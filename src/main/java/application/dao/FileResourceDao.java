package application.dao;

import application.model.FileResource;

import java.util.List;

public interface FileResourceDao extends InterfaceDao<FileResource> {
    List<FileResource> findFilesByNickname(String nickname);
}
