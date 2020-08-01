package com.mlmecommerce.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CheckoutAddressAdapter;
import com.mlmecommerce.Extra.Config;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;


public class ChoosePaymentMethod extends Fragment {


    View view;
    @BindView(R.id.paymentMethodsGroup)
    RadioGroup paymentMethodsGroup;
    RadioButton paymentMethodsButton;
    public static String fullName, mobileNumber, fullAddress, paymentMethod;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_payment_method, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        Bundle bundle = getArguments();
        fullName = bundle.getString("fullName");
        mobileNumber = bundle.getString("mobileNumber");
        fullAddress = bundle.getString("fullAddress");


        return view;

    }

    @OnClick({R.id.continuePayment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continuePayment:
                moveNext();
                break;
        }

    }

    private void moveNext() {
        switch (paymentMethodsGroup.getCheckedRadioButtonId()) {

            case R.id.razorPay:
                paymentMethod = "online";
                break;
            case R.id.cod:

                paymentMethod = "cod";

                ((MainPage) getActivity()).loadFragment(new ConfirmOrderDetails(), true);

                break;

            default:
                paymentMethod = "";
                Config.showCustomAlertDialog(getActivity(),
                        "Payment Method",
                        "Select your payment method to make payment",
                        SweetAlertDialog.WARNING_TYPE);
                break;


        }

        Log.d("paymentMethod", paymentMethod);
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            Config.cartList(getActivity());
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

}