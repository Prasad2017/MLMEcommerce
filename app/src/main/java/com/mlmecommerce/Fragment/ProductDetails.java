package com.mlmecommerce.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.android.material.snackbar.Snackbar;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.ProductImageAdapter;
import com.mlmecommerce.Adapter.SizeListAdapter;
import com.mlmecommerce.Adapter.UnitListAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.Model.ProductImageResponse;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.Model.SizeWiseProductResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetails extends Fragment {


    View view;
    public static int position;
    public static String wishListId, sizeProductId;
    List<SizeWiseProductResponse> sizeWiseProductResponseList = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<ProductImageResponse> productImageResponseList = new ArrayList<>();
    CirclePageIndicator indicator;
    public static ViewPager mPager;
    @BindView(R.id.productImageLayout)
    LinearLayout productImageLayout;
    @BindView(R.id.productImage)
    ImageView productImage;
    @BindView(R.id.sizeCardView)
    LinearLayout sizeCardView;
    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;
    @BindView(R.id.unitCardView)
    LinearLayout unitCardView;
    @BindView(R.id.unitRecyclerView)
    RecyclerView unitRecyclerView;
    public static FormEditText pincode;
    public static TextView productName, productPrice, discountPrice, offerPrice, buyNow, addToCart, productStatus, pincodeStatus;
    public static boolean wishStatus;
    WebView productDescription;
    public ImageView shareProduct, productWish;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        init();

        return view;

    }

    @OnClick({R.id.shareProduct, R.id.productWish, R.id.apply, R.id.buyNow, R.id.addToCart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.productWish:

                if (wishStatus==false) {

                    final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    StatusResponse statusResponse = new StatusResponse();
                    statusResponse.setCustomerId(Integer.valueOf(MainPage.userId));
                    statusResponse.setProductId(position);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.addToWishList(statusResponse);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue() == true) {
                                pDialog.dismiss();

                                Toast toast = Toast.makeText(getActivity(),"Product Added Into WishList", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(getActivity().getResources().getColor(R.color.white));
                                toast.show();

                                getProductDetails();

                            } else if (response.body().booleanValue() == false) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                pDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("wishError", "" + t.getMessage());
                        }
                    });

                } else if (wishStatus==true){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.removeToWishList(wishListId);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue() == true) {
                                pDialog.dismiss();

                                Toast toast = Toast.makeText(getActivity(),"Product Removed From WishList", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.red_900), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(getActivity().getResources().getColor(R.color.white));
                                toast.show();

                               getProductDetails();

                            } else if (response.body().booleanValue() == false) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                pDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("wishError", "" + t.getMessage());
                        }
                    });

                }

                break;

            case R.id.apply:

                if (pincode.testValidity()){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    pincodeStatus.setVisibility(View.GONE);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.checkPincode(pincode.getText().toString().trim());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue()==true){
                                pDialog.dismiss();
                                pincodeStatus.setText("Delivery available on this pincode");
                                pincodeStatus.setTextColor(getActivity().getResources().getColor(R.color.green_700));
                                pincodeStatus.setVisibility(View.VISIBLE);
                            } else if (response.body().booleanValue()==false){
                                pDialog.dismiss();
                                pincodeStatus.setText("Delivery not available on this pincode");
                                pincodeStatus.setTextColor(getActivity().getResources().getColor(R.color.red_900));
                                pincodeStatus.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("pinError", ""+t.getMessage());
                            pincodeStatus.setVisibility(View.GONE);
                        }
                    });

                }

                break;

            case R.id.buyNow:

                if (DetectConnection.checkInternetConnection(getActivity())){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    CartResponse cartResponse = new CartResponse();
                    cartResponse.setProductId(""+position);
                    cartResponse.setCustomerId(MainPage.userId);
                    cartResponse.setSizeWiseProductId(sizeProductId);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.addToCart(cartResponse);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue()==true){

                                pDialog.dismiss();
                                Toast toast = Toast.makeText(getActivity(),"Product Added Into Cart", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(getActivity().getResources().getColor(R.color.white));
                                toast.show();

                                ((MainPage) getActivity()).loadFragment(new Checkout(), false);

                            } else if (response.body().booleanValue()==false){
                                pDialog.dismiss();
                                Toasty.error(getActivity(), "Failed to buy", Toasty.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("CartError", ""+t.getMessage());
                        }
                    });

                }  else {
                    Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

                break;

            case R.id.addToCart:

                if (DetectConnection.checkInternetConnection(getActivity())){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    CartResponse cartResponse = new CartResponse();
                    cartResponse.setProductId(""+position);
                    cartResponse.setCustomerId(MainPage.userId);
                    cartResponse.setSizeWiseProductId(sizeProductId);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.addToCart(cartResponse);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue()==true){

                                pDialog.dismiss();
                                Toast toast = Toast.makeText(getActivity(),"Product Added Into Cart", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(getActivity().getResources().getColor(R.color.white));
                                toast.show();

                                getProductDetails();

                            } else if (response.body().booleanValue()==false){
                                pDialog.dismiss();
                                Toasty.error(getActivity(), "Failed to add", Toasty.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("CartError", ""+t.getMessage());
                        }
                    });

                } else {
                    Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

                break;

        }
    }

    private void init() {

        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        productName = (TextView) view.findViewById(R.id.productName);
        productPrice = (TextView) view.findViewById(R.id.productPrice);
        discountPrice = (TextView) view.findViewById(R.id.discountPrice);
        offerPrice = (TextView) view.findViewById(R.id.offerPrice);
        pincodeStatus = (TextView) view.findViewById(R.id.pincodeStatus);
        pincode = (FormEditText) view.findViewById(R.id.pincode);
        addToCart = (TextView) view.findViewById(R.id.addToCart);
        buyNow = (TextView) view.findViewById(R.id.buyNow);
        productStatus = (TextView) view.findViewById(R.id.productStatus);
        productDescription = (WebView) view.findViewById(R.id.productDescription);
        shareProduct = (ImageView) view.findViewById(R.id.shareProduct);
        productWish = (ImageView) view.findViewById(R.id.productWish);

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getProductDetails();
            Common.getCartCount(getActivity());
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getProductDetails() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ProductResponse> call = apiInterface.getProductDetails(position, MainPage.userId);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                pDialog.dismiss();
                try {

                    productImageResponseList = (ArrayList<ProductImageResponse>) response.body().getProductImageResponseList();
                    sizeWiseProductResponseList = response.body().getSizeWiseProductResponseList();
                    productDescription.loadDataWithBaseURL(null, response.body().getProductDescription(), "text/html", "utf-8", null);

                    ProductDetails.productStatus.setText(""+sizeWiseProductResponseList.get(0).getSizeWiseProductStatus());
                    ProductDetails.sizeProductId = sizeWiseProductResponseList.get(0).getSizeWiseProductId();
                    ProductDetails.productName.setText(response.body().getProductName());
                    wishStatus = response.body().getWishListStatus();
                    wishListId = response.body().getWishListId();

                    if (response.body().getWishListStatus().booleanValue()==true){
                        productWish.setBackgroundResource(R.drawable.favourite);
                    } else  if (response.body().getWishListStatus().booleanValue()==false){
                        productWish.setBackgroundResource(R.drawable.unfavourite);
                    }

                    try {

                        double discountPercentage = Double.parseDouble(sizeWiseProductResponseList.get(0).getSizeWiseProductAmount()) - Double.parseDouble(sizeWiseProductResponseList.get(0).getSizeWiseProductFinalAmount());
                        Log.d("percentage", discountPercentage + "");
                        discountPercentage = (discountPercentage / Double.parseDouble(sizeWiseProductResponseList.get(0).getSizeWiseProductAmount())) * 100;
                        if ((int) Math.round(discountPercentage) > 0) {
                            ProductDetails.offerPrice.setText(((int) Math.round(discountPercentage) + "% Off"));
                        }
                        ProductDetails.discountPrice.setText("You Pay "+MainPage.currency + " " + sizeWiseProductResponseList.get(0).getSizeWiseProductFinalAmount());
                        ProductDetails.productPrice.setText(MainPage.currency + " " + sizeWiseProductResponseList.get(0).getSizeWiseProductAmount());
                        ProductDetails.productPrice.setPaintFlags(ProductDetails.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (productImageResponseList.size() > 0) {
                        mPager.setAdapter(new ProductImageAdapter(getActivity(), productImageResponseList));
                        indicator.setViewPager(mPager);
                        productImageLayout.setVisibility(View.VISIBLE);
                        productImage.setVisibility(View.GONE);
                    } else {
                        productImageLayout.setVisibility(View.GONE);
                        productImage.setVisibility(View.VISIBLE);
                    }

                    final float density = getResources().getDisplayMetrics().density;
                    indicator.setRadius(5 * density);
                    //Set circle indicator radius
                    indicator.setRadius(5 * density);

                    NUM_PAGES = productImageResponseList.size();
                    // Auto start of viewpager
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {

                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }
                            mPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 5000, 5000);

                    // Pager listener over indicator
                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;
                        }

                        @Override
                        public void onPageScrolled(int position, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int position) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (sizeWiseProductResponseList!=null) {
                    setSizeListData();
                   // setUnitListData();
                } else {
                    sizeCardView.setVisibility(View.GONE);
                    //unitCardView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                pDialog.dismiss();
                Log.e("ProductError", ""+t.getMessage());
            }
        });
    }

    private void setSizeListData() {

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        sizeRecyclerView.setLayoutManager(flowLayoutManager);
        SizeListAdapter topListAdapter = new SizeListAdapter(getActivity(), sizeWiseProductResponseList);
        sizeRecyclerView.setAdapter(topListAdapter);

    }


}