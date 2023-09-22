package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Article;
import ch.fortylove.service.ArticleService;
import ch.fortylove.util.DateTimeUtil;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
        createArticle("Einführung fortylove", "Der TC Untervaz ist neu mit fortylove unterwegs! Hier könnt ihr ab jetzt eure Platzbuchungen vornehmen.", yesterday(LocalTime.of(13, 25)));
        createArticle("Tennisschläger Aktion", "Wir dürfen euch mit Freude mitteilen, dass unser Partner \"Tennisshop41\" ihre neuste Kollektion an Profischläger zu vergünstigten Preisen anbietet. Der Flyer " +
                "liegt in der Küche im Clubhaus aus.", yesterday(LocalTime.of(18, 2)));
        createArticle("Anmeldung Clubabend", "Denkt daran, euch für den Clubabend am 21. Oktober anzumelden. Die Frist läuft noch bis kommenden Freitag.", today(LocalTime.of(9, 54)));
    }

    @Transactional
    private void createArticle(@Nonnull final String title,
                               @Nonnull final String text,
                               @Nonnull final LocalDateTime dateTime) {
        articleService.create(new Article(title, text, dateTime));
    }

    @Nonnull
    private LocalDateTime today(@Nonnull final LocalTime time) {
        return LocalDateTime.of(dateTimeUtil.today(), time);
    }

    @Nonnull
    private LocalDateTime yesterday(@Nonnull final LocalTime time) {
        return LocalDateTime.of(dateTimeUtil.today().minusDays(1), time);
    }
}