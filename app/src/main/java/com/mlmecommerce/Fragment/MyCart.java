package com.mlmecommerce.Fragment;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.wallet.Cart;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CartListAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Fragment.bottomsheet.SortSheetDialog;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCart extends Fragment {

    View view;
    @BindView(R.id.myCartsRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyCartsLayout)
    LinearLayout emptyCartsLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    List<CartResponse> cartResponseList = new ArrayList<>();
    public static CartListAdapter cartListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        return view;

    }

    @OnClick({R.id.continueShopping})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continueShopping:
                ((MainPage) getActivity()).loadFragment(new Home(), false);
                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getCartList();
            Common.getCartCount(getActivity());
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getCartList() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CartResponse>> call = apiInterface.getCartList(MainPage.userId);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {

                cartResponseList = response.body();
                if (cartResponseList.size()==0){
                    pDialog.dismiss();

                    emptyCartsLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);

                } else {
                    pDialog.dismiss();

                    cartListAdapter = new CartListAdapter(getActivity(), cartResponseList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(cartListAdapter);
                    cartListAdapter.notifyDataSetChanged();
                    cartListAdapter.notifyItemInserted(cartResponseList.size() - 1);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.invalidate();
                    emptyCartsLayout.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                pDialog.dismiss();
                Log.e("cartError", ""+t.getMessage());
                emptyCartsLayout.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
            }
        });

    }
}