package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime,Long > {
    List<ShowTime> findAllByMovie_IdAndStartTimeGreaterThanEqualOrderByStartTimeAsc(Long id, Date currentDate);
    List<ShowTime> findAllByMovie(Movie movie);

    @Query("SELECT CASE WHEN COUNT(st) > 0 THEN true ELSE false END " +
            "FROM ShowTime st " +
            "WHERE st.screen.id = :screenId " +
            "AND st.startTime < :endTime " +
            "AND st.endTime > :startTime")
    boolean existsOverlappingShowTime(@Param("screenId") Long screenId,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);

    @Query("SELECT CASE WHEN COUNT(st) > 0 THEN true ELSE false END " +
            "FROM ShowTime st " +
            "WHERE st.screen.id = :screenId " +
            "AND st.id <> :showTimeID " +
            "AND st.startTime < :endTime " +
            "AND st.endTime > :startTime")
    boolean existsOverlappingShowTimeExceptId(@Param("screenId") Long screenId,
                                              @Param("showTimeID") Long showTimeID,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);
}


