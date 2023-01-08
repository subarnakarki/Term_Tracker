package com.example.termtracker.UI;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker.Entities.Term;
import com.example.termtracker.R;
//import com.example.termtracker.UI.EditTerm;
import com.example.termtracker.Entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    public static List<Term> mTermsList;
    private static Context context;
    private LayoutInflater inflater;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public static class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView recyclerViewItemLayout;
        //todo add course here

        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout); // -> TEXT ON XML CARD
            //todo add course here

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Term currentTerm = mTermsList.get(position);

                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("selectedTermId", currentTerm.getTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }
    }


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //todo add if statement?
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term currentTerm = mTermsList.get(position);
        holder.recyclerViewItemLayout.setText(currentTerm.getTermTitle());

        //todo code for courses here
    }

    @Override
    public int getItemCount() {
        return mTermsList.size();
        // todo add if statement?
    }

    public Term getTermAt(int position) {
        return mTermsList.get(position);
    }

    public void termsSetter(List<Term> terms) {
        mTermsList = terms;
        notifyDataSetChanged();
    }
}

