package com.mlmecommerce.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.CheckoutUpdateAddress;
import com.mlmecommerce.Fragment.ChoosePaymentMethod;
import com.mlmecommerce.Fragment.UpdateAddress;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.R;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Callback;

public class CheckoutAddressAdapter extends RecyclerView.Adapter<CheckoutAddressAdapter.MyViewHolder> {

    Context context;
    List<AddressResponse> addressResponseList;
    private int checkedPosition = -1;


    public CheckoutAddressAdapter(Context context, List<AddressResponse> addressResponseList) {

        this.context = context;
        this.addressResponseList = addressResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_address_list_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AddressResponse addressResponse = addressResponseList.get(position);

        holder.textViews.get(0).setText(addressResponseList.get(position).getFullName());
        holder.textViews.get(1).setText(addressResponseList.get(position).getCustomerAddressFullAddress());

        try {
            if (addressResponseList.get(position).getCustomerAddressStatus().equals("Default")) {
                holder.checkBox.setChecked(true);
                holder.checkBox.setChecked(checkedPosition==position);

                if (checkedPosition == -1) {
                    holder.textViews.get(2).setVisibility(View.GONE);
                    holder.textViews.get(3).setVisibility(View.GONE);
                } else {
                    if (checkedPosition == position) {
                        holder.textViews.get(2).setVisibility(View.VISIBLE);
                        holder.textViews.get(3).setVisibility(View.VISIBLE);
                    } else {
                        holder.textViews.get(2).setVisibility(View.GONE);
                        holder.textViews.get(3).setVisibility(View.GONE);
                    }
                }
            } else {
                holder.checkBox.setChecked(false);
                holder.checkBox.setChecked(checkedPosition==position);

                if (checkedPosition == -1) {
                    holder.textViews.get(2).setVisibility(View.GONE);
                    holder.textViews.get(3).setVisibility(View.GONE);
                } else {
                    if (checkedPosition == position) {
                        holder.textViews.get(2).setVisibility(View.VISIBLE);
                        holder.textViews.get(3).setVisibility(View.VISIBLE);
                    } else {
                        holder.textViews.get(2).setVisibility(View.GONE);
                        holder.textViews.get(3).setVisibility(View.GONE);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.checkBox.setChecked(checkedPosition==position);

        if (checkedPosition == -1) {
            holder.textViews.get(2).setVisibility(View.GONE);
            holder.textViews.get(3).setVisibility(View.GONE);
        } else {
            if (checkedPosition == position) {
                holder.textViews.get(2).setVisibility(View.VISIBLE);
                holder.textViews.get(3).setVisibility(View.VISIBLE);
            } else {
                holder.textViews.get(2).setVisibility(View.GONE);
                holder.textViews.get(3).setVisibility(View.GONE);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == checkedPosition) {
                    checkedPosition = -1;
                    holder.checkBox.setChecked(false);
                    notifyDataSetChanged();
                } else {
                    checkedPosition = position;
                    holder.checkBox.setChecked(true);
                    notifyDataSetChanged();
                }

            }
        });


        holder.textViews.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckoutUpdateAddress checkoutUpdateAddress = new CheckoutUpdateAddress();
                Bundle bundle = new Bundle();
                bundle.putString("countryId", addressResponseList.get(position).getCountryId());
                bundle.putString("countryName", addressResponseList.get(position).getCountryName());
                bundle.putString("stateId", addressResponseList.get(position).getStateId());
                bundle.putString("stateName", addressResponseList.get(position).getStateName());
                bundle.putString("cityId", addressResponseList.get(position).getCityId());
                bundle.putString("cityName", addressResponseList.get(position).getCityName());
                bundle.putString("pinCodeId", addressResponseList.get(position).getPincodeId());
                bundle.putString("pinCodeName", addressResponseList.get(position).getPincodeName());
                bundle.putString("houseNo", addressResponseList.get(position).getCustomerAddressHouseNo());
                bundle.putString("buildingName", addressResponseList.get(position).getBuildingName());
                bundle.putString("areaId",addressResponseList.get(position).getAreaId());
                bundle.putString("areaName",addressResponseList.get(position).getAreaName());
                bundle.putString("landMark", addressResponseList.get(position).getCustomerAddressLandmark());
                bundle.putString("addressType", addressResponseList.get(position).getCustomerAddressType());
                bundle.putString("addressStatus", addressResponseList.get(position).getCustomerAddressStatus());
                bundle.putString("addressId", addressResponseList.get(position).getCustomerAddressId());
                bundle.putString("fullName", addressResponseList.get(position).getFullName());
                bundle.putString("mobileNumber", addressResponseList.get(position).getMobileNumber());
                checkoutUpdateAddress.setArguments(bundle);
                ((MainPage) context).loadFragment(checkoutUpdateAddress, true);

            }
        });

        holder.textViews.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChoosePaymentMethod choosePaymentMethod = new ChoosePaymentMethod();
                Bundle bundle = new Bundle();
                bundle.putString("fullName", addressResponseList.get(position).getFullName());
                bundle.putString("mobileNumber", addressResponseList.get(position).getMobileNumber());
                bundle.putString("fullAddress", addressResponseList.get(position).getCustomerAddressFullAddress());
                choosePaymentMethod.setArguments(bundle);
                ((MainPage) context).loadFragment(choosePaymentMethod, true);

            }
        });


    }

    @Override
    public int getItemCount() {
        return addressResponseList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.name, R.id.address, R.id.deliveryAddress, R.id.editAddress})
        List<TextView> textViews;
        @BindView(R.id.checkbox)
        CustomCheckBox checkBox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
