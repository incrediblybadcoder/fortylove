package ch.fortylove.presentation.views.sitenotice;

import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Route(value = SiteNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(SiteNoticeView.PAGE_TITLE)
@PermitAll
@Component
public class SiteNoticeView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "sitenotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";


    @Value("${siteNotice.name}")
    private String name;

    @Value("${siteNotice.street}")
    private String street;

    @Value("${siteNotice.plz}")
    private String plz;

    @Value("${siteNotice.city}")
    private String city;

    @Value("${siteNotice.country}")
    private String country;

    @Value("${siteNotice.email}")
    private String email;

    @Value("${siteNotice.website}")
    private String website;

    public SiteNoticeView() {
    }

    @PostConstruct
    public void init() {
        add(new NativeLabel(name));
        add(new NativeLabel(street));
        add(new NativeLabel(plz + " " + city));
        add(new NativeLabel(country));
        add(new NativeLabel("E-Mail: " + email));
        add(new NativeLabel("Website: " + website));
    }

}