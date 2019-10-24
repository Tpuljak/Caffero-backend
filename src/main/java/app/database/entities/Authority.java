package app.database.entities;

import app.database.common.AuthorityType;

import javax.persistence.*;

@Entity(name = "authority")
@Table(name = "authorities")
public class Authority {
    public Authority() {}

    public Authority(AuthorityType name, User user) {
        this.name = name;
        this.user = user;
    }

    @Id
    @GeneratedValue
    private Integer Id;

    @Enumerated(EnumType.STRING)
    private AuthorityType name;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public User getUser() {
        return user;
    }

    public int getId() {
        return Id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthorityType getName() {
        return name;
    }

    public void setName(AuthorityType name) {
        this.name = name;
    }
}
