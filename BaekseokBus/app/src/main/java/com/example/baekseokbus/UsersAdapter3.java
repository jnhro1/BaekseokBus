package com.example.baekseokbus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class UsersAdapter3 extends RecyclerView.Adapter<UsersAdapter3.CustomViewHolder> {

    private ArrayList<PersonalData3> mList = null;
    private Activity context = null;


    public UsersAdapter3(Activity context, ArrayList<PersonalData3> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView name;
        protected TextView country;
        protected TextView clock;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.country = (TextView) view.findViewById(R.id.textView_list_country);
            this.clock = (TextView) view.findViewById(R.id.textView_list_clock);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reservation_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.country.setText(mList.get(position).getMember_country());
        viewholder.clock.setText(mList.get(position).getMember_clock());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}