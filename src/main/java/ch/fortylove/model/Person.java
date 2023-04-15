package ch.fortylove.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.annotation.Nonnull;

@Entity(name = "persons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long person_id;

    @Column(name = "name")
    private String name;

    public Person() {
    }

    public Person(final String name) {
        this.name = name;
    }

    public Person(@Nonnull final Long person_id,
                  @Nonnull final String name) {
        this.person_id = person_id;
        this.name = name;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(@Nonnull final Long person_id) {
        this.person_id = person_id;
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
        return "Person{" +
                "person_id=" + person_id +
                ", name='" + name +
                '}';
    }
}