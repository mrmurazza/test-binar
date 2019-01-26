package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import utils.Utils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password_digest")
    @JsonProperty("password_digest")
    private String passwordDigest;

    @Column(name="access_token")
    @JsonIgnore
    private String accessToken;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private Date updatedAt;

    public User(){

    }

    public User(String name, String email, String passwordDigest){
        this.name = name;
        this.email = email;
        this.passwordDigest = passwordDigest;
        this.accessToken = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("created_at")
    public String getPrettyCreatedAt(){
        return Utils.formatToPrettyDate(createdAt);
    }

    @JsonProperty("updated_at")
    public String getPrettyUpdatedAt(){
        return Utils.formatToPrettyDate(updatedAt);
    }
}
