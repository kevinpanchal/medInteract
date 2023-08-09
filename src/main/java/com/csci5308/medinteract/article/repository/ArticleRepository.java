package com.csci5308.medinteract.article.repository;

import com.csci5308.medinteract.article.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
//    @Query("SELECT a.id, a.isActive, a.doctorId, a.title, a.coverImage, a.createdDate FROM ArticleModel a WHERE a.isActive = true")
    @Query("SELECT a.id, a.createdDate, a.doctorId, a.title, a.coverImage, d.doctorName, d.profilePicture FROM ArticleModel a JOIN DoctorModel d ON a.doctorId = d.id WHERE a.isActive = true ORDER BY a.createdDate DESC")
    List<Object> findAllArticles();

    @Query(value = "SELECT a.id, created_date, doctor_id, title, cover_image, doctor_name, profile_picture FROM articles a JOIN doctor d ON a.doctor_id = d.id WHERE a.is_active = true AND a.id != ?1 ORDER BY a.created_date DESC LIMIT 4;", nativeQuery = true)
    List<Object> fetchLatestArticles(Long id);

    @Query("SELECT a.id, a.createdDate, a.doctorId, a.title, a.coverImage, d.doctorName, d.profilePicture FROM ArticleModel a JOIN DoctorModel d ON a.doctorId = d.id WHERE a.isActive = true AND a.doctorId = ?1 ORDER BY a.createdDate DESC")
    List<Object> findAllDoctorArticles(Long id);

    @Query("SELECT a.id, a.createdDate, a.doctorId, a.title, a.coverImage, d.doctorName, a.content, d.profilePicture FROM ArticleModel a JOIN DoctorModel d ON a.doctorId = d.id WHERE a.isActive = true AND a.id = ?1 ORDER BY a.createdDate DESC")
    List<Object> fetchById(Long id);

    @Modifying
    @Query("UPDATE ArticleModel SET isActive = false WHERE id = ?1")
    void deleteById(Long id);
}
