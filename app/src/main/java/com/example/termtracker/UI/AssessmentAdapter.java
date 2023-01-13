package com.example.termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker.Entities.Assessment;
import com.example.termtracker.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    public List<Assessment> assessmentList;
    private Context context;
    private LayoutInflater inflater;

    public AssessmentAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        Assessment currentAssessment = assessmentList.get(position);
        holder.recyclerViewItemLayout.setText(currentAssessment.getAssessmentTitle());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        public TextView recyclerViewItemLayout;
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Assessment current  = assessmentList.get(position);
                    Intent intent = new Intent(context, AddAssessment.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("position", position);
                    intent.putExtra("assessCourseId",current.getAssessmentCourseId() );
                    intent.putExtra("courseId",current.getAssessmentCourseId() );
                    context.startActivity(intent);
                }
            });
        }
    }

    public void assessmentSetter (List<Assessment> assessments) {
        assessmentList = assessments;
        notifyDataSetChanged();
    }

    public Assessment getAssessmentAt(int position){
        return assessmentList.get(position);
    }
}
