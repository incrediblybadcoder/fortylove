package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.service.CourtService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DevSetupData
public class CourtSetupData {

    public static final int NUMBER_OF_COURTS = 4;
    private static final int[] NUMBERS = {1, 2, 3, 4};
    private static final String[] NAMES = {"Becker", "Federer", "McEnroe", "Nadal"};
    private static final CourtType[] COURT_TYPES = {CourtType.SYNTHETIC, CourtType.SYNTHETIC, CourtType.SYNTHETIC, CourtType.SYNTHETIC};
    private static final CourtIcon[] COURT_ICONS = {CourtIcon.ORANGE, CourtIcon.ORANGE, CourtIcon.ORANGE, CourtIcon.ORANGE};

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtSetupData(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    public void createCourts() {
        for (int i = 0; i < NUMBER_OF_COURTS; i++) {
            createCourt(COURT_TYPES[i], COURT_ICONS[i], NUMBERS[i], NAMES[i]);
        }
    }

    @Transactional
    private void createCourt(@Nonnull final CourtType courtType,
                             @Nonnull final CourtIcon courtIcon,
                             final int number,
                             @Nonnull final String name) {
        courtService.create(new Court(courtType, courtIcon, number, name));
    }
}