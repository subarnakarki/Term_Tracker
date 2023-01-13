package com.example.termtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Instructor;

import java.util.List;
@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("DELETE FROM instructors")
    void deleteAllFromInstructorTable();

    @Query("SELECT * FROM instructors ORDER BY instructorID ASC")
    List<Instructor> getInstructorsFromTable();

    @Update
    void update(Instructor instructor);
}
