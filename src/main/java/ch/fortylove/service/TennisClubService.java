package ch.fortylove.service;

import ch.fortylove.model.TennisClub;
import ch.fortylove.repository.TennisClubRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class TennisClubService {

    @Nonnull private final TennisClubRepository tennisClubRepository;

    @Autowired
    public TennisClubService(@Nonnull final TennisClubRepository tennisClubRepository) {
        this.tennisClubRepository = tennisClubRepository;
    }

    @Nonnull
    public List<TennisClub> findAll() {
        List<TennisClub> tennisClubs = tennisClubRepository.findAll();
        if (tennisClubs.isEmpty()) {
            return (List<TennisClub>) ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tennisClubs).getBody();
    }

    public TennisClub find(final Long id) {
        return tennisClubRepository.getReferenceById(id);
    }

    public TennisClub create(final TennisClub tennisClub) {
        return tennisClubRepository.saveAndFlush(tennisClub);
    }

    public void delete(final Long id) {
        tennisClubRepository.deleteById(id);
    }

    public TennisClub update(final Long id, final TennisClub tennisClub) {
        final TennisClub existingTennisClub = tennisClubRepository.getReferenceById(id);
        BeanUtils.copyProperties(tennisClub, existingTennisClub, "tennisClub_id");
        return tennisClubRepository.saveAndFlush(existingTennisClub);
    }
}
