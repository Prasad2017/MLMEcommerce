package com.mlmecommerce.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.AddressBookAdapter;
import com.mlmecommerce.Adapter.CheckoutAddressAdapter;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Checkout extends Fragment {

    View view;
    @BindView(R.id.addressRecyclerView)
    RecyclerView recyclerView;
    List<AddressResponse> addressResponseList = new ArrayList<>();
    CheckoutAddressAdapter checkoutAddressAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_checkout, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        return view;

    }

    @OnClick({R.id.addAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAddress:
                ((MainPage) getActivity()).loadFragment(new CheckoutNewAddress(), true);
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
            try {
                getAddressList();
            }catch (Exception e){
                e.printStackTrace();
            }
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
                if (addressResponseList==null){
                    pDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                } else {
                    pDialog.dismiss();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    checkoutAddressAdapter = new CheckoutAddressAdapter(getActivity(), addressResponseList);
                    recyclerView.setAdapter(checkoutAddressAdapter);
                    checkoutAddressAdapter.notifyDataSetChanged();
                    checkoutAddressAdapter.notifyItemInserted(addressResponseList.size() - 1);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<List<AddressResponse>> call, Throwable t) {
                pDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
                Log.e("AddressError",""+t.getMessage());
            }
        });

    }


}