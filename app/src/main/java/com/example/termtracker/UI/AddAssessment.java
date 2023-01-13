package com.example.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termtracker.Database.Repository;
import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.Entities.Term;
import com.example.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssessment extends AppCompatActivity {
    EditText assessmentName;
    Spinner assessmentType;
    EditText assessmentStartDate;
    EditText assessmentEndDate;
    Repository repository;
    List<Assessment> assessmentList;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    int courseId, assessmentId, assessmentCourseId;
    List<Assessment> assessmentsInCourse = new ArrayList<>();
    Course currentCourse;
    Assessment selectedAssessment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Spinner assessmentTypes = findViewById(R.id.assessmentType);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentTypes.setAdapter(adapter);
        repository = new Repository(getApplication());
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        courseId = getIntent().getIntExtra("courseId", -1);
        Log.i("info", "course id from adapter is "+ courseId);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);

        assessmentName = findViewById(R.id.assessmentNameTitle);
        assessmentType = findViewById(R.id.assessmentType);
        assessmentStartDate = findViewById(R.id.assessmentStartDate);
        assessmentEndDate = findViewById(R.id.assessmentEndDate);
        if (assessmentId != -1) {
            setTitle("Assessment Details");
        }
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getAssessmentId() == assessmentId) {
                selectedAssessment = assessment;
            }
            if (assessment.getAssessmentCourseId() == courseId) {
                assessmentsInCourse.add(assessment);
            }
        }
        if (selectedAssessment != null) {
            assessmentName.setText(selectedAssessment.getAssessmentTitle());
            assessmentStartDate.setText(selectedAssessment.getAssessmentStartDate());
            assessmentEndDate.setText(selectedAssessment.getAssessmentEndDate());
            String myString = selectedAssessment.getAssessmentType();
            ArrayAdapter myAdapter = (ArrayAdapter) assessmentType.getAdapter();
            int spinnerPosition = myAdapter.getPosition(myString);
            assessmentType.setSelection(spinnerPosition);
        }
        assessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = assessmentStartDate.getText().toString();
                if (info.equals("")) info = "01/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, startDate, myCalendarStart
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
                updateLabelStart(assessmentStartDate);
            }
        };

        assessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = assessmentEndDate.getText().toString();
                if (info.equals("")) info = "01/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, endDate, myCalendarStart
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
                updateLabelStart(assessmentEndDate);
            }
        };

    }

    public void saveAssessmentButtonClicked(View view) {
        String title, type, date, endDate;

        title = assessmentName.getText().toString();
        type = assessmentType.getSelectedItem().toString();
        date = assessmentStartDate.getText().toString();
        endDate = assessmentEndDate.getText().toString();

        assessmentList = repository.getAllAssessments();
        if (assessmentId != -1) {
            Assessment assessment = new Assessment(assessmentId, title, type, date, endDate, courseId);
            repository.update(assessment);
        } else {
            int newId = assessmentList.size();
            for (Assessment assessment : assessmentList) {
                if (assessment.getAssessmentCourseId() == courseId) {
                    Log.i("info", "current course is for assessment is" + courseId);
                }
                if (assessment.getAssessmentId() >= newId) {
                    newId = assessment.getAssessmentId();
                }
            }
            if (assessmentsInCourse != null && assessmentsInCourse.size() >= 5) {
                Toast.makeText(AddAssessment.this, "You cannot have more than 5 assessments in a course", Toast.LENGTH_LONG).show();
            } else {
                Assessment assessment = new Assessment(newId +1, title, type, date, endDate,  courseId);
                repository.insert(assessment);
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_details, menu);
        MenuItem menuItem = menu.findItem(R.id.deleteAssessment);
        menuItem.setTitle("Delete Assessment");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessment:
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentId() == assessmentId) {
                        repository.delete(assessment);
                    }
                }
            case R.id.notifystart:
                String dateFromScreen = assessmentStartDate.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent notifyIntent = new Intent(AddAssessment.this, MyReceiver.class);
                notifyIntent.putExtra("key", dateFromScreen + " Assessment has started");
                PendingIntent sender = PendingIntent.getBroadcast(AddAssessment.this, ++MainActivity.numAlert, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyend:
                String dateFromScreenEnd = assessmentEndDate.getText().toString();
                String myFormatEnd = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                Date myDateEnd = null;
                try {
                    myDateEnd = sdfEnd.parse(dateFromScreenEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerEnd = myDateEnd.getTime();
                Intent notifyIntentEnd = new Intent(AddAssessment.this, MyReceiver.class);
                notifyIntentEnd.putExtra("key", dateFromScreenEnd + " Assessment has ended");
                PendingIntent senderEnd = PendingIntent.getBroadcast(AddAssessment.this, ++MainActivity.numAlert, notifyIntentEnd, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, triggerEnd, senderEnd);
                return true;
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

    private void updateLabelStart(EditText editText) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendarStart.getTime()));
    }
}