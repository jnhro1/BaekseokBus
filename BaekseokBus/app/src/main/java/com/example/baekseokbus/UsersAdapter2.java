package com.example.baekseokbus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class UsersAdapter2 extends RecyclerView.Adapter<UsersAdapter2.CustomViewHolder> {

    private ArrayList<PersonalData2> mList = null;
    private Activity context = null;


    public UsersAdapter2(Activity context, ArrayList<PersonalData2> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView content;
        protected TextView name;
        protected TextView country;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
            this.content = (TextView) view.findViewById(R.id.textView_list_content);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.country = (TextView) view.findViewById(R.id.textView_list_country);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complain_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.content.setText(mList.get(position).getMember_content());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.country.setText(mList.get(position).getMember_country());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}