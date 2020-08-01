package com.mlmecommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.mlmecommerce.Model.ProductImageResponse;
import com.mlmecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductImageAdapter extends PagerAdapter {

    private ArrayList<ProductImageResponse> arrayList;
    private LayoutInflater inflater;
    private Context context;

    public ProductImageAdapter(Context context, ArrayList<ProductImageResponse> arr) {

        this.context = context;
        this.arrayList= arr;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View itemView = inflater.inflate(R.layout.product_image_list, view, false);
        // assert imageLayout != null;
        ImageView imageView = itemView.findViewById(R.id.imageView);
        try {
            Picasso.with(context)
                    .load(arrayList.get(position).getProductImagePath())
                    .placeholder(R.drawable.defaultimage)
                    .into(imageView);
            view.addView(itemView);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

