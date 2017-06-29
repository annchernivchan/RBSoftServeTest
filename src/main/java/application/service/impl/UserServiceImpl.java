package application.service.impl;

import application.dao.UserDao;
import application.model.Authority;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static application.model.Authority.AuthorityType.USER;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isExist(User user) {
        return userDao.isExists(user.getNickname());
    }

    @Override
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthorities(Collections.singletonList(new Authority(USER)));
        userDao.create(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}