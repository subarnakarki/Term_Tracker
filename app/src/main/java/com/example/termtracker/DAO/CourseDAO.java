package com.example.termtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Term;

import java.util.List;
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Course course);

    @Delete
    void delete (Course course );

    @Query("DELETE FROM courses")
    void deleteAllFromCourseTable();

    @Query("SELECT * FROM courses ORDER BY courseId ASC") // change to order by todo
    List<Course> getAllCoursesFromTable();
    @Update
    void update(Course course);
}
