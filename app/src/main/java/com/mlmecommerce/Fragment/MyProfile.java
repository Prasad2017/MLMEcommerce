package com.mlmecommerce.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andreabaccega.widget.FormEditText;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Extra.Blur;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProfile extends Fragment {

    View view;
    @BindViews({R.id.firstName, R.id.middleName, R.id.lastName, R.id.mobile, R.id.email})
    List<FormEditText> formEditTexts;
    @BindView(R.id.profileImage)
    ImageView profileImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        return view;

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getProfile();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getProfile() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CustomerResponse> call = apiInterface.getProfile(MainPage.userId);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {

                CustomerResponse customerResponse = response.body();
                if (customerResponse==null){
                    pDialog.dismiss();
                } else {

                    pDialog.dismiss();

                    formEditTexts.get(0).setText(customerResponse.getCustomerFName());
                    formEditTexts.get(1).setText(customerResponse.getCustomerMName());
                    formEditTexts.get(2).setText(customerResponse.getCustomerLName());
                    formEditTexts.get(3).setText(customerResponse.getCustomerMobileNo());
                    formEditTexts.get(4).setText(customerResponse.getCustomerMail());

                    try {

                        Transformation blurTransformation = new Transformation() {
                            @Override
                            public Bitmap transform(Bitmap source) {
                                Bitmap blurred = Blur.fastblur(getActivity(), source, 10);
                                source.recycle();
                                return blurred;
                            }

                            @Override
                            public String key() {
                                return "blur()";
                            }
                        };

                        Picasso.with(getActivity())
                                .load(customerResponse.getCustomerPhoto())
                                .placeholder(R.drawable.defaultimage)
                                .transform(blurTransformation)
                                .into(profileImage, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                        Picasso.with(getActivity())
                                                .load(customerResponse.getCustomerPhoto())
                                                .placeholder(profileImage.getDrawable())
                                                .into(profileImage);

                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                pDialog.dismiss();
                Log.e("ProfileError", ""+t.getMessage());
            }
        });

    }

}