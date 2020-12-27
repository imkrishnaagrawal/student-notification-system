package com.codingstrings.sns.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.models.WriteUp;
import com.codingstrings.sns.screens.PdfActivity;
import com.codingstrings.sns.utils.http.DownloadQueue;
import com.codingstrings.sns.R;

import java.util.List;

public class WriteUpsAdapter extends RecyclerView.Adapter<WriteUpsAdapter.WriteUpViewHolder> {
    static List<WriteUp> writeUps;


    public static class WriteUpViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView writeupname;
        TextView writeupno;
        TextView writeupgroup;
        TextView writeupsubject;
        private final Context context;


        public WriteUpViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView)itemView.findViewById(R.id.wcv);

            writeupname = (TextView)itemView.findViewById(R.id.nsubject);
            writeupno = (TextView)itemView.findViewById(R.id.wno);
            writeupgroup = (TextView)itemView.findViewById(R.id.wgroup);
            writeupsubject = (TextView)itemView.findViewById(R.id.message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WriteUp writeup = writeUps.get(getPosition());

                    if(new DownloadQueue().Download(writeup._id,1)) {
                        Intent i1 = new Intent(view.getContext(), PdfActivity.class);
                        i1.putExtra("_id", writeup._id);
                        i1.putExtra("number", writeup.number);
                        view.getContext().startActivity(i1);
                    }



                }
            });
        }


    }



    public WriteUpsAdapter(List<WriteUp> writeup){
        this.writeUps = writeup;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public WriteUpViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_writeups, viewGroup, false);
        WriteUpViewHolder pvh = new WriteUpViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(WriteUpViewHolder assignmentViewHolder, int i) {

        assignmentViewHolder.writeupname.setText(writeUps.get(i).name);
        assignmentViewHolder.writeupno.setText(writeUps.get(i).number);
        assignmentViewHolder.writeupgroup.setText(writeUps.get(i).group);
        assignmentViewHolder.writeupsubject.setText(writeUps.get(i).subject);
    }


    @Override
    public int getItemCount() {
        return writeUps.size();
    }



}
