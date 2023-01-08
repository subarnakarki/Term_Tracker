package com.example.termtracker.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.termtracker.DAO.AssessmentDAO;
import com.example.termtracker.DAO.CourseDAO;
import com.example.termtracker.DAO.InstructorDAO;
import com.example.termtracker.DAO.TermDAO;
import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Instructor;
import com.example.termtracker.Entities.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 8, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDao();
    public abstract CourseDAO courseDao();
    public abstract AssessmentDAO assessmentDao();
    public abstract InstructorDAO instructorDao();
    private static int NUMBER_OF_THREADS = 6;
    static ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile DatabaseBuilder INSTANCE;
    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE==null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "TermDatabase.db")
                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            TermDAO termDao = INSTANCE.termDao();
            CourseDAO courseDao = INSTANCE.courseDao();
            AssessmentDAO assessmentDao = INSTANCE.assessmentDao();
            InstructorDAO instructorDao = INSTANCE.instructorDao();


            dbWriteExecutor.execute(()->{

                termDao.deleteAllTerms();
                courseDao.deleteAllFromCourseTable();
                assessmentDao.deleteAllFromAssessment();
                instructorDao.deleteAllFromInstructorTable();


                Term termDbTable = new Term(1,"Term 1", "02/01/2022", "02/28/2022");
                termDao.insert(termDbTable);
                Term terDbTable2 = new Term(2,"Term 2", "03/01/2022", "03/28/2022");
                termDao.insert(terDbTable2);


                Course courseDbTable = new Course(101, "Course 1", "02/01/2022","02/28/2022", "Complete","First programming course",1);
                courseDao.insert(courseDbTable);
                Course courseDbTable2 = new Course(102, "Course 2", "03/01/2022","03/30/2022", "In Progress","second programming course",1);
                courseDao.insert(courseDbTable2);
                Course courseDbTable3 = new Course(103, "Course 3", "04/01/2022","04/30/2022", "Dropped","third programming course",2);
                courseDao.insert(courseDbTable3);
                Course courseDbTable11 = new Course(104, "Course 4", "05/01/2022","05/28/2022", "Incomplete","fourth programming course",2);
                courseDao.insert(courseDbTable11);
//                Course courseDbTable12 = new Course(105, "Course 5", "06/01/2022","06/30/2022", "Incomplete","fifth programming course",2);
//                courseDao.insert(courseDbTable12);
//                Course courseDbTable13 = new Course(106, "Course 6", "07/01/2022","07/30/2022", "Incomplete","sixth programming course",2);
//                courseDao.insert(courseDbTable13);
//


                Assessment Assessment = new Assessment(201,"Assessment 1", "Objective", "12/01/2021", "12/01/2021", 101);
                assessmentDao.insert(Assessment);
                Assessment Assessment2 = new Assessment(202,"Assessment 2", "Performance", "01/01/2022",  "01/01/2022", 102);
                assessmentDao.insert(Assessment2);
                Assessment Assessment3 = new Assessment(203,"Assessment 3", "Performance", "02/01/2022",  "03/01/2022", 103);
                assessmentDao.insert(Assessment3);
                Assessment Assessment4 = new Assessment(204,"Assessment 4", "Objective", "03/01/2022", "03/30/2021", 104);
                assessmentDao.insert(Assessment4);
                Assessment Assessment5 = new Assessment(205,"Assessment 5", "Performance", "04/01/2022", "04/30/2021", 101);
                assessmentDao.insert(Assessment5);
                Assessment Assessment6 = new Assessment(206,"Assessment 6", "Objective", "03/01/2022", "03/30/2021", 101);
                assessmentDao.insert(Assessment4);
                Assessment Assessment7 = new Assessment(207,"Assessment 7", "Performance", "04/01/2022", "04/30/2021", 102);
                assessmentDao.insert(Assessment5);



                Instructor Instructor = new Instructor(301, "Instructor 1", "209-333-4587", "ruifern@wgue.edu", 101);
                instructorDao.insert(Instructor);
                Instructor Instructor2 = new Instructor(302, "Instructor 2", "928-333-4587", "johnyb@wgu.edu", 102);
                instructorDao.insert(Instructor2);
                Instructor Instructor3 = new Instructor(303, "Instructor 3", "928-333-4587", "joe@wgu.edu", 103);
                instructorDao.insert(Instructor3);
                Instructor Instructor4 = new Instructor(304, "Instructor 4", "928-333-4587", "kim@wgu.edu", 104);
                instructorDao.insert(Instructor4);
                Instructor Instructor5 = new Instructor(305, "Instructor 5", "928-333-4587", "james@wgu.edu", 101);
                instructorDao.insert(Instructor3);
                Instructor Instructor6 = new Instructor(306, "Instructor 6", "928-333-4587", "jill@wgu.edu", 102);
                instructorDao.insert(Instructor4);

            });
        }

    };
}
