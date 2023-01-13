package com.example.termtracker.Database;

import android.app.Application;

import com.example.termtracker.DAO.AssessmentDAO;
import com.example.termtracker.DAO.CourseDAO;
import com.example.termtracker.DAO.InstructorDAO;
import com.example.termtracker.DAO.TermDAO;
import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Instructor;
import com.example.termtracker.Entities.Term;

import java.util.List;

public class Repository {
    private TermDAO mTermDao;
    private CourseDAO mCourseDao;
    private AssessmentDAO mAssessmentDao;
    private InstructorDAO mInstructorDao;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mallAssessments;
    private List<Instructor> mallInstructors;

    public Repository(Application application){
        DatabaseBuilder database = DatabaseBuilder.getDatabase(application);
        mTermDao = database.termDao();
        mCourseDao = database.courseDao();
        mAssessmentDao = database.assessmentDao();
        mInstructorDao = database.instructorDao();
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

    public void update(Course course) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mCourseDao.update(course);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
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


    public List<Assessment> getAllAssessments() {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mallAssessments = mAssessmentDao.getAssessmentsFromTable();
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mallAssessments;
    }

    public void insert(Assessment assessment) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mAssessmentDao.update(assessment);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mAssessmentDao.delete(assessment);
        });
    }

    public void deleteAllAssessments() {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAllFromAssessment();
        });
    }





    public List<Instructor> getAllInstructors(){
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mallInstructors = mInstructorDao.getInstructorsFromTable();
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mallInstructors;
    }


    public void insert(Instructor instructor) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mInstructorDao.insert(instructor);
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Instructor instructor) {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mInstructorDao.delete(instructor);
        });
    }

    public void update(Instructor instructor) {
        DatabaseBuilder.dbWriteExecutor.execute(()->{
            mInstructorDao.update(instructor);
        });
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAllInstructors() {
        DatabaseBuilder.dbWriteExecutor.execute(() -> {
            mInstructorDao.deleteAllFromInstructorTable();
        });
    }
    
}
