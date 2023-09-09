package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "articles")
public class Article extends AbstractEntity implements HasIdentifier {

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "picture")
    private byte[] picture;

    protected Article() {
    }

    public Article(@Nonnull final String title,
                   @Nonnull final String text,
                   @Nonnull final LocalDateTime dateTime) {
        super(UUID.randomUUID());
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Article(@Nonnull final String title,
                   @Nonnull final String text,
                   @Nonnull final LocalDateTime dateTime,
                   final byte[] picture) {
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.picture = picture;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull final String title) {
        this.title = title;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    public void setText(@Nonnull final String text) {
        this.text = text;
    }

    @Nonnull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nonnull final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(final byte[] picture) {
        this.picture = picture;
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return title + " " + dateTime;
    }
}
