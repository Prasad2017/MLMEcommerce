package com.mlmecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Model.BrandResponse;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.R;

import java.util.List;

public class BrandValRecyclerAdapter extends RecyclerView.Adapter<BrandValRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<BrandResponse> categoryResponseList;
    private int resource;
    private int type;
    OnItemClickListener mItemClickListener;


    public BrandValRecyclerAdapter(Context context, int filter_list_item_layout, List<BrandResponse> categoryResponseList, int type) {

        this.context = context;
        this.resource = filter_list_item_layout;
        this.categoryResponseList = categoryResponseList;
        this.type = type;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(this.context)
                .inflate(resource, viewGroup, false);
        return new MyViewHolder(v,this.type);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.subCategoryName.setText(categoryResponseList.get(position).getBrandName());
        holder.cbSelected.setChecked(categoryResponseList.get(position).isChecked());

    }

    @Override
    public int getItemCount() {
        return categoryResponseList.size();
    }

    public void setItemSelected(int position) {
        if (position != -1) {
            categoryResponseList.get(position).setChecked(!categoryResponseList.get(position).isChecked());
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView subCategoryName;
        CheckBox cbSelected;
        View colorView;
        public int type;

        MyViewHolder(View itemView, int type) {
            super(itemView);
            itemView.setOnClickListener(this);
            subCategoryName = (TextView) itemView.findViewById(R.id.txt_item_list_title);
            cbSelected = (CheckBox) itemView.findViewById(R.id.cbSelected);
            colorView = itemView.findViewById(R.id.colored_bar);
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
}

