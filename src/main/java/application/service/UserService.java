package application.service;

import application.model.FileResource;
import application.model.User;

import java.util.List;

public interface UserService {
    boolean isExist(User user);
    void create(User user);
    User findByUsername(String username);
}