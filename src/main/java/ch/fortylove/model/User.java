package ch.fortylove.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.annotation.Nonnull;

@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "name")
    private String name;

    public User() {
    }

    public User(final String name) {
        this.name = name;
    }

    public User(@Nonnull final Long user_id,
                @Nonnull final String name) {
        this.user_id = user_id;
        this.name = name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(@Nonnull final Long user_id) {
        this.user_id = user_id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name +
                '}';
    }
}