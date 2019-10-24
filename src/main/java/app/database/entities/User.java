package app.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@Table(name = "users")
public class User {
    public User() {}

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = (new BCryptPasswordEncoder()).encode(password);
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Session activeSession = null;

    public Integer getId() {
        return id;
    }

    public Session getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(Session activeSession) {
        this.activeSession = activeSession;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}
