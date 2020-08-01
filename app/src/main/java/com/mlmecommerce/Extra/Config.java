package com.mlmecommerce.Extra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CartListAdapter;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.Model.PostOrderResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Config {

    public static List<CartResponse> cartResponseList = new ArrayList<>();


    public static void moveTo(Context context, Class targetClass) {
        Intent intent = new Intent(context, targetClass);
        context.startActivity(intent);
    }

    public static boolean validateEmail(EditText editText, Context context) {
        String email = editText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            // editText.setError(context.getString(R.string.err_msg_email));
            editText.requestFocus();
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void cartList(Context context){

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CartResponse>> call = apiInterface.getCartList(MainPage.userId);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {

                cartResponseList = response.body();

            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                Log.e("cartError", ""+t.getMessage());
            }
        });

    }


    public static void showCustomAlertDialog(Context context, String title, String msg,int type) {

        SweetAlertDialog alertDialog = new SweetAlertDialog(context, type);
        alertDialog.setTitleText(title);
        alertDialog.setCancelable(false);

        if (msg.length() > 0)
            alertDialog.setContentText(msg);
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void postOrder(Context context, String userId, String paymentMethod, List<CartResponse> cartResponseList, String totalAmountPayable, String fullAddress, String fullName, String mobileNumber) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        PostOrderResponse postOrderResponse = new PostOrderResponse();
        postOrderResponse.setCustomerId(userId);
        postOrderResponse.setOrderPaymentMode(paymentMethod);
        postOrderResponse.setOrderTotalCartAmount(totalAmountPayable);
        postOrderResponse.setOrderFinalAmount(totalAmountPayable);
        postOrderResponse.setOrderDeliveryAddress(fullName+"\n"+fullAddress+"\n"+mobileNumber);
        postOrderResponse.setOrderBillingAddress(fullName+"\n"+fullAddress+"\n"+mobileNumber);
        postOrderResponse.setCartResponseList(cartResponseList);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiInterface.postOrder(postOrderResponse);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body().booleanValue()==true){

                    progressDialog.dismiss();
                    Toasty.success(context, "Order Placed", Toasty.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(context, MainPage.class);
                    context.startActivity(intent);
                    ((Activity) context).finishAffinity();

                } else if (response.body().booleanValue()==false){
                    progressDialog.dismiss();
                    Toast.makeText(context, "Order Not Placed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("orderError", ""+t.getMessage());
            }
        });

    }
}