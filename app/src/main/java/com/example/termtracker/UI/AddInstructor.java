package com.example.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.termtracker.Database.Repository;
import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Instructor;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.R;

import java.util.List;

public class AddInstructor extends AppCompatActivity {
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhone;
    List<Instructor>  instructorList;
    Repository repository;
    Course currentCourse;
    int courseId;
    int instructorId;
    int termId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        repository = new Repository(getApplication());
        instructorName = findViewById(R.id.instructorName);
        instructorEmail = findViewById(R.id.instructorEmail);
        instructorPhone = findViewById(R.id.instructorPhone);
        termId = getIntent().getIntExtra("selectedTermId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        instructorId = getIntent().getIntExtra("instructorId", -1);
        instructorName.setText(getIntent().getStringExtra("name"));
        instructorEmail.setText(getIntent().getStringExtra("email"));
        instructorPhone.setText(getIntent().getStringExtra("phone"));

        if (instructorId != -1) {
            setTitle("Instructor Details");
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.deleteTerm);
        menuItem.setTitle("Delete instructor");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTerm:
                for (Instructor instructor : repository.getAllInstructors()) {
                    if (instructor.getInstructorID() == instructorId) {
                        repository.delete(instructor);
                    }
                }
        }

        for (Course course : repository.getAllCourses()) {
            if (course.getCourseId() == courseId) {
                currentCourse = course;
            }
        }
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("courseName", currentCourse.getCourseName());
        intent.putExtra("courseStart", currentCourse.getCourseStartDate());
        intent.putExtra("courseEnd", currentCourse.getCourseEndDate());
        intent.putExtra("courseStatus", currentCourse.getCourseStatus());
        intent.putExtra("courseNotes", currentCourse.getCourseNotes());
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void saveInstructorButtonClicked(View view) {
        String name, email, phone;

        name = instructorName.getText().toString();
        email = instructorEmail.getText().toString();
        phone = instructorPhone.getText().toString();

        instructorList = repository.getAllInstructors();

        if (instructorId != -1) {
            Instructor instructor = new Instructor(instructorId, name, email, phone, courseId);
            repository.update(instructor);
        } else {
            int newId = instructorList.size();
            for (Instructor instructor : instructorList) {
                if (instructor.getInstructorID() >= newId) {
                    newId = instructor.getInstructorID();
                }
            }
            Instructor instructor = new Instructor(newId +1, name, email, phone,  courseId);
            repository.insert(instructor);
        }

        for (Course course : repository.getAllCourses()) {
            if (course.getCourseId() == courseId) {
                currentCourse = course;
            }
        }
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("courseName", currentCourse.getCourseName());
        intent.putExtra("courseStart", currentCourse.getCourseStartDate());
        intent.putExtra("courseEnd", currentCourse.getCourseEndDate());
        intent.putExtra("courseStatus", currentCourse.getCourseStatus());
        intent.putExtra("courseNotes", currentCourse.getCourseNotes());
        startActivity(intent);
    }
}
