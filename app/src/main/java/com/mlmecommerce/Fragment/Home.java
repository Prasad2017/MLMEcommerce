package com.mlmecommerce.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
;

import com.mlmecommerce.Activity.Login;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CategoryAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.Config;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    View view;
    @BindViews({R.id.firstBannerRecyclerView, R.id.categoryRecyclerView, R.id.secondBannerRecyclerView, R.id.productRecyclerView, R.id.thirdBannerRecyclerView})
    List<RecyclerView> recyclerViews;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CirclePageIndicator indicator;
    public static ViewPager mPager;
    public static List<CategoryResponse> categoryResponseList = new ArrayList<>();
    CategoryAdapter categoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        swipeRefreshLayout = view.findViewById(R.id.simpleSwipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (DetectConnection.checkInternetConnection(getActivity())){

                    getCategoryList();
                    Common.getCartCount(getActivity());
                    Common.getProfile(getActivity());

                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return view;

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(0);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {

            getCategoryList();
            Common.getCartCount(getActivity());
            Common.getProfile(getActivity());

        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getCategoryList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CategoryResponse>> call = apiInterface.getCategoryList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                categoryResponseList = response.body();

                if (categoryResponseList==null){

                } else {

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViews.get(1).setLayoutManager(linearLayoutManager);


                    if (categoryResponseList.size() < 10) {
                        categoryAdapter = new CategoryAdapter(getActivity(), categoryResponseList, categoryResponseList.size());
                    } else {
                        categoryAdapter = new CategoryAdapter(getActivity(), categoryResponseList, 10);
                    }

                    recyclerViews.get(1).setAdapter(categoryAdapter);

                    categoryAdapter.notifyDataSetChanged();
                    categoryAdapter.notifyItemInserted(categoryResponseList.size() - 1);
                    recyclerViews.get(1).setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("CategoryListError",""+t.getMessage());
            }
        });

    }

}