package com.example.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.termtracker.Database.Repository;
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

public class TermDetails extends AppCompatActivity {
    Repository repository;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    EditText termName;
    EditText startDateEditText;
    EditText endDateEditText;
    Term selectedTerm;
    int selectedTermId;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseAdapter mCourseAdapter;
    List<Course> allCourses;
    List<Course> coursesInTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        termName = findViewById(R.id.editNameTextView);
        startDateEditText = findViewById(R.id.editStartDateTextView);
        endDateEditText = findViewById(R.id.editEndDateTextView);
        selectedTermId = getIntent().getIntExtra("selectedTermId", -1);
        Log.i("info", String.valueOf(selectedTermId));
        for (Term term : repository.getAllTerms()) {
            if (term.getTermId() == selectedTermId) {
                selectedTerm = term;
            }
        }
        if (selectedTerm != null) {
            termName.setText(selectedTerm.getTermTitle());
            startDateEditText.setText(selectedTerm.getTermStartDate());
            endDateEditText.setText(selectedTerm.getTermEndDate());
        }
        setRecyclerViewAndAdapter();
        allCourses = repository.getAllCourses();
        coursesInTerm = new ArrayList<>();
        for (Course course : allCourses) {

            if (course.getCourseTermId() == selectedTermId) {
                Log.i("Info", String.valueOf("inside!!!"));
                Log.i("Info", String.valueOf(selectedTermId));
                Log.i("Info", String.valueOf(course.getCourseTermId()));
                coursesInTerm.add(course);
            }
        }

        Button button = findViewById(R.id.addCourseButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Info", "Hello " + selectedTermId);
                Intent intent = new Intent(TermDetails.this,  AddCourse.class );
                intent.putExtra("selectedTermId", selectedTermId);
                startActivity(intent);
            }
        });

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
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
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
                new DatePickerDialog(TermDetails.this, endDate, myCalendarStart
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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteTerm:
                coursesInTerm = new ArrayList<>();
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseTermId() == selectedTermId) {
                        coursesInTerm.add(course);
                    }
                }
                if (coursesInTerm.isEmpty()) {
                    repository.delete(selectedTerm);
                } else {
                    Toast.makeText(TermDetails.this, "Please remove all courses in term before deleting", Toast.LENGTH_LONG).show();
                }

        }
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    public void termDetailsSaveButtonClicked(View view) {
        String name = termName.getText().toString();
        String start = startDateEditText.getText().toString();
        String end = endDateEditText.getText().toString();
// todo may need validation for empty fields
        Log.i("info", String.valueOf(getIntent().getIntExtra("termId", -1)));

        Term updatedTerm = new Term(selectedTermId, name, start, end);
        repository.update(updatedTerm);
        Log.i("Ingo", "HEllo subarna");
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

//    public void addCourseButtonClicked(View view) {
//        Log.i("Info", "Hello " + selectedTermId);
////        Intent intent = new Intent(this, AddCourse.class);
////        intent.putExtra("selectedTermId", selectedTermId);
////        startActivity(intent);
//    }
    public void setRecyclerViewAndAdapter() {
        coursesInTerm = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {

            if (course.getCourseTermId() == selectedTermId) {
                Log.i("Info", String.valueOf("inside!!!"));
                Log.i("Info", String.valueOf(selectedTermId));
                Log.i("Info", String.valueOf(course.getCourseTermId()));
                coursesInTerm.add(course);
            }
        }
        mRecyclerView = findViewById(R.id.coursesRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mCourseAdapter = new CourseAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCourseAdapter);
        mCourseAdapter.courseSetter(coursesInTerm);
    }

    private void updateLabelStart(EditText editText) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendarStart.getTime()));
    }
}