package com.mlmecommerce.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.Home;
import com.mlmecommerce.Fragment.SubCategoryProduct;
import com.mlmecommerce.Fragment.SubCategoryProductList;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.Model.SubCategoryResponse;
import com.mlmecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    Context context;
    List<SubCategoryResponse> categoryResponseList;
    int size;

    public SubCategoryAdapter(Context context, List<SubCategoryResponse> categoryResponseList, int size) {

        this.context = context;
        this.categoryResponseList = categoryResponseList;
        this.size=size;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position == 10) {
            holder.catName.setText("");
            holder.image.setImageResource(R.drawable.more);
        } else {

            holder.catName.setText(categoryResponseList.get(position).getSubCategoryName());
            Picasso.with(context)
                    .load(categoryResponseList.get(position).getSubCategoryImage())
                    .placeholder(R.drawable.defaultimage)
                    .into(holder.image);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SubCategoryProductList subCategoryProductList = new SubCategoryProductList();
                    Bundle bundle = new Bundle();
                    bundle.putString("subCategoryId", ""+categoryResponseList.get(position).getSubCategoryId());
                    subCategoryProductList.setArguments(bundle);
                    ((MainPage)context).loadFragment(subCategoryProductList, true);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView catName;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.categoryIcon);
            catName = (TextView) itemView.findViewById(R.id.categoryName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);


        }
    }
}
