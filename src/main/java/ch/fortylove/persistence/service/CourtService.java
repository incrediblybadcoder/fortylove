package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.repository.CourtRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Collection;

@Service
public class CourtService {

    @Nonnull private final CourtRepository courtRepository;

    @Autowired
    public CourtService(@Nonnull final CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @Nonnull
    public Collection<Court> findAll() {
        return courtRepository.findAll();
    }

    @Nonnull
    public Court find(final long id) {
        return courtRepository.getReferenceById(id);
    }

    @Nonnull
    public Court create(@Nonnull final Court court) {
        return courtRepository.saveAndFlush(court);
    }

    public void delete(final long id) {
        courtRepository.deleteById(id);
    }

    @Nonnull
    public Court update(final long id,
                        @Nonnull final Court court) {
        final Court existingCourt = courtRepository.getReferenceById(id);
        BeanUtils.copyProperties(court, existingCourt, "court_id");
        return courtRepository.saveAndFlush(existingCourt);
    }
}
