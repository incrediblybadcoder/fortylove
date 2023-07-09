package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@DevSetupData
public class CourtSetupData {

    public static final long[] COURT_IDS = {1L, 2L, 3L, 4L, 5L, 6L};

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtSetupData(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    public void createCourts() {
        for (final long courtId : COURT_IDS) {
            createCourtIfNotFound(courtId, CourtType.CLAY, false);
        }
    }

    @Transactional
    void createCourtIfNotFound(final long id,
                               final CourtType courtType,
                               final boolean hasBallMachine) {
        final Optional<Court> court = courtService.findById(id);

        if (court.isEmpty()) {
            courtService.create(new Court(courtType, hasBallMachine));
        }
    }
}