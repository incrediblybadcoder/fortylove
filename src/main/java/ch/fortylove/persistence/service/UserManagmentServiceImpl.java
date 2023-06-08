package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class UserManagmentServiceImpl implements UserManagmentService {

    @Nonnull private final UserService userService;
    @Nonnull private final BookingService bookingService;


    public UserManagmentServiceImpl(@Nonnull final UserService userService, @Nonnull final BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Nonnull
    @Override
    public boolean deleteUserAndAllItsBookings(@Nonnull final User user) {
        try {
            //bookingService.deleteAllBookingsForUser(user);
            userService.delete(user);
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
