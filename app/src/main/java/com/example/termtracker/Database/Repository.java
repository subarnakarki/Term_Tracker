package com.example.termtracker.Database;

import android.app.Application;

import com.example.termtracker.DAO.CourseDAO;
import com.example.termtracker.DAO.TermDAO;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Term;

import java.util.List;

public class Repository {
    private TermDAO mTermDao;
    private CourseDAO mCourseDao;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    public Repository(Application application){
        DatabaseBuilder database = DatabaseBuilder.getDatabase(application);
        mTermDao = database.termDao();
        mCourseDao = database.courseDao();
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Term> getAllTerms() {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mAllTerms = mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mTermDao.update(term);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public void delete(Term term) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mTermDao.delete(term);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void deleteAllTerms(Term term) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mTermDao.deleteAllTerms();
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public List<Course> getAllCourses() {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCoursesFromTable();
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mCourseDao.delete(course);
        });
    }

    public void deleteAllCourses() {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mCourseDao.deleteAllFromCourseTable();
        });
    }

}
