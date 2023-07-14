package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@DevSetupData
public class CourtSetupData {

    public static final int NUMBER_OF_COURTS = 6;
    private static final int[] NUMBERS = {1, 2, 3, 4, 5, 6};
    private static final String[] NAMES = {"Becker", "Federer", "McEnroe", "Nadal", "Agassi", "Borg"};
    private static final CourtType[] COURT_TYPES = {CourtType.CLAY, CourtType.CLAY, CourtType.GRASS, CourtType.GRASS, CourtType.HARD, CourtType.SYNTHETIC};

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtSetupData(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    public void createCourts() {
        for (int i = 0; i < NUMBER_OF_COURTS; i++) {
            createCourt(COURT_TYPES[i], NUMBERS[i], NAMES[i]);
        }
    }

    @Transactional
    private void createCourt(@Nonnull final CourtType courtType,
                             final int number,
                             @Nonnull final String name) {
        courtService.create(new Court(courtType, number, name));
    }
}