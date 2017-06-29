package application.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "files")
@NamedQueries({
        @NamedQuery(
                name = FileResource.FIND_FILES_BY_NICKNAME,
                query = "select f FROM FileResource f INNER JOIN f.owner u WHERE u.nickname = :nickname"
        )
})
public class FileResource implements Serializable {

    public static final String FIND_FILES_BY_NICKNAME = "User.findFilesByNickname";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    @Column(name = "relative_path")
    private String relativePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    public FileResource() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

}