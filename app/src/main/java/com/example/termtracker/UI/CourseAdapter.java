package com.example.termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker.Entities.Course;
import com.example.termtracker.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    public List<Course> mCourseList;
    private Context context;
    private LayoutInflater inflater;


    public CourseAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Course currentCourse = mCourseList.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("selectedTermId", currentCourse.getCourseTermId());
                    intent.putExtra("courseId", currentCourse.getCourseId());
                    intent.putExtra("courseName", currentCourse.getCourseName());
                    intent.putExtra("courseStart", currentCourse.getCourseStartDate());
                    intent.putExtra("courseEnd", currentCourse.getCourseEndDate());
                    intent.putExtra("courseStatus", currentCourse.getCourseStatus());
                    intent.putExtra("courseNotes", currentCourse.getCourseNotes());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent,false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = mCourseList.get(position);
        holder.recyclerViewItemLayout.setText(currentCourse.getCourseName());
    }

    @Override
    public int getItemCount() {
        if (mCourseList != null) {
            return mCourseList.size();
        } else {
            return 0;
        }
    }

    public Course getCourseAt(int position) {
        return mCourseList.get(position);
    }

    public void courseSetter(List<Course> courses){
        mCourseList = courses;
        notifyDataSetChanged();
    }

}
