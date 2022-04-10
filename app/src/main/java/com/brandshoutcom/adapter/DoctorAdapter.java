package com.brandshoutcom.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.brandshoutcom.emedicals.R;
import com.brandshoutcom.pojo.Doctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LANReHNiCs on 1/26/2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    private List<Doctor> listDoc = Collections.EMPTY_LIST;
    private ClickListener clickListener;


    public void setListDoc(List<Doctor> listDoc) {
        this.listDoc = new ArrayList<>(listDoc);
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_doctor, parent, false);
        DoctorHolder holder = new DoctorHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DoctorHolder holder, int position) {
        Doctor doctor = listDoc.get(position);
        holder.tv_doc_job.setText(doctor.getJob());
        holder.tv_doc_name.setText(doctor.getName());
        holder.ratingBar.setRating(doctor.getRating());
    }

    @Override
    public int getItemCount() {
        return listDoc.size();
    }

    class DoctorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView im_doc;
        private TextView tv_doc_job, tv_doc_name;
        private RatingBar ratingBar;
        private CardView card_read_profile,card_call;

        public DoctorHolder(View itemView) {
            super(itemView);
            tv_doc_job = (TextView) itemView.findViewById(R.id.tv_doc_job);
            tv_doc_name = (TextView) itemView.findViewById(R.id.tv_doc_name);
            im_doc = (CircleImageView) itemView.findViewById(R.id.im_doc);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            card_read_profile = (CardView) itemView.findViewById(R.id.card_read_profile);
            card_call = (CardView) itemView.findViewById(R.id.card_call);

            card_read_profile.setOnClickListener(this);
            card_call.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.itemClick(view, getPosition());
        }
    }

    public interface ClickListener {
        void itemClick(View v, int postion);
    }
}
