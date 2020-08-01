package com.mlmecommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.SubCategoryProduct;
import com.mlmecommerce.Model.SizeWiseProductResponse;
import com.mlmecommerce.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.VariantsViewHolder> {

    Context context;
    List<SizeWiseProductResponse> sizeWiseProductResponseList;
    int parentPosition;



    public ProductSizeAdapter(Context context, List<SizeWiseProductResponse> sizeWiseProductResponseList, int parentPosition) {
        this.context = context;
        this.sizeWiseProductResponseList = sizeWiseProductResponseList;
        this.parentPosition = parentPosition;

    }

    @Override
    public VariantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.size_list_items, null);
        VariantsViewHolder variantsViewHolder = new VariantsViewHolder(view);
        return variantsViewHolder;
    }

    @Override
    public void onBindViewHolder(VariantsViewHolder holder, int position) {

        holder.textViewList.get(0).setText(sizeWiseProductResponseList.get(position).getUnitName());
        holder.textViewList.get(1).setText(MainPage.currency+" "+sizeWiseProductResponseList.get(position).getSizeWiseProductAmount());
        Log.d("indexIS",ProductAdapter.index+"");

        if (ProductAdapter.index == position) {
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.textViewList.get(0).setTextColor(context.getResources().getColor(R.color.white));
            holder.textViewList.get(1).setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.textViewList.get(0).setTextColor(context.getResources().getColor(R.color.light_black));
            holder.textViewList.get(1).setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return sizeWiseProductResponseList.size();
    }

    public class VariantsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.relativeLayout)
        RelativeLayout relativeLayout;
        @BindViews({R.id.size, R.id.price})
        List<TextView> textViewList;


        public VariantsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("variantlayoutClick",parentPosition+" called "+getAdapterPosition());
                    SubCategoryProduct.selectedPosList.set(parentPosition,getAdapterPosition());
                    SubCategoryProduct.selectedPosHashMap.put(SubCategoryProduct.viewPagerCurrentPos, SubCategoryProduct.selectedPosList);
                    ProductAdapter.popupwindow_obj.dismiss();
                    ProductAdapter.isFirstTime=false;
                    Log.d("instanceSIZE",SubCategoryProduct.getInstances().size()+""+SubCategoryProduct.viewPagerCurrentPos);
                    (SubCategoryProduct.getInstances().get(0)).notifyDataSetChanged();
                }
            });

        }
    }
}
