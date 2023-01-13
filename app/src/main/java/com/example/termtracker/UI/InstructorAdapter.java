package com.example.termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker.Entities.Instructor;
import com.example.termtracker.R;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {
    public List<Instructor> instructorList;
    private Context context;
    private LayoutInflater inflater;

    public InstructorAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    public class InstructorViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Instructor current = instructorList.get(position);

                    Intent intent = new Intent(context, AddInstructor.class );
                    intent.putExtra("instructorId", current.getInstructorID() );
                    intent.putExtra("position", position);
                    intent.putExtra("courseId", current.getInstructorCourseId());
                    intent.putExtra("name", current.getInstructorName());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("phone", current.getInstructorPhone());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent, false);
        return new InstructorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        Instructor currentInstructor = instructorList.get(position);
        holder.recyclerViewItemLayout.setText(currentInstructor.getInstructorName());
    }

    @Override
    public int getItemCount() {
            return instructorList.size();
    }
    public void instructorSetter(List<Instructor> instructor){
        instructorList = instructor;
        notifyDataSetChanged();
    }
}
