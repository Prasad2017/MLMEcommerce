package com.mlmecommerce.Extra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prasad
 */

public class Common {

    public static final String SHARED_PREF = "userData";
    public static String totalCartCount = "0";


    public static void saveUserData(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSavedUserData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(key, "");

    }

    public static void getProfile(Context context){

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CustomerResponse> call = apiInterface.getProfile(MainPage.userId);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {

                CustomerResponse customerResponse = response.body();

                MainPage.firstName = customerResponse.getCustomerFName();
                MainPage.middleName = customerResponse.getCustomerMName();
                MainPage.lastName = customerResponse.getCustomerLName();
                MainPage.mobileNumber = customerResponse.getCustomerMobileNo();
                MainPage.emailId = customerResponse.getCustomerMail();
                MainPage.defaultAddress = customerResponse.getCustomerAddressFullAddress();
                MainPage.profileImage = customerResponse.getCustomerPhoto();


            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.e("ProfileError", ""+t.getMessage());
            }
        });

    }

    public static void getCartCount(Context context){

        MainPage.progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<Long> call = apiInterface.getCartCount(MainPage.userId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                MainPage.progressBar.setVisibility(View.GONE);
                try {
                    totalCartCount = String.valueOf(response.body());
                    MainPage.cartCount.setText("" + response.body());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                MainPage.progressBar.setVisibility(View.GONE);
                MainPage.cartCount.setVisibility(View.GONE);
                Log.e("countError", t.getMessage());
                try {
                    totalCartCount = "0";
                    MainPage.cartCount.setText("0");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}
