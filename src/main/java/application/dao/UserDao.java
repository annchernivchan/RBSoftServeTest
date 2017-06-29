package application.dao;

import application.model.User;

public interface UserDao extends InterfaceDao<User> {
    User findByUsername(String nickname);
    boolean isExists(String nickname);
}