package com.mlmecommerce.Fragment;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.BrandValRecyclerAdapter;
import com.mlmecommerce.Adapter.FilterRecyclerAdapter;
import com.mlmecommerce.Adapter.FilterValRecyclerAdapter;
import com.mlmecommerce.Adapter.ProductAdapter;
import com.mlmecommerce.Adapter.SubCategoryAdapter;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Fragment.bottomsheet.FilterSheetDialog;
import com.mlmecommerce.Fragment.bottomsheet.SortSheetDialog;
import com.mlmecommerce.Model.BrandResponse;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.Model.FilterDiscount;
import com.mlmecommerce.Model.FilterPrice;
import com.mlmecommerce.Model.FilterResponse;
import com.mlmecommerce.Model.MainFilterModel;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.Model.SubCategoryResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategoryProductList extends Fragment {

    View view;
    @BindViews({R.id.productRecyclerView})
    List<RecyclerView> recyclerViews;
    List<ProductResponse> productResponseList = new ArrayList<>();
    public static ArrayList<Integer> selectedPosList;
    public static LinkedHashMap<Integer, ArrayList<Integer>> selectedPosHashMap = new LinkedHashMap<>();
    public static List<ProductAdapter> instances = new ArrayList();
    public static int viewPagerCurrentPos = 0;
    public static ProductAdapter productAdapter;
    String subCategoryId;
    @BindViews({R.id.categoryProgressBar, R.id.productProgressBar})
    List<ProgressBar> progressBars;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;


    //Id's of BottomSheet
    CrystalRangeSeekbar priceBar;
    CrystalRangeSeekbar discountBar;
    RelativeLayout priceLayout;
    RelativeLayout discountLayout;
    RecyclerView filter_dialog_listview;
    RecyclerView filter_value_listview;
    RecyclerView brand_value_listview;
    TextView minValuetxt;
    TextView maxValuetxt;
    TextView discountMinValuetxt;
    TextView discountMaxValuetxt;

    FilterRecyclerAdapter adapter;
    FilterValRecyclerAdapter filterValAdapter;
    BrandValRecyclerAdapter brandValAdapter;
    private List<MainFilterModel> filterModels = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    List<BrandResponse> brandResponseList = new ArrayList<>();
    private List<String> rootFilters;
    private ArrayList<String> categorySelected = new ArrayList<String>();
    private ArrayList<String> brandSelected = new ArrayList<String>();
    private String minPriceValue="0", maxPriceValue="100000", minDiscountPriceValue="0", maxDiscountPriceValue="100";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sub_category_product, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");
        SubCategoryProduct.selectedPosHashMap.clear();

        Bundle bundle = getArguments();
        subCategoryId = bundle.getString("subCategoryId");


        bottom_sheet = view.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        return view;

    }

    @OnClick({R.id.sort, R.id.filter, R.id.cartLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort:
                SortSheetDialog sortSheetDialog = new SortSheetDialog();
                sortSheetDialog.show(getActivity().getSupportFragmentManager(), sortSheetDialog.getTag());

                break;

            case R.id.filter:
                showFilterData();
                break;

            case R.id.cartLayout:
                ((MainPage) getActivity()).loadFragment(new MyCart(), true);
                break;
        }
    }


    private void showFilterData() {

        try {

            categoryResponseList.clear();
            brandResponseList.clear();
            filterModels.clear();


            if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

            getBrandList();
            getCategoryList();


            final View view = getLayoutInflater().inflate(R.layout.fragment_filter_sheet_dialog_full, null);
            priceBar = view.findViewById(R.id.priceBar);
            discountBar = view.findViewById(R.id.discountBar);
            priceLayout = view.findViewById(R.id.priceLayout);
            discountLayout = view.findViewById(R.id.discountLayout);
            filter_dialog_listview = view.findViewById(R.id.filter_dialog_listview);
            filter_value_listview = view.findViewById(R.id.filter_value_listview);
            brand_value_listview = view.findViewById(R.id.brand_value_listview);
            minValuetxt = view.findViewById(R.id.minValuetxt);
            maxValuetxt = view.findViewById(R.id.maxValuetxt);
            discountMinValuetxt = view.findViewById(R.id.discountMinValuetxt);
            discountMaxValuetxt = view.findViewById(R.id.discountMaxValuetxt);

            priceBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    minValuetxt.setText(String.valueOf(minValue));
                    maxValuetxt.setText(String.valueOf(maxValue));
                }
            });


            priceBar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {

                    minPriceValue = String.valueOf(minValue);
                    maxPriceValue = String.valueOf(maxValue);
                }
            });

            discountBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    discountMinValuetxt.setText(String.valueOf(minValue));
                    discountMaxValuetxt.setText(String.valueOf(maxValue));
                }
            });


            discountBar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {

                    minDiscountPriceValue = String.valueOf(minValue);
                    maxDiscountPriceValue = String.valueOf(maxValue);
                }
            });


            (view.findViewById(R.id.clear)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (CategoryResponse selectedModel : categoryResponseList) {
                        selectedModel.setChecked(false);
                    }

                    for (BrandResponse selectedModel : brandResponseList) {
                        selectedModel.setChecked(false);
                    }

                    minPriceValue = "0";
                    maxPriceValue = "100000";

                    minDiscountPriceValue = "0";
                    maxDiscountPriceValue = "100";

                    // set properties
                    priceBar
                            .setMinValue(0)
                            .setMaxValue(100000)
                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                            .apply();

                    // set properties
                    discountBar
                            .setMinValue(0)
                            .setMaxValue(100)
                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                            .apply();

                    adapter.notifyDataSetChanged();
                    filterValAdapter.notifyDataSetChanged();
                    brandValAdapter.notifyDataSetChanged();

                }
            });

            (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (CategoryResponse selectedModel : categoryResponseList) {
                        selectedModel.setChecked(false);
                    }

                    for (BrandResponse selectedModel : brandResponseList) {
                        selectedModel.setChecked(false);
                    }

                    minPriceValue = "0";
                    maxPriceValue = "100000";

                    minDiscountPriceValue = "0";
                    maxDiscountPriceValue = "100";

                    // set properties
                    priceBar
                            .setMinValue(0)
                            .setMaxValue(100000)
                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                            .apply();

                    // set properties
                    discountBar
                            .setMinValue(0)
                            .setMaxValue(100)
                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                            .apply();

                    adapter.notifyDataSetChanged();
                    filterValAdapter.notifyDataSetChanged();
                    brandValAdapter.notifyDataSetChanged();

                    mBottomSheetDialog.dismiss();
                }
            });

            (view.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (CategoryResponse model : categoryResponseList) {
                        if (model.isChecked()) {
                            filterModels.get(MainFilterModel.INDEX_SIZE).getSubtitles().add(String.valueOf(model.getCategoryId()));
                        }
                    }

                    for (BrandResponse model : brandResponseList) {
                        if (model.isChecked()) {
                            filterModels.get(MainFilterModel.INDEX_COLOR).getSubtitles().add(String.valueOf(model.getBrandId()));
                        }
                    }

                    /*Get value from checked of category checkbox*/
                    categorySelected = filterModels.get(MainFilterModel.INDEX_SIZE).getSubtitles();
                    filterModels.get(MainFilterModel.INDEX_SIZE).setSubtitles(new ArrayList<String>());

                    /*Get value from checked of brand checkbox*/
                    brandSelected = filterModels.get(MainFilterModel.INDEX_COLOR).getSubtitles();
                    filterModels.get(MainFilterModel.INDEX_COLOR).setSubtitles(new ArrayList<String>());

                    if (categorySelected.isEmpty() && brandSelected.isEmpty() && minPriceValue.isEmpty() && maxPriceValue.isEmpty() && minDiscountPriceValue.isEmpty() && maxDiscountPriceValue.isEmpty()) {
                        Toasty.normal(getActivity(), "Select category, brand, price, discount", Toasty.LENGTH_SHORT).show();
                    } else {

                        productResponseList.clear();

                        FilterDiscount filterDiscount = new FilterDiscount();
                        filterDiscount.setToDisc(maxDiscountPriceValue);
                        filterDiscount.setFromDisc(minDiscountPriceValue);

                        FilterPrice filterPrice = new FilterPrice();
                        filterPrice.setToPrice(maxPriceValue);
                        filterPrice.setFromPrice(minPriceValue);


                        FilterResponse filterResponse = new FilterResponse();
                        filterResponse.setCustomerId(Integer.valueOf(MainPage.userId));
                        filterResponse.setFilterBrandList(brandSelected);
                        filterResponse.setFilterCategoryList(categorySelected);
                        filterResponse.setFilterDiscount(filterDiscount);
                        filterResponse.setFilterPrice(filterPrice);

                        progressBars.get(1).setVisibility(View.VISIBLE);

                        ProductAdapter.isFirstTime = true;

                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                        Call<List<ProductResponse>> call = apiInterface.getFilterList(filterResponse);
                        call.enqueue(new Callback<List<ProductResponse>>() {
                            @Override
                            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {

                                productResponseList = response.body();

                                if (productResponseList == null) {
                                    progressBars.get(1).setVisibility(View.GONE);
                                    mBottomSheetDialog.dismiss();
                                } else {

                                    selectedPosList = new ArrayList<>();
                                    if (viewPagerCurrentPos == 0) {
                                        for (int i = 0; i < productResponseList.size(); i++) {
                                            selectedPosList.add(0);
                                        }
                                    }
                                    selectedPosHashMap.put(0, selectedPosList);
                                    instances = new ArrayList<>();

                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                                    recyclerViews.get(1).setLayoutManager(gridLayoutManager);
                                    productAdapter = new ProductAdapter(getActivity(), productResponseList);
                                    SubCategoryProduct.instances.add(productAdapter);
                                    recyclerViews.get(1).setAdapter(productAdapter);
                                    productAdapter.notifyDataSetChanged();
                                    productAdapter.notifyItemInserted(productResponseList.size() - 1);
                                    recyclerViews.get(1).setHasFixedSize(true);

                                    progressBars.get(1).setVisibility(View.GONE);


                                    for (CategoryResponse selectedModel : categoryResponseList) {
                                        selectedModel.setChecked(false);
                                    }

                                    for (BrandResponse selectedModel : brandResponseList) {
                                        selectedModel.setChecked(false);
                                    }

                                    minPriceValue = "0";
                                    maxPriceValue = "100000";

                                    minDiscountPriceValue = "0";
                                    maxDiscountPriceValue = "100";

                                    // set properties
                                    priceBar
                                            .setMinValue(0)
                                            .setMaxValue(100000)
                                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                                            .apply();

                                    // set properties
                                    discountBar
                                            .setMinValue(0)
                                            .setMaxValue(100)
                                            .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                                            .apply();

                                    adapter.notifyDataSetChanged();
                                    filterValAdapter.notifyDataSetChanged();
                                    brandValAdapter.notifyDataSetChanged();

                                    mBottomSheetDialog.dismiss();

                                }

                            }

                            @Override
                            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                                Log.e("CategoryProductList", "" + t.getMessage());
                                progressBars.get(1).setVisibility(View.GONE);
                                mBottomSheetDialog.dismiss();
                            }
                        });

                    }


                }
            });

            try {

                rootFilters = Arrays.asList(this.getResources().getStringArray(R.array.filter_category));
                for (int i = 0; i < rootFilters.size(); i++) {
                    MainFilterModel model = new MainFilterModel();
                    model.setTitle(rootFilters.get(i));
                    model.setSub("All");
                    filterModels.add(model);
                }

                adapter = new FilterRecyclerAdapter(getActivity(), R.layout.filter_list_item_layout, filterModels);
                filter_dialog_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                filter_dialog_listview.setAdapter(adapter);
                filter_dialog_listview.setHasFixedSize(true);

                filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, filterModels.size());
                filter_value_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                filter_value_listview.setAdapter(filterValAdapter);
                filter_value_listview.setHasFixedSize(true);

                brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, filterModels.size());
                brand_value_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                brand_value_listview.setAdapter(brandValAdapter);
                brand_value_listview.setHasFixedSize(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter.setOnItemClickListener(new FilterRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    filterItemListClicked(position, v);
                    adapter.setItemSelected(position);
                }
            });

            filterItemListClicked(0, null);
            adapter.setItemSelected(0);

            for (CategoryResponse categoryResponse : categoryResponseList) {

                CategoryResponse integerResponse = new CategoryResponse();
                integerResponse.setCategoryId(categoryResponse.getCategoryId());

                categoryResponseList.add(integerResponse);

            }

            for (BrandResponse categoryResponse : brandResponseList) {

                BrandResponse integerResponse = new BrandResponse();
                integerResponse.setBrandId(categoryResponse.getBrandId());

                brandResponseList.add(integerResponse);

            }


            mBottomSheetDialog = new BottomSheetDialog(getActivity());
            mBottomSheetDialog.setContentView(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            mBottomSheetDialog.show();
            mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mBottomSheetDialog = null;
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void filterItemListClicked(int position, View v) {
        if (position == 0) {
            filter_value_listview.setVisibility(View.VISIBLE);
            brand_value_listview.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
            discountLayout.setVisibility(View.GONE);
            filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, MainFilterModel.SIZE);

        } else if (position == 1) {

            filter_value_listview.setVisibility(View.GONE);
            brand_value_listview.setVisibility(View.VISIBLE);
            priceLayout.setVisibility(View.GONE);
            discountLayout.setVisibility(View.GONE);
            brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, MainFilterModel.COLOR);

        } else if (position == 2) {

            filter_value_listview.setVisibility(View.GONE);
            brand_value_listview.setVisibility(View.GONE);
            priceLayout.setVisibility(View.VISIBLE);
            discountLayout.setVisibility(View.GONE);

        } else {

            filter_value_listview.setVisibility(View.GONE);
            brand_value_listview.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
            discountLayout.setVisibility(View.VISIBLE);

        }

        filter_value_listview.setAdapter(filterValAdapter);
        brand_value_listview.setAdapter(brandValAdapter);

        filterValAdapter.setOnItemClickListener(new FilterValRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                filterValitemListClicked(position);
            }
        });

        filterValAdapter.notifyDataSetChanged();

        brandValAdapter.setOnItemClickListener(new BrandValRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                brandValitemListClicked(position);
            }
        });

        brandValAdapter.notifyDataSetChanged();

    }

    private void brandValitemListClicked(int position) {
        brandValAdapter.setItemSelected(position);
    }

    private void filterValitemListClicked(int position) {
        filterValAdapter.setItemSelected(position);
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getSubCategoryProductList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    public static List<ProductAdapter> getInstances() {
        return instances;
    }

    private void getBrandList() {

        brandResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<BrandResponse>> call = apiInterface.getBrandList();
        call.enqueue(new Callback<List<BrandResponse>>() {
            @Override
            public void onResponse(Call<List<BrandResponse>> call, Response<List<BrandResponse>> response) {

                brandResponseList = response.body();

                if (brandResponseList == null) {

                } else {
                    brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, filterModels.size());
                    brand_value_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    brand_value_listview.setAdapter(brandValAdapter);
                    brand_value_listview.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<BrandResponse>> call, Throwable t) {
                Log.e("CategoryListError", "" + t.getMessage());
            }
        });

    }

    private void getCategoryList() {

        categoryResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CategoryResponse>> call = apiInterface.getCategoryList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                categoryResponseList = response.body();

                if (categoryResponseList == null) {

                } else {
                    filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, filterModels.size());
                    filter_value_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    filter_value_listview.setAdapter(filterValAdapter);
                    filter_value_listview.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("CategoryListError", "" + t.getMessage());
            }
        });
    }

    private void getSubCategoryProductList() {

        progressBars.get(1).setVisibility(View.VISIBLE);

        ProductAdapter.isFirstTime = true;

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<ProductResponse>> call = apiInterface.getSubCategoryProductList(subCategoryId, MainPage.userId);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {

                productResponseList = response.body();

                if (productResponseList == null){
                    progressBars.get(1).setVisibility(View.GONE);
                } else {

                    selectedPosList = new ArrayList<>();
                    if (viewPagerCurrentPos == 0) {
                        for (int i = 0; i < productResponseList.size(); i++) {
                            selectedPosList.add(0);
                        }
                    }
                    selectedPosHashMap.put(0, selectedPosList);
                    instances = new ArrayList<>();

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerViews.get(1).setLayoutManager(gridLayoutManager);
                    productAdapter = new ProductAdapter(getActivity(), productResponseList);
                    SubCategoryProduct.instances.add(productAdapter);
                    recyclerViews.get(1).setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    productAdapter.notifyItemInserted(productResponseList.size() - 1);
                    recyclerViews.get(1).setHasFixedSize(true);

                    progressBars.get(1).setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.e("CategoryProductList",""+t.getMessage());
                progressBars.get(1).setVisibility(View.GONE);
            }
        });
    }
}