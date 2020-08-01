package com.mlmecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Model.CountryResponse;
import com.mlmecommerce.Model.StateResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    Context context;
    List<StateResponse> stateResponseList;

    public StateAdapter(Context context, List<StateResponse> stateResponseList) {

        this.context = context;
        this.stateResponseList = stateResponseList;

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

        StateResponse statusResponse = stateResponseList.get(position);

        holder.countryName.setText(stateResponseList.get(position).getStateName());

    }

    @Override
    public int getItemCount() {
        return stateResponseList.size();
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
