package ch.fortylove.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.annotation.Nonnull;

@Entity(name = "tennis_clubs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TennisClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tennis_club_id")
    private Long tennis_club_id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public TennisClub() {
    }

    public TennisClub(@Nonnull final  String name,@Nonnull final String address) {
        this.name = name;
        this.address = address;
    }
    @Override
    public String toString() {
        return "TennisClub{" +
                "tennis_club_id=" + tennis_club_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
