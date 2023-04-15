package ch.fortylove.controller;

import ch.fortylove.model.Person;
import ch.fortylove.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> list() {
        return personService.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Person get(@PathVariable Long id) {
        return personService.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody final Person person) {
        return personService.create(person);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Person update(@PathVariable Long id, @RequestBody Person person) {
        return personService.update(id, person);
    }
}
