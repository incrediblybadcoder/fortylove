package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Article;
import ch.fortylove.service.ArticleService;
import ch.fortylove.util.DateTimeUtil;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@DevSetupData
public class ArticleDevSetupData implements ch.fortylove.configuration.devsetupdata.data.DevSetupData {

    @Nonnull private final ArticleService articleService;
    @Nonnull private final DateTimeUtil dateTimeUtil;

    @Autowired
    public ArticleDevSetupData(@Nonnull final ArticleService articleService,
                               @Nonnull final DateTimeUtil dateTimeUtil) {
        this.articleService = articleService;
        this.dateTimeUtil = dateTimeUtil;
    }

    @Override
    public void createDevData() {
        createArticle("Cluberöffnung", "Good News Everybody! Der TCS Untervaz wurde soeben eröffnet!", yesterday());
        createArticle("Sand im Clubhaus", "Bitte reinigt eure Schuhe nach dem Verlassen des Tennisplatzes" +
                "und vor dem Betreten des Clubhauses gründlich. Unsere Böden sehen vermehrt aus wie die Strände vom Mallorca!", yesterday());
        createArticle("Rücktritt Clubpresident", "Hanspeter Peterhans wird uns Ende Jahr nach nur kurzer Amtszeit" +
                "leider verlassen. Es hat sich herausgestellt, dass er eine Allergie gegen Tennisballhaare entwickelt hat.", yesterday());
        createArticle("Tennisschläger Aktion", "Wir dürfen euch mit Freude mitteilen, dass unser Partner " +
                "\"Brackets105\" ihre neuste Kollektion an Profischläger zu vergünstigten Preisen anbietet. Der Flyer " +
                "liegt in der Küche im Clubhaus aus.", yesterday());
        createArticle("Sommerturnier", "Soeben eröffnet findet auch gleich das erste Turnier statt!" +
                " Alle sind herzlich eingeladen, an unserem Sommerturnier nächste Woche Samstag teilzunehmen", today());
        createArticle("Zukünftige Wartungsarbeiten", "Die Plätze 2 und 3 werden kommende Woche gewartet." +
                "Aus diesem Grund wurden sie bereits gesperrt. Alle bereits bestehenden Buchungen wurde aufgelöst.", today());
    }

    @Transactional
    private void createArticle(@Nonnull final String title,
                               @Nonnull final String text,
                               @Nonnull final LocalDateTime dateTime) {
        articleService.create(new Article(title, text, dateTime));
    }

    @Nonnull
    private LocalDateTime today() {
        return dateTimeUtil.todayNow();
    }

    @Nonnull
    private LocalDateTime yesterday() {
        return dateTimeUtil.todayNow().minusDays(1);
    }
}