package application.dao.impls;

import application.dao.AbstractDao;
import application.dao.FileResourceDao;
import application.model.FileResource;
import application.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileResourceDaoImpl extends AbstractDao<FileResource> implements FileResourceDao {
    @Override
    public List<FileResource> findFilesByNickname(String nickname) {
        return entityManager
                .createNamedQuery(FileResource.FIND_FILES_BY_NICKNAME, FileResource.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
