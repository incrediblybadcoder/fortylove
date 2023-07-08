package ch.fortylove.testdata.factory;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@SpringComponent
public class TestDataFactory {

    @Nonnull private final PrivilegeTestDataFactory privilegeTestDataFactory;
    @Nonnull private final RoleTestDataFactory roleTestDataFactory;
    @Nonnull private final CourtTestDataFactory courtTestDataFactory;
    @Nonnull private final PlayerStatusTestDataFactory playerStatusTestDataFactory;
    @Nonnull private final UserTestDataFactory userTestDataFactory;
    @Nonnull private final TimeslotTestDataFactory timeslotTestDataFactory;
    @Nonnull private final BookingSettingsTestDataFactory bookingSettingsTestDataFactory;

    @Autowired
    public TestDataFactory(@Nonnull final PrivilegeTestDataFactory privilegeTestDataFactory,
                           @Nonnull final RoleTestDataFactory roleTestDataFactory,
                           @Nonnull final CourtTestDataFactory courtTestDataFactory,
                           @Nonnull final PlayerStatusTestDataFactory playerStatusTestDataFactory,
                           @Nonnull final UserTestDataFactory userTestDataFactory,
                           @Nonnull final TimeslotTestDataFactory timeslotTestDataFactory,
                           @Nonnull final BookingSettingsTestDataFactory bookingSettingsTestDataFactory) {
        this.privilegeTestDataFactory = privilegeTestDataFactory;
        this.roleTestDataFactory = roleTestDataFactory;
        this.courtTestDataFactory = courtTestDataFactory;
        this.playerStatusTestDataFactory = playerStatusTestDataFactory;
        this.userTestDataFactory = userTestDataFactory;
        this.timeslotTestDataFactory = timeslotTestDataFactory;
        this.bookingSettingsTestDataFactory = bookingSettingsTestDataFactory;
    }

    @Nonnull
    public PrivilegeTestDataFactory getPrivilegeDataFactory() {
        return privilegeTestDataFactory;
    }

    @Nonnull
    public RoleTestDataFactory getRoleDataFactory() {
        return roleTestDataFactory;
    }

    @Nonnull
    public CourtTestDataFactory getCourtDataFactory() {
        return courtTestDataFactory;
    }

    @Nonnull
    public PlayerStatusTestDataFactory getPlayerStatusDataFactory() {
        return playerStatusTestDataFactory;
    }

    @Nonnull
    public UserTestDataFactory getUserDataFactory() {
        return userTestDataFactory;
    }

    @Nonnull
    public TimeslotTestDataFactory getTimeslotDataFactory() {
        return timeslotTestDataFactory;
    }

    @Nonnull
    public BookingSettingsTestDataFactory getBookingSettingsDataFactory() {
        return bookingSettingsTestDataFactory;
    }
}
