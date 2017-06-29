package application.dao.impls;

import application.dao.AbstractDao;
import application.dao.UserDao;
import application.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public User findByUsername(String nickname) {
        return entityManager
                .createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }

    public boolean isExists(String nickname) {
        try {
            User user = findByUsername(nickname);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}