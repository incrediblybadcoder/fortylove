package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile("!production")
public class CourtSetupData {

    @Nonnull private final CourtRepository courtRepository;

    @Autowired
    public CourtSetupData(@Nonnull final CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public void createCourts() {
        createCourtIfNotFound(1L);
        createCourtIfNotFound(2L);
        createCourtIfNotFound(3L);
        createCourtIfNotFound(4L);
        createCourtIfNotFound(5L);
        createCourtIfNotFound(6L);
    }

    @Transactional
    void createCourtIfNotFound(final long id) {
        Court court = courtRepository.findById(id);
        if (court == null) {
            court = new Court();
            courtRepository.save(court);
        }
    }
}