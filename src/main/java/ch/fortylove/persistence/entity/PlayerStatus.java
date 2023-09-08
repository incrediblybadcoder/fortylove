package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "playerstatus")
public class PlayerStatus extends AbstractEntity implements Comparable<PlayerStatus>, HasIdentifier {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "playerstatus_type")
    private PlayerStatusType playerStatusType;

    @Column(name = "is_default")
    private boolean isDefault;

    @PositiveOrZero
    @Column(name = "bookings_per_day")
    private int bookingsPerDay;

    @PositiveOrZero
    @Column(name = "bookable_days_in_advance")
    private int bookableDaysInAdvance;

    @OneToMany(mappedBy = "playerStatus", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    protected PlayerStatus() {
    }

    public PlayerStatus(@Nonnull final String name,
                        @Nonnull final PlayerStatusType playerStatusType,
                        final boolean isDefault,
                        final int bookingsPerDay,
                        final int bookableDaysInAdvance) {
        super(UUID.randomUUID());
        this.name = name;
        this.playerStatusType = playerStatusType;
        this.isDefault = isDefault;
        this.bookingsPerDay = bookingsPerDay;
        this.bookableDaysInAdvance = bookableDaysInAdvance;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public PlayerStatusType getPlayerStatusType() {
        return playerStatusType;
    }

    public void setPlayerStatusType(@Nonnull final PlayerStatusType playerStatusType) {
        this.playerStatusType = playerStatusType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(final boolean aDefault) {
        isDefault = aDefault;
    }

    @Nonnull
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final Set<User> users) {
        this.users = users;
    }

    public int getBookingsPerDay() {
        return bookingsPerDay;
    }

    public void setBookingsPerDay(final int bookingsPerDay) {
        this.bookingsPerDay = bookingsPerDay;
    }

    public int getBookableDaysInAdvance() {
        return bookableDaysInAdvance;
    }

    public void setBookableDaysInAdvance(final int bookableDaysInAdvance) {
        this.bookableDaysInAdvance = bookableDaysInAdvance;
    }

    @Override
    public String toString() {
        return "PlayerStatus{" +
                "name='" + name + '\'' +
                "playerStatusType='" + playerStatusType + '\'' +
                "isDefault='" + isDefault + '\'' +
                '}';
    }

    @Override
    public int compareTo(final PlayerStatus o) {
        return name.compareTo(o.getName());
    }

    @Nonnull
    public String getIdentifier() {
        return name;
    }
}
