package com.Bootcamp19.RestFullAPI.repository;

import com.Bootcamp19.RestFullAPI.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long> {
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);

    @Query("select c from CharAnalysis c where c.totalHuruf >= :min")
    List<CharAnalysis> findMinimal(@Param("min") int min);

    @Query(value = "select * from mst_char_analysis order by created_at desc limit 5", nativeQuery = true)
    List<CharAnalysis> findTerbaru();
}
