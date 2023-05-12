package ch.fortylove.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import javax.annotation.Nonnull;

@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends AbstractEntity {

    @Column(name = "name")
    private String name;

    public User() {
    }

    public User(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name +
                '}';
    }
}