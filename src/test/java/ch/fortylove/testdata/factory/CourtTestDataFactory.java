package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.service.CourtService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@SpringComponent
public class CourtTestDataFactory {

    @Nonnull private final CourtService courtService;

    private UUID defaultCourtId = new UUID(0L,0L);

    @Autowired
    public CourtTestDataFactory(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    @Nonnull
    public Court createCourt() {
        return courtService.create(new Court(CourtType.CLAY, CourtIcon.ORANGE, 1, "name")).getData().get();
    }

    @Nonnull
    public Court getDefault() {
        final Optional<Court> defaultCourt = courtService.findById(defaultCourtId);
        if (defaultCourt.isEmpty()) {
            final Court court = createCourt();
            defaultCourtId = court.getId();
            return court;
        }

        return defaultCourt.get();
    }
}
