package ch.fortylove;

import ch.fortylove.model.TennisClub;
import ch.fortylove.model.User;
import ch.fortylove.repository.TennisClubRepository;
import ch.fortylove.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FortyloveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FortyloveApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(UserRepository userRepository, TennisClubRepository tennisClubRepository) {
		return args -> {
			userRepository.deleteAll();
			userRepository.save(new User("Marco"));
			userRepository.save(new User("Jonas"));
			userRepository.save(new User("Anna"));
			userRepository.save(new User("Zoro"));
			tennisClubRepository.deleteAll();
			tennisClubRepository.save(new TennisClub("TC Winterthur", "Winterthur"));
			tennisClubRepository.save(new TennisClub("TC Untervaz", "Untervaz"));
			tennisClubRepository.save(new TennisClub("TC Zürich", "Zürich"));
			tennisClubRepository.save(new TennisClub("TC Kalifornien", "USA"));
		};
	}
}
