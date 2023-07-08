package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Timeslot;
import com.vaadin.flow.spring.annotation.SpringComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SpringComponent
public class TimeslotTestDataFactory {

    @Nonnull
    public List<Timeslot> getDefaultBookable() {
        return Arrays.asList(
                new Timeslot(true, 0),
                new Timeslot(true, 1),
                new Timeslot(true, 2),
                new Timeslot(true, 3),
                new Timeslot(true, 4),
                new Timeslot(true, 5),
                new Timeslot(true, 6),
                new Timeslot(true, 7),
                new Timeslot(true, 8),
                new Timeslot(true, 9),
                new Timeslot(true, 10),
                new Timeslot(true, 11),
                new Timeslot(true, 12),
                new Timeslot(true, 13),
                new Timeslot(true, 14),
                new Timeslot(true, 15),
                new Timeslot(true, 16),
                new Timeslot(true, 17),
                new Timeslot(true, 18),
                new Timeslot(true, 19),
                new Timeslot(true, 20),
                new Timeslot(true, 21),
                new Timeslot(true, 22),
                new Timeslot(true, 23)
        );
    }

    @Nonnull
    public List<Timeslot> getDefaultNonBookable() {
        return Arrays.asList(
                new Timeslot(false, 0),
                new Timeslot(false, 1),
                new Timeslot(false, 2),
                new Timeslot(false, 3),
                new Timeslot(false, 4),
                new Timeslot(false, 5),
                new Timeslot(false, 6),
                new Timeslot(false, 7),
                new Timeslot(false, 8),
                new Timeslot(false, 9),
                new Timeslot(false, 10),
                new Timeslot(false, 11),
                new Timeslot(false, 12),
                new Timeslot(false, 13),
                new Timeslot(false, 14),
                new Timeslot(false, 15),
                new Timeslot(false, 16),
                new Timeslot(false, 17),
                new Timeslot(false, 18),
                new Timeslot(false, 19),
                new Timeslot(false, 20),
                new Timeslot(false, 21),
                new Timeslot(false, 22),
                new Timeslot(false, 23)
        );
    }
}
