package com.mlmecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Model.PinCodeResponse;
import com.mlmecommerce.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PincodeAdapter extends RecyclerView.Adapter<PincodeAdapter.MyViewHolder>{

    Context context;
    List<PinCodeResponse> pinCodeResponseList;

    public PincodeAdapter(Context context, List<PinCodeResponse> pinCodeResponseList){

        this.context=context;
        this.pinCodeResponseList=pinCodeResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view= LayoutInflater.from(context).inflate(R.layout.country_list_item,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position){

        PinCodeResponse pinCodeResponse = pinCodeResponseList.get(position);

        holder.countryName.setText(pinCodeResponseList.get(position).getPincodeName());

    }

    @Override
    public int getItemCount(){
        return pinCodeResponseList.size();
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
