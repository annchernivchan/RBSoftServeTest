package application.components;

import application.model.Authority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        redirectStrategy.sendRedirect(request, response, getHomePage(authentication.getAuthorities()));
    }

    public String getHomePage(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return "/";
        }

        String authority = authorities
                .stream()
                .findFirst()
                .orElse(null)
                .getAuthority();

        Authority.AuthorityType authorityType = Authority.AuthorityType.valueOf(authority);

        switch (authorityType) {
            case ADMIN: return "/adminPage";
            case USER: return "/files";

            default: throw new IllegalArgumentException("Authority type not present: " + authorityType);
        }

    }

}
