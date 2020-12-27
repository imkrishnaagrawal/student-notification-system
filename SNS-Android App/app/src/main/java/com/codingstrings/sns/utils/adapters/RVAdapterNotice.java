package com.codingstrings.sns.utils.adapters;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.models.Notice;
import com.codingstrings.sns.screens.PdfActivity;
import com.codingstrings.sns.utils.http.DownloadQueue;
import com.codingstrings.sns.R;

import java.util.List;

public class RVAdapterNotice extends RecyclerView.Adapter<RVAdapterNotice.NoticeViewHolder> {
    static List<Notice> notices;


    public static class NoticeViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView nsubject;
        TextView nmessage;

        private final Context context;


        NoticeViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            cv = (CardView)itemView.findViewById(R.id.ncv);
            nsubject = (TextView)itemView.findViewById(R.id.nsubject);
            nmessage = (TextView)itemView.findViewById(R.id.nmessage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notice notice = notices.get(getPosition());



                    if(new DownloadQueue().Download(notice._id,2)) {
                        Intent i1 = new Intent(view.getContext(), PdfActivity.class);
                        i1.putExtra("_id", notice._id);
                        view.getContext().startActivity(i1);
                    }



                }
            });
        }


    }



    public RVAdapterNotice(List<Notice> notices){
        this.notices = notices;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice, viewGroup, false);
        NoticeViewHolder pvh = new NoticeViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder noticeViewHolder, int i) {
        noticeViewHolder.nmessage.setText(notices.get(i).message);
        noticeViewHolder.nsubject.setText(notices.get(i).msubject);
    }


    @Override
    public int getItemCount() {
        return notices.size();
    }



}
