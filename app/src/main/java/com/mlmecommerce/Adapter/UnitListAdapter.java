package com.mlmecommerce.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.ProductDetails;
import com.mlmecommerce.Model.SizeWiseProductResponse;
import com.mlmecommerce.R;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.MyViewHolder> implements View.OnClickListener {

    List<SizeWiseProductResponse> sizeWiseProductResponseList;
    Context context;
    public static int pos;


    public UnitListAdapter(Context context, List<SizeWiseProductResponse> sizeWiseProductResponseList) {
        this.context = context;
        this.sizeWiseProductResponseList = sizeWiseProductResponseList;
        pos = -1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_list, parent, false);
        // set the view's size, margins, padding and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{5, 5, 5, 5, 5, 5, 5, 5});
        if (position == pos) {
            holder.size.setTextColor(Color.WHITE);
            shape.setColor(holder.colorPrimary);
        } else {
            holder.size.setTextColor(Color.BLACK);
            shape.setColor(holder.gray);
        }
        holder.size.setText(sizeWiseProductResponseList.get(position).getUnitName().trim());
        holder.size.setBackgroundDrawable(shape);
        holder.size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;

                try {

                    double discountPercentage = Double.parseDouble(sizeWiseProductResponseList.get(position).getSizeWiseProductAmount()) - Double.parseDouble(sizeWiseProductResponseList.get(position).getSizeWiseProductFinalAmount());
                    Log.d("percentage", discountPercentage + "");
                    discountPercentage = (discountPercentage / Double.parseDouble(sizeWiseProductResponseList.get(position).getSizeWiseProductAmount())) * 100;
                    if ((int) Math.round(discountPercentage) > 0) {
                        ProductDetails.offerPrice.setText(((int) Math.round(discountPercentage) + "% Off"));
                    }
                    ProductDetails.discountPrice.setText("You Pay "+MainPage.currency + " " + sizeWiseProductResponseList.get(position).getSizeWiseProductFinalAmount());
                    ProductDetails.productPrice.setText(MainPage.currency + " " + sizeWiseProductResponseList.get(position).getSizeWiseProductAmount());
                    ProductDetails.productPrice.setPaintFlags(ProductDetails.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ProductDetails.productStatus.setText(sizeWiseProductResponseList.get(position).getSizeWiseProductStatus());

                if (sizeWiseProductResponseList.get(position).getSizeWiseProductStatus().equals("Out of Stock")){
                    ProductDetails.buyNow.setVisibility(View.GONE);
                    ProductDetails.addToCart.setText("Out of Stock");
                    ProductDetails.addToCart.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    ProductDetails.addToCart.setTextColor(context.getResources().getColor(R.color.light_black));
                    ProductDetails.addToCart.setClickable(false);
                } else if (sizeWiseProductResponseList.get(position).getSizeWiseProductStatus().equals("Coming Soon")){
                    ProductDetails.buyNow.setVisibility(View.GONE);
                    ProductDetails.addToCart.setText("Coming Soon");
                    ProductDetails.addToCart.setBackgroundColor(context.getResources().getColor(R.color.green_100));
                    ProductDetails.addToCart.setTextColor(context.getResources().getColor(R.color.white));
                    ProductDetails.addToCart.setClickable(false);
                } else {
                    ProductDetails.buyNow.setVisibility(View.VISIBLE);
                    ProductDetails.addToCart.setText("Add To Cart");
                    ProductDetails.addToCart.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    ProductDetails.addToCart.setTextColor(context.getResources().getColor(R.color.white));
                }

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sizeWiseProductResponseList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        @BindView(R.id.size)
        TextView size;
        @BindColor(R.color.gray)
        int gray;
        @BindColor(R.color.colorPrimary)
        int colorPrimary;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            ButterKnife.bind(this, itemView);

        }
    }
}

