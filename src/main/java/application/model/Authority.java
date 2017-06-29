package application.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    public enum AuthorityType {
        USER, ADMIN
    }

    @Id
    @Column(name = "name", nullable = false, length = 36)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    public Authority() {}

    public Authority(AuthorityType authority) {
        this.authority = authority;
    }

    public Authority(String authority) {
        this.authority = AuthorityType.valueOf(authority);
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }
    public void setAuthority(AuthorityType authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority1 = (Authority) o;

        return authority == authority1.authority;
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }

}