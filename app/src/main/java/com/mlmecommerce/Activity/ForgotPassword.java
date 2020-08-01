package com.mlmecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.mlmecommerce.helper.AppSignatureHelper;
import com.mlmecommerce.interfaces.OtpReceivedInterface;
import com.mlmecommerce.receiver.SmsBroadcastReceiver;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.mobileNumber)
    FormEditText formEditText;
    @BindView(R.id.otpView)
    OtpView otpView;
    @BindViews({R.id.sendotpLayout, R.id.verifyotpLayout})
    List<LinearLayout> linearLayouts;
    @BindView(R.id.forgotLayout)
    LinearLayout forgotLayout;
    GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    CountDownTimer cTimer = null;
    String otpCode, OTP, customerId;
    private String HASH_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();

        //set google api client for hint request
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        forgotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                try {
                    otpCode = otp;
                    int length = otpCode.trim().length();
                    if (length == 4) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick({R.id.submit, R.id.verify, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                moveTaskToBack(false);
                break;

            case R.id.submit:
                if (formEditText.testValidity()) {
                    sendOTP(formEditText.getText().toString().trim());
                }
                break;

            case R.id.verify:

                if (otpCode != null) {

                    if (otpCode.equalsIgnoreCase(OTP)) {
                        Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
                        intent.putExtra("OTP", OTP);
                        intent.putExtra("customerId", customerId);
                        startActivity(intent);
                    } else {
                        Toasty.error(ForgotPassword.this, "OTP Not Match", Toasty.LENGTH_LONG, true).show();
                    }

                } else {
                    Toasty.warning(ForgotPassword.this, "Please Enter OTP", Toasty.LENGTH_LONG, true).show();
                }

                break;
        }
    }

    private void sendOTP(String mobileNumber) {

        HASH_KEY = (String) new AppSignatureHelper(this).getAppSignatures().get(0);
        HASH_KEY = HASH_KEY.replace("+", "%252B");
        OTP= new DecimalFormat("0000").format(new Random().nextInt(9999));
        String message = "<#> Your Hadapsar Bazaar verification OTP code is "+ OTP +". Code valid for 10 minutes only, one time use. Please DO NOT share this OTP with anyone.\n" + HASH_KEY;

        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("OTP is sending");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerMobileNo(mobileNumber);
        customerResponse.setMessage(message);
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<StatusResponse> call = apiInterface.customerForgotPassword(customerResponse);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                if (response.body().getStatus().booleanValue()==true){
                    progressDialog.dismiss();

                    linearLayouts.get(0).setVisibility(View.GONE);
                    linearLayouts.get(1).setVisibility(View.VISIBLE);
                    Toast.makeText(ForgotPassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    customerId = String.valueOf(response.body().getCustomerId());
                    Log.e("customerId", ""+customerId);

                    startSMSListener();

                } else  if (response.body().getStatus().booleanValue()==false){
                    progressDialog.dismiss();

                    linearLayouts.get(1).setVisibility(View.GONE);
                    linearLayouts.get(0).setVisibility(View.VISIBLE);
                    Toast.makeText(ForgotPassword.this, "Please use a valid phone number", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progressDialog.dismiss();

                linearLayouts.get(1).setVisibility(View.GONE);
                linearLayouts.get(0).setVisibility(View.VISIBLE);
                Log.e("Error", ""+t.getMessage());
            }
        });

    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
                linearLayouts.get(0).setVisibility(View.GONE);
                linearLayouts.get(1).setVisibility(View.VISIBLE);
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {
        otpView.setText(otp);
    }

    @Override
    public void onOtpTimeout() {

    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}