package com.example.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.termtracker.Database.Repository;
import com.example.termtracker.Entities.Term;
import com.example.termtracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TermAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    Repository repository;

    public List<Term> termTableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        repository = new Repository(getApplication());
        getTermsList();
        buildRecyclerView();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, AddTerm.class);
                startActivity(intent);
            }
        });
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Term term = new Term( "Term Three", "11/22/2022", "5/22/2023");
//        switch(item.getItemId()) {
//            case R.id.addSampleTerm:
//                Repository repository = new Repository(getApplication());
//                repository.insert(term);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    public void getTermsList() {
        termTableList = repository.getAllTerms();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_terms);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);
        mAdapter = new TermAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.termsSetter(repository.getAllTerms());
    }
}