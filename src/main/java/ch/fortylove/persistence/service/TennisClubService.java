package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.TennisClub;
import ch.fortylove.persistence.repository.TennisClubRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return tennisClubRepository.findAll();
    }

    @Nonnull
    public TennisClub find(final Long id) {
        return tennisClubRepository.getReferenceById(id);
    }

    @Nonnull
    public TennisClub create(final TennisClub tennisClub) {
        return tennisClubRepository.saveAndFlush(tennisClub);
    }

    public void delete(final Long id) {
        tennisClubRepository.deleteById(id);
    }

    @Nonnull
    public TennisClub update(final Long id, final TennisClub tennisClub) {
        final TennisClub existingTennisClub = tennisClubRepository.getReferenceById(id);
        BeanUtils.copyProperties(tennisClub, existingTennisClub, "tennisClub_id");
        return tennisClubRepository.saveAndFlush(existingTennisClub);
    }
}
