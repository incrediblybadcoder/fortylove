package ch.fortylove.presentation.views.booking.grid;

import ch.fortylove.persistence.entity.Court;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.List;

public class BookingGridConfiguration {
    @Nonnull private final LocalDate date;
    @Nonnull private final List<Court> courts;

    public BookingGridConfiguration(@Nonnull final LocalDate date,
                                    @Nonnull final List<Court> courts) {
        this.date = date;
        this.courts = courts;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }

    @Nonnull
    public List<Court> getCourts() {
        return courts;
    }
}
