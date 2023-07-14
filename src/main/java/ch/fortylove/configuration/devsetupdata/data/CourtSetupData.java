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
    private static final int[] NUMBERS = {1, 2, 3, 4, 5, 6};
    private static final String[] NAMES = {"Becker", "Federer", "McEnroe", "Nadal", "Agassi", "Borg"};
    private static final CourtType[] COURT_TYPES = {CourtType.CLAY, CourtType.CLAY, CourtType.GRASS, CourtType.GRASS, CourtType.HARD, CourtType.SYNTHETIC};

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtSetupData(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    public void createCourts() {
        for (int i = 0; i < COURT_IDS.length; i++) {
            createCourtIfNotFound(COURT_IDS[i], COURT_TYPES[i], NUMBERS[i], NAMES[i]);
        }
    }

    @Transactional
    void createCourtIfNotFound(final long id,
                               @Nonnull final CourtType courtType,
                               final int number,
                               @Nonnull final String name) {
        final Optional<Court> court = courtService.findById(id);

        if (court.isEmpty()) {
            courtService.create(new Court(courtType, number, name));
        }
    }
}