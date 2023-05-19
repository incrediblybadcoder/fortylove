package ch.fortylove.service;

import ch.fortylove.persistence.entity.TennisClub;
import ch.fortylove.persistence.repository.TennisClubRepository;
import ch.fortylove.persistence.service.TennisClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TennisClubServiceTest {

    @Mock
    private TennisClubRepository tennisClubRepository;

    @InjectMocks
    private TennisClubService tennisClubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        // Prepare test data
        List<TennisClub> tennisClubs = new ArrayList<>();
        tennisClubs.add(new TennisClub("Club1", "Address1"));
        tennisClubs.add(new TennisClub("Club2", "Address2"));

        // Define mock behavior
        when(tennisClubRepository.findAll()).thenReturn(tennisClubs);

        // Execute the method to be tested
        List<TennisClub> result = tennisClubService.findAll();

        // Assert the result
        assertEquals(tennisClubs, result);
    }
}
