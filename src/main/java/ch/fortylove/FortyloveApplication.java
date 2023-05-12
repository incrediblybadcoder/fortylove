package ch.fortylove;

import ch.fortylove.data.entity.TennisClub;
import ch.fortylove.data.entity.User;
import ch.fortylove.data.repository.TennisClubRepository;
import ch.fortylove.data.repository.UserRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@PWA(
		name = "fortylove",
		shortName = "fortylove",
		offlinePath = "offline.html",
		offlineResources = {"./images/offline.png"}
)
public class FortyloveApplication implements AppShellConfigurator {

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
