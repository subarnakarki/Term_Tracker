package com.example.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.termtracker.Database.Repository;
import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Instructor;
import com.example.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    Repository repository;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    EditText courseName;
    EditText startDateEditText;
    EditText endDateEditText;
    EditText statusEditText;
    EditText notesEditText;
    int termId;
    int courseId;
    List<Course> courseList;
    List<Assessment> assessmentList;
    List<Instructor> instructorList;
    Course selectedCourse;
    RecyclerView assessmentRecyclerView;
    RecyclerView instructorRecyclerView;
    RecyclerView.LayoutManager assessmentLayoutManager;
    RecyclerView.LayoutManager instructorLayoutManager;
    AssessmentAdapter assessmentAdapter;
    InstructorAdapter instructorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        courseName = findViewById(R.id.courseNameTitle);
        startDateEditText = findViewById(R.id.courseStartDate);
        endDateEditText = findViewById(R.id.courseEndDate);
        statusEditText = findViewById(R.id.statusEditText);
        notesEditText = findViewById(R.id.notesEditText);
        termId = getIntent().getIntExtra("selectedTermId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        courseList = repository.getAllCourses();

        fillCourseInfo();
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = startDateEditText.getText().toString();
                if (info.equals("")) info = "01/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart(startDateEditText);
            }
        };

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = endDateEditText.getText().toString();
                if (info.equals("")) info = "01/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart(endDateEditText);
            }
        };

        Button saveCourseButton = findViewById(R.id.saveCourseButton);
        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = courseName.getText().toString();
                String start = startDateEditText.getText().toString();
                String end = endDateEditText.getText().toString();
                String status = statusEditText.getText().toString();
                String note = notesEditText.getText().toString();
                if (note.trim().isEmpty()) {
                    note = " ";
                }
                if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() || status.trim().isEmpty() || note.trim().isEmpty()) {

                    AlertDialog alertDialog = new AlertDialog.Builder(CourseDetails.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Ensure all fields are filled");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
                if (courseId != -1) {
                    Log.i("info", "updateing course!");
                    Course updateCourse = new Course(courseId, name, start, end, status, note, termId);
                    repository.update(updateCourse);
                } else {
                    Log.i("info", "adding new course!");
                    Course addCourse = new Course(courseId, name, start, end, status, note, termId);
                    repository.insert(addCourse);
                }

                Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        setAssessmentRecyclerViewAndAdapter();
        setInstructorRecyclerViewAndAdapter();
    }

    private void updateLabelStart(EditText editText) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendarStart.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_details, menu);
        MenuItem menuItem = menu.findItem(R.id.deleteCourse);
        menuItem.setTitle("Delete Course");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteCourse:
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseId() == courseId) {
                        repository.delete(course);
                    }
                }
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentCourseId() == courseId) {
                        repository.delete(assessment);
                    }
                }
                for (Instructor instructor : repository.getAllInstructors()) {
                    if (instructor.getInstructorCourseId() == courseId) {
                        repository.delete(instructor);
                    }
                }
                Intent intent = new Intent(this, TermDetails.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
                startActivity(intent);
                return true;
            case R.id.sendNotification:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notesEditText.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for course " + courseName.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifystart:
                String dateFromScreen = startDateEditText.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent notifyIntent = new Intent(CourseDetails.this, MyReceiver.class);
                notifyIntent.putExtra("key", dateFromScreen + " Course has started");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyend:
                String dateFromScreenEnd = endDateEditText.getText().toString();
                String myFormatEnd = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                Date myDateEnd = null;
                try {
                    myDateEnd = sdfEnd.parse(dateFromScreenEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerEnd = myDateEnd.getTime();
                Intent notifyIntentEnd = new Intent(CourseDetails.this, MyReceiver.class);
                notifyIntentEnd.putExtra("key", dateFromScreenEnd + " Course has ended");
                PendingIntent senderEnd = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, notifyIntentEnd, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, triggerEnd, senderEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addCourseSaveButtonClicked(View view) {
        Log.i("info", "Save clicked!");
        String name = courseName.getText().toString();
        String start = startDateEditText.getText().toString();
        String end = endDateEditText.getText().toString();
        String status = statusEditText.getText().toString();
        String note = notesEditText.getText().toString();
        if (note.trim().isEmpty()) {
            note = " ";
        }
        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() || status.trim().isEmpty() || note.trim().isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Ensure all fields are filled");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        if (courseId == -1) {
            Course addCourse = new Course(courseId, name, start, end, status, note, termId);
            repository.insert(addCourse);
        } else {
            Course updateCourse = new Course(courseId, name, start, end, status, note, termId);
            repository.update(updateCourse);
        }
        Intent intent = new Intent(this, TermList.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void fillCourseInfo() {
        courseName.setText(getIntent().getStringExtra("courseName"));
        startDateEditText.setText(getIntent().getStringExtra("courseStart"));
        endDateEditText.setText(getIntent().getStringExtra("courseEnd"));
        statusEditText.setText(getIntent().getStringExtra("courseStatus"));
        notesEditText.setText(getIntent().getStringExtra("courseNotes"));
    }

    public void setAssessmentRecyclerViewAndAdapter() {
        List<Assessment> assessmentInCourse = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getAssessmentCourseId() == courseId) {
                assessmentInCourse.add(assessment);
            }
        }
        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentLayoutManager = new LinearLayoutManager(this);
        assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setLayoutManager(assessmentLayoutManager);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentAdapter.assessmentSetter(assessmentInCourse);

    }
    public void setInstructorRecyclerViewAndAdapter() {
        List<Instructor> instructorsInCourse = new ArrayList<>();
        for (Instructor instructor : repository.getAllInstructors()) {
            if (instructor.getInstructorCourseId() == courseId) {
                instructorsInCourse.add(instructor);
            }
        }
        instructorRecyclerView = findViewById(R.id.instructorRecyclerView);
        instructorLayoutManager = new LinearLayoutManager(this);
        instructorAdapter = new InstructorAdapter(this);
        instructorRecyclerView.setLayoutManager(instructorLayoutManager);
        instructorRecyclerView.setAdapter(instructorAdapter);
        instructorAdapter.instructorSetter(instructorsInCourse);
    }
    public void getSelectedCourse() {
        for (Course course : courseList) {
            if (courseId == course.getCourseId()) {
                selectedCourse = course;
            }
        }

        courseName.setText(selectedCourse.getCourseName());
        startDateEditText.setText(selectedCourse.getCourseStartDate());
        endDateEditText.setText(selectedCourse.getCourseEndDate());
        statusEditText.setText(selectedCourse.getCourseStatus());
        notesEditText.setText(selectedCourse.getCourseNotes());
        termId = selectedCourse.getCourseTermId();
    }
    public void addInstructorButtonClicked(View view) {
        Intent intent = new Intent(CourseDetails.this,  AddInstructor.class );
        intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void addAssessmentButtonClicked(View view) {
        Intent intent = new Intent(CourseDetails.this,  AddAssessment.class );
        intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }
}
