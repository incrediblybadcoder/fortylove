package ch.fortylove;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(
		name = "fortylove",
		shortName = "fortylove",
		offlinePath = "offline.html",
		offlineResources = {"./images/offline.png"}
)
@Theme(value = "fortylove")
public class FortyloveApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(FortyloveApplication.class, args);
	}
}
