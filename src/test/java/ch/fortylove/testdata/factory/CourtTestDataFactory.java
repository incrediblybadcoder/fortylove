package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.service.CourtService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringComponent
public class CourtTestDataFactory {

    public static final long DEFAULT_COURT = 1L;

    @Nonnull private final CourtService courtService;

    @Autowired
    public CourtTestDataFactory(@Nonnull final CourtService courtService) {
        this.courtService = courtService;
    }

    @Nonnull
    public Court createCourt() {
        return courtService.create(new Court(CourtType.CLAY, false));
    }

    @Nonnull
    public Court getDefault() {
        final Optional<Court> defaultCourt = courtService.findById(DEFAULT_COURT);
        if (defaultCourt.isEmpty()) {
            return createCourt();
        }

        return defaultCourt.get();
    }
}
