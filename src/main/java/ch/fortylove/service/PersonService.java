package ch.fortylove.service;

import ch.fortylove.model.Person;
import ch.fortylove.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class PersonService {

    @Nonnull private final PersonRepository personRepository;

    @Autowired
    public PersonService(@Nonnull final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Nonnull
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Nonnull
    public Person find(final long id) {
        return personRepository.getReferenceById(id);
    }

    @Nonnull
    public Person create(@Nonnull final Person person) {
        return personRepository.saveAndFlush(person);
    }

    public void delete(final long id) {
        personRepository.deleteById(id);
    }

    @Nonnull
    public Person update(final long id, @Nonnull Person person) {
        final Person existingPerson = personRepository.getReferenceById(id);
        BeanUtils.copyProperties(person, existingPerson, "person_id");
        return personRepository.saveAndFlush(existingPerson);
    }
}
