package application.service.impl;

import application.dao.FileResourceDao;
import application.model.FileResource;
import application.model.User;
import application.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class FileResourceServiceImpl implements FileResourceService {
    private static final Logger logger = Logger.getLogger(FileResourceServiceImpl.class.getName());

    @Value("${upload.base_directory}")
    private String baseDirectory;

    @Autowired
    private FileResourceDao fileResourceDao;

    @Override
    public List<FileResource> findFilesByNickname(String nickname) {
        return fileResourceDao.findFilesByNickname(nickname);
    }

    @Override
    public FileResource findFileById(String id) {
        return fileResourceDao.findOne(id);
    }

    @Override
    public void deleteFileById(String fileId) {
        FileResource fileResource = fileResourceDao.findOne(fileId);

        if (fileResource != null) {
            logger.info("Removed file with id:" + fileId + ", name: " + fileResource.getName());

            String relativePath = fileResource.getRelativePath();

            Paths.get(getUploadRootPath(), relativePath).toFile().delete();
            fileResourceDao.deleteById(fileId);
        }

    }

    @Override
    public void saveFile(User user, byte[] fileBytes, String filename) {
        String generatedFilename = generateName();
        String relativeDirectory = File.separator + generatedFilename;

        File destinationDirectory = new File(getUploadRootPath());
        destinationDirectory.mkdirs();

        String fullPath = Paths.get(destinationDirectory.getAbsolutePath()) + File.separator + generatedFilename;

        StringBuilder messageBuilder = new StringBuilder();
        try {
            Files.write(Paths.get(fullPath).normalize(), fileBytes);

            FileResource fileResourceEntity = new FileResource();
            fileResourceEntity.setOwner(user);
            fileResourceEntity.setName(filename);
            fileResourceEntity.setRelativePath(relativeDirectory);

            fileResourceDao.create(fileResourceEntity);

            messageBuilder.append("file saved");
        } catch (Exception e) {
            e.printStackTrace();
            messageBuilder.append("Fail to save file");
        }

        messageBuilder.append(", Path: ").append(fullPath);

        logger.info(messageBuilder.toString());
    }

    @Override
    public String getUploadRootPath() {
        return System.getProperty("catalina.home") + File.separator + baseDirectory;
    }

    private String generateName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
