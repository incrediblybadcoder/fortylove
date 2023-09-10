package ch.fortylove.service;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.persistence.repository.ArticleRepository;
import ch.fortylove.service.util.DatabaseResult;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleService {

    @Nonnull private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(@Nonnull final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Nonnull
    public DatabaseResult<Article> create(@Nonnull final Article article) {
        if (articleRepository.findById(article.getId()).isPresent()) {
            return new DatabaseResult<>("Artikel existiert bereits: " + article.getIdentifier());
        }

        return new DatabaseResult<>(articleRepository.save(article));
    }
}
