package com.mlmecommerce.Fragment.bottomsheet;

import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.BrandValRecyclerAdapter;
import com.mlmecommerce.Adapter.FilterRecyclerAdapter;
import com.mlmecommerce.Adapter.FilterValRecyclerAdapter;
import com.mlmecommerce.Extra.Tools;
import com.mlmecommerce.Model.BrandResponse;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.Model.FilterDiscount;
import com.mlmecommerce.Model.FilterPrice;
import com.mlmecommerce.Model.FilterResponse;
import com.mlmecommerce.Model.MainFilterModel;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    @BindViews({R.id.filter_dialog_listview, R.id.filter_value_listview, R.id.brand_value_listview})
    List<RecyclerView> recyclerViews;
    @BindViews({R.id.clear, R.id.apply, R.id.minValuetxt, R.id.maxValuetxt, R.id.discountMinValuetxt, R.id.discountMaxValuetxt})
    List<TextView> textViews;
    @BindViews({R.id.priceBar, R.id.discountBar})
    List<CrystalRangeSeekbar> crystalRangeSeekbars;
    @BindViews({R.id.priceLayout, R.id.discountLayout})
    List<RelativeLayout> relativeLayouts;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    FilterRecyclerAdapter adapter;
    FilterValRecyclerAdapter filterValAdapter;
    BrandValRecyclerAdapter brandValAdapter;
    private List<MainFilterModel> filterModels = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    List<BrandResponse> brandResponseList = new ArrayList<>();
    private List<String> rootFilters;
    private ArrayList<String> categorySelected = new ArrayList<String>();
    private ArrayList<String> brandSelected = new ArrayList<String>();
    private String minPriceValue="", maxPriceValue="", minDiscountPriceValue="", maxDiscountPriceValue="";
    public static List<ProductResponse> productResponseList = new ArrayList<>();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_filter_sheet_dialog_full, null);
        ButterKnife.bind(this, view);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        app_bar_layout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);

        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(Tools.getScreenHeight() / 2);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    showView(app_bar_layout, getActionBarSize());
                    hideView(relativeLayout);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hideView(app_bar_layout);
                    showView(relativeLayout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ((ImageButton) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (CategoryResponse selectedModel : categoryResponseList) {
                    selectedModel.setChecked(false);
                }

                for (BrandResponse selectedModel : brandResponseList) {
                    selectedModel.setChecked(false);
                }

                minPriceValue = "0";
                maxPriceValue = "0";

                minDiscountPriceValue = "0";
                maxDiscountPriceValue = "0";

                // set properties
                crystalRangeSeekbars.get(0)
                        .setMinValue(0)
                        .setMaxValue(100000)
                        .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                        .apply();

                // set properties
                crystalRangeSeekbars.get(1)
                        .setMinValue(0)
                        .setMaxValue(100)
                        .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                        .apply();

                adapter.notifyDataSetChanged();
                filterValAdapter.notifyDataSetChanged();
                brandValAdapter.notifyDataSetChanged();

                dismiss();
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
            recyclerViews.get(0).setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViews.get(0).setAdapter(adapter);
            recyclerViews.get(0).setHasFixedSize(true);

            filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, filterModels.size());
            recyclerViews.get(1).setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViews.get(1).setAdapter(filterValAdapter);
            recyclerViews.get(1).setHasFixedSize(true);

            brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, filterModels.size());
            recyclerViews.get(2).setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViews.get(2).setAdapter(brandValAdapter);
            recyclerViews.get(2).setHasFixedSize(true);

        }catch (Exception e){
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

        textViews.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if(categorySelected.isEmpty() || brandSelected.isEmpty() || minPriceValue.isEmpty() || maxPriceValue.isEmpty() || minDiscountPriceValue.isEmpty() || maxDiscountPriceValue.isEmpty()){
                    Toast.makeText(getActivity(),"Please select category, brand, price, discount", Toast.LENGTH_SHORT).show();
                } else {

                    FilterDiscount filterDiscount = new FilterDiscount();
                    filterDiscount.setToDisc(maxDiscountPriceValue);
                    filterDiscount.setFromDisc(minDiscountPriceValue);

                    FilterPrice filterPrice = new FilterPrice();
                    filterPrice.setToPrice(maxPriceValue);
                    filterPrice.setFromPrice(minPriceValue);


                    ApplyFilter(filterDiscount, filterPrice, categorySelected, brandSelected);

                }

            }
        });

        /*TODO: Clear user selected */
        textViews.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (CategoryResponse selectedModel : categoryResponseList) {
                    selectedModel.setChecked(false);
                }

                for (BrandResponse selectedModel : brandResponseList) {
                    selectedModel.setChecked(false);
                }

                minPriceValue = "0";
                maxPriceValue = "0";

                minDiscountPriceValue = "0";
                maxDiscountPriceValue = "0";

                // set properties
                crystalRangeSeekbars.get(0)
                        .setMinValue(0)
                        .setMaxValue(100000)
                        .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                        .apply();

                // set properties
                crystalRangeSeekbars.get(1)
                        .setMinValue(0)
                        .setMaxValue(100)
                        .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                        .apply();

                adapter.notifyDataSetChanged();
                filterValAdapter.notifyDataSetChanged();
                brandValAdapter.notifyDataSetChanged();
            }
        });

        crystalRangeSeekbars.get(0).setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                textViews.get(2).setText(String.valueOf(minValue));
                textViews.get(3).setText(String.valueOf(maxValue));
            }
        });


        crystalRangeSeekbars.get(0).setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

                minPriceValue = String.valueOf(minValue);
                maxPriceValue = String.valueOf(maxValue);
            }
        });

        crystalRangeSeekbars.get(1).setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                textViews.get(4).setText(String.valueOf(minValue));
                textViews.get(5).setText(String.valueOf(maxValue));
            }
        });


        crystalRangeSeekbars.get(1).setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

                minDiscountPriceValue = String.valueOf(minValue);
                maxDiscountPriceValue = String.valueOf(maxValue);
            }
        });


        return dialog;
    }

    private void ApplyFilter(FilterDiscount filterDiscount, FilterPrice filterPrice, List<String> categoryResponseList, List<String> brandResponseList) {


        FilterResponse filterResponse = new FilterResponse();
        filterResponse.setCustomerId(Integer.valueOf(MainPage.userId));
        filterResponse.setFilterBrandList(brandResponseList);
        filterResponse.setFilterCategoryList(brandResponseList);
        filterResponse.setFilterDiscount(filterDiscount);
        filterResponse.setFilterPrice(filterPrice);

        ApiInterface apiInterface= Api.getClient().create(ApiInterface.class);
        Call<List<ProductResponse>> call = apiInterface.getFilterList(filterResponse);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                Log.e("ResponseFilter", ""+response.body());
                productResponseList = response.body();
                if (productResponseList==null){
                    dismiss();
                } else {
                    dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.e("FilterError", ""+t.getMessage());
                dismiss();
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        getCategoryList();
        getBrandList();
    }

    private void filterItemListClicked(int position, View v) {
        if (position == 0) {

            recyclerViews.get(2).setVisibility(View.GONE);
            relativeLayouts.get(0).setVisibility(View.GONE);
            relativeLayouts.get(1).setVisibility(View.GONE);
            recyclerViews.get(1).setVisibility(View.VISIBLE);
            filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, MainFilterModel.SIZE);

        } else if (position == 1) {

            recyclerViews.get(1).setVisibility(View.GONE);
            relativeLayouts.get(0).setVisibility(View.GONE);
            relativeLayouts.get(1).setVisibility(View.GONE);
            recyclerViews.get(2).setVisibility(View.VISIBLE);
            brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, MainFilterModel.COLOR);

        } else if (position == 2) {

            recyclerViews.get(2).setVisibility(View.GONE);
            relativeLayouts.get(0).setVisibility(View.VISIBLE);
            relativeLayouts.get(1).setVisibility(View.GONE);
            recyclerViews.get(1).setVisibility(View.GONE);

        } else {

            recyclerViews.get(2).setVisibility(View.GONE);
            relativeLayouts.get(1).setVisibility(View.VISIBLE);
            relativeLayouts.get(0).setVisibility(View.GONE);
            recyclerViews.get(1).setVisibility(View.GONE);

        }

        recyclerViews.get(1).setAdapter(filterValAdapter);
        recyclerViews.get(2).setAdapter(brandValAdapter);

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

    private void getBrandList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<BrandResponse>> call = apiInterface.getBrandList();
        call.enqueue(new Callback<List<BrandResponse>>() {
            @Override
            public void onResponse(Call<List<BrandResponse>> call, Response<List<BrandResponse>> response) {

                brandResponseList = response.body();

                if (brandResponseList == null) {

                } else {
                    brandValAdapter = new BrandValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, brandResponseList, filterModels.size());
                    recyclerViews.get(2).setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViews.get(2).setAdapter(brandValAdapter);
                    recyclerViews.get(2).setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<BrandResponse>> call, Throwable t) {
                Log.e("CategoryListError", "" + t.getMessage());
            }
        });

    }

    private void getCategoryList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CategoryResponse>> call = apiInterface.getCategoryList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                categoryResponseList = response.body();

                if (categoryResponseList == null) {

                } else {
                    filterValAdapter = new FilterValRecyclerAdapter(getActivity(), R.layout.filter_list_val_item_layout, categoryResponseList, filterModels.size());
                    recyclerViews.get(1).setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViews.get(1).setAdapter(filterValAdapter);
                    recyclerViews.get(1).setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("CategoryListError", "" + t.getMessage());
            }
        });
    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) styledAttributes.getDimension(0, 0);
        return size;
    }


    public void setFilterList(List<ProductResponse> productResponseList) {

        this.productResponseList = productResponseList;
    }
}
