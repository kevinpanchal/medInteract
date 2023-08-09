package com.csci5308.medinteract.article.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="articles")
public class ArticleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_generator")
    @SequenceGenerator(name="article_generator", sequenceName = "article_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @NotBlank
    @NotNull
    private Long doctorId;

    @NotBlank
    @NotNull
    private String coverImage;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] content;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @NotBlank
    @NotNull
    public LocalDateTime createdDate;

    @NotBlank
    @NotNull
    private Boolean isActive;
}
