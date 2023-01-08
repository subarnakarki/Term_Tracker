package com.example.termtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termtracker.Entities.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Delete
    void delete(Term term );

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Query("SELECT * FROM terms ORDER BY termId ASC")
    List<Term> getAllTerms();

    @Update
    void update(Term term);
}
