package com.example.termtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Course;

import java.util.List;
@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessments")
    void deleteAllFromAssessment();

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<Assessment> getAssessmentsFromTable();

    @Update
    void update(Assessment assessment);
}
