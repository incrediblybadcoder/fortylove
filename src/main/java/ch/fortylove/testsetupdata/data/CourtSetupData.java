package ch.fortylove.testsetupdata.data;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.testsetupdata.TestSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@TestSetupData
public class CourtSetupData {

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtSetupData(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
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
        final Optional<Court> court = courtService.findById(id);

        if (court.isEmpty()) {
            courtService.create(new Court());
        }
    }
}