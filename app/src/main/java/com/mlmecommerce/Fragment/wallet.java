package com.mlmecommerce.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.Login;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.OrderCartAdapter;

import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Fragment.Home;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.Model.WalletResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wallet extends Fragment {


    View view;
    public static Activity activity;
    TextView owncoins,ownamount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.wallet, container, false);
        activity = (Activity) view.getContext();
        ownamount=view.findViewById(R.id.ownamount);
        owncoins=view.findViewById(R.id.owncoins);
        view.setVisibility(view.GONE);

      //   MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        getwalletdata();
        return view;
    }



    void getwalletdata(){

        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
            Call<WalletResponse> call = apiInterface.getwalletdata(MainPage.userId);
            call.enqueue(new Callback<WalletResponse>() {
                @Override
                public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {

                    progressDialog.dismiss();
                    view.setVisibility(view.VISIBLE);
                    WalletResponse  result = response.body();
                    owncoins.setText(String.valueOf(result.getBonusPoint()));
                    ownamount.setText(String.valueOf("र"+" "+result.getReferAmount()));
                }

                @Override
                public void onFailure(Call<WalletResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("cartError", ""+t.getMessage());
                }
            });

        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }
}
