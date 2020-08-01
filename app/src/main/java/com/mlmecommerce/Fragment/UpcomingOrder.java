package com.mlmecommerce.Fragment;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CartListAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class UpcomingOrder extends Fragment {

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
        view = inflater.inflate(R.layout.fragment_my_order, container, false);
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
            getplacedOrderList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getplacedOrderList() {



    }

}