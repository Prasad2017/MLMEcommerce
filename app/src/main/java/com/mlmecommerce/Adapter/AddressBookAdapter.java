package com.mlmecommerce.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.AddressBook;
import com.mlmecommerce.Fragment.UpdateAddress;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.MyViewHolder> {

    Context context;
    List<AddressResponse> addressResponseList;

    public AddressBookAdapter(Context context, List<AddressResponse> addressResponseList) {

        this.context = context;
        this.addressResponseList = addressResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AddressResponse addressResponse = addressResponseList.get(position);

        holder.address.setText(addressResponseList.get(position).getCustomerAddressFullAddress());
        holder.addressType.setText(addressResponseList.get(position).getCustomerAddressType());

        try {
            if (addressResponseList.get(position).getCustomerAddressStatus().equals("Default")) {
                holder.markAsDefaultImage.setVisibility(View.VISIBLE);
            } else {
                holder.markAsDefaultImage.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:

                                UpdateAddress updateAddress = new UpdateAddress();
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
                                updateAddress.setArguments(bundle);
                                ((MainPage) context).loadFragment(updateAddress, true);

                                return true;

                            case R.id.remove:
                                removeAddress(addressResponseList.get(position).getCustomerAddressId());
                                return true;

                            default:
                                return false;
                        }
                    }
                });

                popup.inflate(R.menu.update_menu);
                popup.show();

            }
        });

        holder.markAsDefaultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                Call<Boolean> call = apiInterface.markAsDefaultAddress(addressResponseList.get(position).getCustomerAddressId(), MainPage.userId);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if (response.body().booleanValue()==true){
                            progressDialog.dismiss();
                            ((MainPage) context).removeCurrentFragmentAndMoveBack();
                            ((MainPage) context).loadFragment(new AddressBook(), true);

                        } else if (response.body().booleanValue()==false){
                            progressDialog.dismiss();
                            Toasty.error(context, "Try Again", Toasty.LENGTH_SHORT, true).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("AddressError", ""+t.getMessage());
                    }
                });

            }
        });

    }

    private void removeAddress(String customerAddressId) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiInterface.removeAddress(customerAddressId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body().booleanValue() == true) {
                    progressDialog.dismiss();
                    ((MainPage) context).removeCurrentFragmentAndMoveBack();
                    ((MainPage) context).loadFragment(new AddressBook(), true);

                } else if (response.body().booleanValue() == false) {
                    progressDialog.dismiss();
                    Toasty.error(context, "Try Again", Toasty.LENGTH_SHORT, true).show();
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("AddressError", "" + t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.optionsMenu)
        ImageView optionsMenu;
        @BindView(R.id.markAsDefault)
        TextView markAsDefault;
        @BindView(R.id.markAsDefaultImage)
        ImageView markAsDefaultImage;
        @BindView(R.id.markAsDefaultLayout)
        LinearLayout markAsDefaultLayout;
        @BindView(R.id.addressType)
        TextView addressType;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
