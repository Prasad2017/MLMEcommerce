package com.mlmecommerce.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.AddressBookAdapter;
import com.mlmecommerce.Adapter.CategoryProductAdapter;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressBook extends Fragment {

    View view;
    @BindView(R.id.addressRecyclerView)
    RecyclerView recyclerView;
    List<AddressResponse> addressResponseList = new ArrayList<>();
    public static AddressBookAdapter addressBookAdapter;
    @BindView(R.id.addressLayout)
    LinearLayout addressLayout;
    @BindView(R.id.newAddressLayout)
    LinearLayout newAddressLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_address_book, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");


        return view;

    }

    @OnClick({R.id.newAddAddress, R.id.addAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newAddAddress:
            case R.id.addAddress:
                ((MainPage) getActivity()).loadFragment(new AddAddress(), true);
                break;
        }
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getAddressList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getAddressList() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        recyclerView.clearOnScrollListeners();
        addressResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<AddressResponse>> call = apiInterface.getAddress(MainPage.userId);
        call.enqueue(new Callback<List<AddressResponse>>() {
            @Override
            public void onResponse(Call<List<AddressResponse>> call, Response<List<AddressResponse>> response) {

                addressResponseList = response.body();
                if (addressResponseList.size()==0){
                    pDialog.dismiss();

                    addressLayout.setVisibility(View.GONE);
                    newAddressLayout.setVisibility(View.VISIBLE);
                } else {
                    pDialog.dismiss();

                    addressLayout.setVisibility(View.VISIBLE);
                    newAddressLayout.setVisibility(View.GONE);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    addressBookAdapter = new AddressBookAdapter(getActivity(), addressResponseList);
                    recyclerView.setAdapter(addressBookAdapter);
                    addressBookAdapter.notifyDataSetChanged();
                    addressBookAdapter.notifyItemInserted(addressResponseList.size() - 1);
                    recyclerView.setHasFixedSize(true);

                }

            }

            @Override
            public void onFailure(Call<List<AddressResponse>> call, Throwable t) {
                pDialog.dismiss();

                addressLayout.setVisibility(View.GONE);
                newAddressLayout.setVisibility(View.VISIBLE);
                Log.e("AddressError",""+t.getMessage());
            }
        });

    }

}