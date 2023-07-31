package ch.fortylove;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import jakarta.annotation.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(
		name = FortyloveApplication.APP_NAME,
		shortName = FortyloveApplication.APP_NAME,
		offlinePath = "offline.html",
		offlineResources = {"./images/offline.png"}
)
@Theme(FortyloveApplication.APP_NAME)
public class FortyloveApplication implements AppShellConfigurator {

	@Nonnull public static final String APP_NAME = "fortylove";

	public static void main(String[] args) {
		SpringApplication.run(FortyloveApplication.class, args);
	}
}
