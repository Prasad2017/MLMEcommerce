package com.mlmecommerce.Fragment;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.CartListAdapter;
import com.mlmecommerce.Adapter.ProductAdapter;
import com.mlmecommerce.Adapter.SubCategoryAdapter;
import com.mlmecommerce.Adapter.WishProductAdapter;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.ProductImageResponse;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.Model.SubCategoryResponse;
import com.mlmecommerce.Model.WishListResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyWishList extends Fragment {

    public static View view;
    @BindViews({R.id.productRecyclerView})
    List<RecyclerView> recyclerViews;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    List<SubCategoryResponse> categoryResponseList = new ArrayList<>();
    List<ProductResponse> productResponseList = new ArrayList<>();
    public static ArrayList<Integer> selectedPosList;
    public static LinkedHashMap<Integer, ArrayList<Integer>> selectedPosHashMap = new LinkedHashMap<>();
    public static List<WishProductAdapter> instances = new ArrayList();
    public static int viewPagerCurrentPos = 0;
    public static WishProductAdapter productAdapter;
    String categoryId;
    public static CoordinatorLayout coordinatorLayout;
    CartListAdapter cartListAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");
        MyWishList.selectedPosHashMap.clear();
        init();

        return view;

    }

    private void init() {
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.relativeLayout);
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getWishList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getWishList() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        ProductAdapter.isFirstTime = true;

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<ProductResponse>> call = apiInterface.getWishList(MainPage.userId);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {

                productResponseList = response.body();

                if (productResponseList == null){
                    pDialog.dismiss();
                    emptyLayout.setVisibility(View.VISIBLE);
                    recyclerViews.get(0).setVisibility(View.GONE);
                } else {
                    pDialog.dismiss();

                    emptyLayout.setVisibility(View.GONE);
                    recyclerViews.get(0).setVisibility(View.VISIBLE);

                    selectedPosList = new ArrayList<>();
                    if (viewPagerCurrentPos == 0) {
                        for (int i = 0; i < productResponseList.size(); i++) {
                            selectedPosList.add(0);
                        }
                    }
                    selectedPosHashMap.put(0, selectedPosList);
                    instances = new ArrayList<>();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerViews.get(0).setLayoutManager(gridLayoutManager);
                    productAdapter = new WishProductAdapter(getActivity(), productResponseList);
                    MyWishList.instances.add(productAdapter);
                    recyclerViews.get(0).setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    productAdapter.notifyItemInserted(productResponseList.size() - 1);
                    recyclerViews.get(0).setHasFixedSize(true);

                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                pDialog.dismiss();
                emptyLayout.setVisibility(View.VISIBLE);
                Log.e("CategoryProductList",""+t.getMessage());
            }
        });

    }
}