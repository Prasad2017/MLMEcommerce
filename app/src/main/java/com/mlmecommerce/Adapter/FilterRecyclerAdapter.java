package com.mlmecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.Model.MainFilterModel;
import com.mlmecommerce.R;

import java.util.ArrayList;
import java.util.List;


public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.MyViewHolder> {

    Context context;
    List<MainFilterModel> filterModels;
    OnItemClickListener mItemClickListener;
    private int resource;

    public FilterRecyclerAdapter(Context context, int filter_list_item_layout, List<MainFilterModel> filterModels) {

        this.context = context;
        this.filterModels = filterModels;
        this.resource = filter_list_item_layout;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(this.context)
                .inflate(resource, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.parentView.setSelected(filterModels.get(position).isSelected());

        if (holder.personName.isSelected()) {
            if (filterModels.get(position).getTitle().equals("Category")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.sizeblack, 0,0 );
                holder.personName.setText("Category");
            } else if (filterModels.get(position).getTitle().equals("Brand")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizeblack, 0, 0);
                holder.personName.setText("Brand");
            } else if (filterModels.get(position).getTitle().equals("Price")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizeblack, 0, 0);
                holder.personName.setText("Price");
            } else if (filterModels.get(position).getTitle().equals("Discount")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizeblack, 0, 0);
                holder.personName.setText("Discount");
            }
        } else {
            if (filterModels.get(position).getTitle().equals("Category")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.sizepink, 0,0 );
                holder.personName.setText("Category");
            } else if (filterModels.get(position).getTitle().equals("Brand")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizepink, 0, 0);
                holder.personName.setText("Brand");
            } else if (filterModels.get(position).getTitle().equals("Price")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizepink, 0, 0);
                holder.personName.setText("Price");
            } else if (filterModels.get(position).getTitle().equals("Discount")) {
                holder.personName.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.sizepink, 0, 0);
                holder.personName.setText("Discount");
            }
        }



    }

    @Override
    public int getItemCount() {
        return filterModels.size();
    }

    public void setItemSelected(int position) {
        for (MainFilterModel filterModel : filterModels) {
            filterModel.setIsSelected(false);


        }
        if (position != -1) {
            filterModels.get(position).setIsSelected(true);
            notifyDataSetChanged();
        }

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView personName;
        public View parentView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            personName = (TextView) itemView.findViewById(R.id.txt_item_list_title);
            parentView = itemView;

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
}
