package com.example.termtracker.UI;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.termtracker.Database.Repository;
import com.example.termtracker.Entities.Course;
import com.example.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddCourse extends AppCompatActivity {
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
    Course selectedCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


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
                new DatePickerDialog(AddCourse.this, startDate, myCalendarStart
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
                new DatePickerDialog(AddCourse.this, endDate, myCalendarStart
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

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourse.this,  TermDetails.class );
                intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
                startActivity(intent);
            }
        });
    }

    private void updateLabelStart(EditText editText) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendarStart.getTime()));
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
        if (courseId != -1) {
            Course updateCourse = new Course(courseId, name, start, end, status, note, termId);
            repository.insert(updateCourse);

        } else {

            int newId = courseList.size();
            for (Course course : courseList) {
                if (course.getCourseId() >= newId) {
                    newId = course.getCourseId();
                }
            }
            Course addCourse = new Course(newId + 1, name, start, end, status, note, termId);
            repository.insert(addCourse);
        }

        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra("selectedTermId", getIntent().getIntExtra("selectedTermId", -1));
        startActivity(intent);

    }

    public void fillCourseInfo() {
        courseName.setText(getIntent().getStringExtra("courseName"));
        startDateEditText.setText(getIntent().getStringExtra("courseStart"));
        endDateEditText.setText(getIntent().getStringExtra("courseEnd"));
        statusEditText.setText(getIntent().getStringExtra("courseStatus"));
        notesEditText.setText(getIntent().getStringExtra("courseNotes"));
    }
}
