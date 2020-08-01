package com.mlmecommerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CartListAdapter;
import com.mlmecommerce.Adapter.CheckoutAddressAdapter;
import com.mlmecommerce.Adapter.OrderCartAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.Config;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfirmOrderDetails extends Fragment {

    View view;
    @BindView(R.id.myCartsRecyclerView)
    RecyclerView myCartsRecyclerView;
    public static OrderCartAdapter orderCartAdapter;
    public static TextView deliveryAddress, subAmount, totalAmount, orderAmount, deliveryAmount, promoCode, paymentMode, fullName, billingAddress;
    public static List<CartResponse> cartResponseList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_order_details, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");
        init();


        try{

            fullName.setText(ChoosePaymentMethod.fullName);
            billingAddress.setText(ChoosePaymentMethod.fullAddress);
            deliveryAddress.setText(ChoosePaymentMethod.fullAddress);
            paymentMode.setText(ChoosePaymentMethod.paymentMethod);


        } catch (Exception e){
            e.printStackTrace();
        }

        return view;

    }

    @OnClick({R.id.placeOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.placeOrder:
                Config.postOrder(getActivity(), MainPage.userId, ChoosePaymentMethod.paymentMethod, cartResponseList, MyCart.cartListAdapter.totalAmountPayable, ChoosePaymentMethod.fullAddress, ChoosePaymentMethod.fullName, ChoosePaymentMethod.mobileNumber);
                break;
        }

    }

    private void init() {

        fullName = (TextView) view.findViewById(R.id.fullName);
        billingAddress = (TextView) view.findViewById(R.id.billingAddress);
        deliveryAddress = (TextView) view.findViewById(R.id.deliveryAddress);
        subAmount = (TextView) view.findViewById(R.id.subAmount);
        totalAmount = (TextView) view.findViewById(R.id.totalAmount);
        orderAmount = (TextView) view.findViewById(R.id.orderAmount);
        deliveryAmount = (TextView) view.findViewById(R.id.deliveryAmount);
        promoCode = (TextView) view.findViewById(R.id.promoCode);
        paymentMode = (TextView) view.findViewById(R.id.paymentMode);

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {

            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
            Call<List<CartResponse>> call = apiInterface.getCartList(MainPage.userId);
            call.enqueue(new Callback<List<CartResponse>>() {
                @Override
                public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {

                    cartResponseList = response.body();
                    try {
                        Log.e("List data", ""+cartResponseList.size());
                        if (cartResponseList.size()>0) {
                            orderCartAdapter = new OrderCartAdapter(getActivity(), cartResponseList);
                            myCartsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            myCartsRecyclerView.setAdapter(orderCartAdapter);
                            orderCartAdapter.notifyDataSetChanged();
                            myCartsRecyclerView.setHasFixedSize(true);
                        } else {
                            ((MainPage) getActivity()).loadFragment(new Home(), false);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                    Log.e("cartError", ""+t.getMessage());
                }
            });

        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }
}