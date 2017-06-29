package application.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = User.FIND_BY_USERNAME,
                query = "FROM User u WHERE u.nickname = :nickname"
        )
})
public class User implements Serializable {

    public static final String FIND_BY_USERNAME = "User.findByUsername";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "nickname", nullable = false, length = 36)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "owner"
    )
    private List<FileResource> files;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", nullable = false)})
    private Collection<Authority> authorities;

    public User() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<FileResource> getFiles() {
        return files;
    }
    public void setFiles(List<FileResource> files) {
        this.files = files;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

}