package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Article;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Nonnull
    List<Article> findByOrderByDateTimeDesc();
}
