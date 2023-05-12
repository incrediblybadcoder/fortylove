package ch.fortylove.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import javax.annotation.Nonnull;

@Entity(name = "tennis_clubs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TennisClub extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public TennisClub() {
    }

    public TennisClub(@Nonnull final String name,
                      @Nonnull final String address) {
        this.name = name;
        this.address = address;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nonnull final String address) {
        this.address = address;
    }

    @Override
    @Nonnull
    public String toString() {
        return "TennisClub{" +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
