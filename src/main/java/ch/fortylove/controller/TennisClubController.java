package ch.fortylove.controller;

import ch.fortylove.model.TennisClub;
import ch.fortylove.service.TennisClubService;
import org.atmosphere.config.service.Get;
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
@RequestMapping("/api/v1/tennisclub")
public class TennisClubController {

    @Autowired
    private TennisClubService tennisClubService;

    @GetMapping
    public List<TennisClub> list() {
        return tennisClubService.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public TennisClub get(@PathVariable Long id) {
        return tennisClubService.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TennisClub create(@RequestBody final TennisClub tennisClub) {
        return tennisClubService.create(tennisClub);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        tennisClubService.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public TennisClub update(@PathVariable Long id, @RequestBody TennisClub tennisClub) {
        return tennisClubService.update(id, tennisClub);
    }

}
