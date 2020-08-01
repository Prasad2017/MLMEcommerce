package com.mlmecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mlmecommerce.Extra.Config;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.Model.StateResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registration extends AppCompatActivity {

    @BindViews({R.id.firstName, R.id.middleName, R.id.lastName, R.id.mobile, R.id.email, R.id.password, R.id.confirmPassword, R.id.referenceId, R.id.referenceName})
    List<FormEditText> formEditTexts;
    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;
    @BindView(R.id.referenceNameLayout)
    TextInputLayout referenceNameLayout;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);
        ButterKnife.bind(this);

        formEditTexts.get(0).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(1).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(2).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(7).setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        formEditTexts.get(8).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        formEditTexts.get(0).setSelection(formEditTexts.get(0).getText().toString().length());
        formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());
        formEditTexts.get(2).setSelection(formEditTexts.get(2).getText().toString().length());
        formEditTexts.get(3).setSelection(formEditTexts.get(3).getText().toString().length());
        formEditTexts.get(4).setSelection(formEditTexts.get(4).getText().toString().length());
        formEditTexts.get(5).setSelection(formEditTexts.get(5).getText().toString().length());
        formEditTexts.get(6).setSelection(formEditTexts.get(6).getText().toString().length());
        formEditTexts.get(7).setSelection(formEditTexts.get(7).getText().toString().length());
        formEditTexts.get(8).setSelection(formEditTexts.get(8).getText().toString().length());

        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        formEditTexts.get(7).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String s1 = s.toString();

                try{

                    if (formEditTexts.get(7).testValidity()) {

                        if (formEditTexts.get(7).getText().toString().length()==15) {

                            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                            Call<StatusResponse> call = apiInterface.checkReferenceId(formEditTexts.get(8).getText().toString().trim());
                            call.enqueue(new Callback<StatusResponse>() {
                                @Override
                                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                                    try {

                                        if (response.body().getStatus().booleanValue() == true) {
                                            formEditTexts.get(8).setText(response.body().getCustomerName());
                                            formEditTexts.get(8).setVisibility(View.VISIBLE);
                                        } else if (response.body().getStatus().booleanValue() == false) {
                                            formEditTexts.get(7).setError("Enter Valid Reference Id");
                                            formEditTexts.get(7).requestFocus();
                                            formEditTexts.get(8).setVisibility(View.GONE);
                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<StatusResponse> call, Throwable t) {
                                    Log.e("MBError", "" + t.getMessage());
                                }
                            });
                        } else {

                        }
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        formEditTexts.get(3).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (formEditTexts.get(3).testValidity()){

                    if (formEditTexts.get(3).getText().toString().trim().length()==10){

                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                        Call<Boolean> call = apiInterface.checkCustomerMobileNumber(formEditTexts.get(3).getText().toString().trim());
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                if (response.body().booleanValue()==true){

                                } else if (response.body().booleanValue()==false){
                                    formEditTexts.get(3).setError("Mobile Number Already Exit");
                                    formEditTexts.get(3).requestFocus();
                                }

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.e("MBError", ""+t.getMessage());
                            }
                        });

                    } else {

                    }

                }
            }
        });

        formEditTexts.get(4).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (formEditTexts.get(4).testValidity()){

                    if (Config.validateEmail(formEditTexts.get(4),Registration.this)){

                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                        Call<Boolean> call = apiInterface.checkCustomerMail(formEditTexts.get(4).getText().toString().trim());
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                if (response.body().booleanValue()==true){

                                } else if (response.body().booleanValue()==false){
                                    formEditTexts.get(4).setError("Email Already Exit");
                                    formEditTexts.get(4).requestFocus();
                                }

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.e("MBError", ""+t.getMessage());
                            }
                        });

                    }
                }
            }
        });

        formEditTexts.get(5).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 =s.toString();

                if (formEditTexts.get(5).testValidity() && formEditTexts.get(6).testValidity()) {
                    if (validatePassword(formEditTexts.get(5)) && validatePassword(formEditTexts.get(6))) {
                        if (formEditTexts.get(6).getText().toString().equals(formEditTexts.get(5).getText().toString())) {

                        } else {
                            formEditTexts.get(5).setError("Password do not match");
                            formEditTexts.get(5).requestFocus();
                            formEditTexts.get(6).setError("Confirm password do not match");
                            formEditTexts.get(6).requestFocus();
                        }
                    }
                }
            }
        });

        formEditTexts.get(6).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 =s.toString();

                if (formEditTexts.get(5).testValidity() && formEditTexts.get(6).testValidity()) {
                    if (validatePassword(formEditTexts.get(5)) && validatePassword(formEditTexts.get(6))) {
                        if (formEditTexts.get(6).getText().toString().equals(formEditTexts.get(5).getText().toString())) {

                        } else {
                            formEditTexts.get(5).setError("Password do not match");
                            formEditTexts.get(5).requestFocus();
                            formEditTexts.get(6).setError("Confirm password do not match");
                            formEditTexts.get(6).requestFocus();
                        }
                    }
                }
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

    @OnClick({R.id.back, R.id.signIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finishAffinity();
                break;

            case R.id.signIn:

                if (formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity() && formEditTexts.get(2).testValidity()
                        && formEditTexts.get(3).testValidity() && formEditTexts.get(4).testValidity() && formEditTexts.get(5).testValidity() && formEditTexts.get(6).testValidity()) {

                    if (validatePassword(formEditTexts.get(5)) && validatePassword(formEditTexts.get(6))) {

                        if (formEditTexts.get(6).getText().toString().equals(formEditTexts.get(5).getText().toString())) {

                            if (DetectConnection.checkInternetConnection(Registration.this)) {

                                RegistrationData(formEditTexts.get(0).getText().toString(), formEditTexts.get(1).getText().toString(),
                                        formEditTexts.get(2).getText().toString(), formEditTexts.get(3).getText().toString(),
                                        formEditTexts.get(4).getText().toString(), formEditTexts.get(5).getText().toString(), formEditTexts.get(7).getText().toString().trim());

                            } else {
                                Toasty.warning(Registration.this, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                            }

                        } else {
                            formEditTexts.get(6).setError("Confirm password do not match");
                            formEditTexts.get(6).requestFocus();
                        }
                    }
                }
                break;
        }
    }

    private void RegistrationData(String firstName, String lastName, String middleName, String mobile, String email, String password, String referenceId) {

        if (DetectConnection.checkInternetConnection(Registration.this)) {


            ProgressDialog progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Account is in creating");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);

            if (referenceId.trim().isEmpty()){
                referenceId="null";
            } else {
                referenceId =referenceId;
            }
            Log.e("referenceId",""+referenceId);

            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustomerFName(firstName);
            customerResponse.setCustomerMName(middleName);
            customerResponse.setCustomerLName(lastName);
            customerResponse.setCustomerLName(lastName);
            customerResponse.setCustomerMobileNo(mobile);
            customerResponse.setCustomerMail(email);
            customerResponse.setCustomerPassword(password);
            customerResponse.setReferenceId(referenceId);

            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
            Call<Boolean> call = apiInterface.Registration(customerResponse);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    if (response.body().booleanValue()==true){
                        progressDialog.dismiss();
                        Toasty.success(Registration.this, "Account Created Successfully", Toasty.LENGTH_SHORT).show();
                    } else if (response.body().booleanValue()==false){
                        progressDialog.dismiss();
                        Toasty.error(Registration.this, "Account Created Failed", Toasty.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Reg_Error",""+t.getMessage());
                }
            });

        } else {
            Toasty.warning(Registration.this, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }


    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }

}