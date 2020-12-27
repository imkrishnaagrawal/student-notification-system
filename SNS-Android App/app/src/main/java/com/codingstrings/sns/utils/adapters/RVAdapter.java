package com.codingstrings.sns.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.models.Assignment;
import com.codingstrings.sns.screens.PdfActivity;
import com.codingstrings.sns.utils.http.DownloadQueue;
import com.codingstrings.sns.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AssignmentViewHolder> {
    static List<Assignment> assignments;


    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView assignmentname;
        TextView assignmentno;
        TextView unit;
        TextView subject;
        private final Context context;


        public AssignmentViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView)itemView.findViewById(R.id.cv);
            assignmentname = (TextView)itemView.findViewById(R.id.name);
            assignmentno = (TextView)itemView.findViewById(R.id.no);
            unit = (TextView)itemView.findViewById(R.id.unit);
            subject = (TextView)itemView.findViewById(R.id.subject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Assignment assignment = assignments.get(getPosition());


                    if(new DownloadQueue().Download(assignment._id,0)) {
                        Intent i1 = new Intent(view.getContext(), PdfActivity.class);
                        i1.putExtra("_id", assignment._id);
                        i1.putExtra("number", assignment.number);
                        view.getContext().startActivity(i1);
                    }



                }
            });
        }


    }



    public RVAdapter(List<Assignment> assignments){
        this.assignments = assignments;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        AssignmentViewHolder pvh = new AssignmentViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder assignmentViewHolder, int i) {
        assignmentViewHolder.assignmentname.setText(assignments.get(i).name);
        assignmentViewHolder.assignmentno.setText(assignments.get(i).number);
        assignmentViewHolder.unit.setText(assignments.get(i).unit);
        assignmentViewHolder.subject.setText(assignments.get(i).subject);
    }


    @Override
    public int getItemCount() {
        return assignments.size();
    }



}
