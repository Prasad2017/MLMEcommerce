package com.mlmecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    @BindViews({R.id.password, R.id.confirmPassword})
    List<FormEditText> formEditTexts;
    String OTP, customerId;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        formEditTexts.get(0).setSelection(formEditTexts.get(0).getText().toString().length());
        formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());

        try {
            Intent intent = getIntent();
            OTP = intent.getStringExtra("OTP");
            customerId = intent.getStringExtra("customerId");
            Log.e("customerId", ""+customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.back, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                moveTaskToBack(false);
                break;

            case R.id.submit:

                if (formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity()) {
                    if (validatePassword(formEditTexts.get(0)) && validatePassword(formEditTexts.get(1))) {
                        if (formEditTexts.get(1).getText().toString().equals(formEditTexts.get(0).getText().toString())) {
                            if (DetectConnection.checkInternetConnection(ChangePassword.this)) {
                                changePassword(formEditTexts.get(0).getText().toString());
                            } else {
                                Toasty.warning(ChangePassword.this, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toast.makeText(ChangePassword.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }
    }

    private void changePassword(String password) {

        ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Password is updating");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(customerId);
        customerResponse.setCustomerPassword(password);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiInterface.customerUpdatePassword(customerResponse);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body().booleanValue()==true){
                    progressDialog.dismiss();
                    Toasty.success(ChangePassword.this, "Password has been changed", Toasty.LENGTH_SHORT).show();
                } else if (response.body().booleanValue()==false){
                    progressDialog.dismiss();
                    Toasty.error(ChangePassword.this, "Password has been change Failed", Toasty.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangePassword.this, Login.class);
                    startActivity(intent);
                    finishAffinity();

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("PassError", ""+t.getMessage());
            }
        });


    }

    private boolean validatePassword(FormEditText editText) {
        if (editText.getText().toString().trim().length() > 7) {
            return true;
        } else if (editText.getText().toString().trim().length() > 0) {
            editText.setError("Password must be of 8 characters");
            editText.requestFocus();
            return false;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ChangePassword.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}