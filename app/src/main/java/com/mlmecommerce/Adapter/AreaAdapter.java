package com.mlmecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Model.AreaResponse;
import com.mlmecommerce.Model.CityResponse;
import com.mlmecommerce.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.MyViewHolder> {

    Context context;
    List<AreaResponse> areaResponseList;

    public AreaAdapter(Context context, List<AreaResponse> areaResponseList) {

        this.context = context;
        this.areaResponseList = areaResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.country_list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AreaResponse areaResponse = areaResponseList.get(position);

        holder.countryName.setText(areaResponseList.get(position).getAreaName());

    }

    @Override
    public int getItemCount() {
        return areaResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.countryName)
        TextView countryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

