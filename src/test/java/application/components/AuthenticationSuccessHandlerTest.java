package application.components;

import application.model.Authority;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static application.model.Authority.AuthorityType.ADMIN;
import static application.model.Authority.AuthorityType.USER;

public class AuthenticationSuccessHandlerTest {

    @Test
    public void homePageTest() {
        AuthenticationSuccessHandlerImpl successHandler = new AuthenticationSuccessHandlerImpl();

        String userHomePage = successHandler.getHomePage(Collections.singleton(new Authority(USER)));
        String adminHomePage = successHandler.getHomePage(Collections.singleton(new Authority(ADMIN)));

        Assert.assertArrayEquals(
                new String[]{userHomePage, adminHomePage},
                new String[]{"/files", "/adminPage"}
        );

    }

}
