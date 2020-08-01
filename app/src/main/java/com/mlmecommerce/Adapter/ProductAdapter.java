package com.mlmecommerce.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.net.http.LoggingEventHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Fragment.ProductDetails;
import com.mlmecommerce.Fragment.SubCategoryProduct;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.Model.ProductImageResponse;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.Model.SizeWiseProductResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    Context context;
    List<ProductResponse> productResponseList;
    public static PopupWindow popupwindow_obj;
    public static int index = 0;
    public static boolean isFirstTime = true;


    public ProductAdapter(Context context, List<ProductResponse> productResponseList) {

        this.context = context;
        this.productResponseList = productResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ProductResponse productResponse = productResponseList.get(position);
        Log.d("size products", productResponseList.size() + " " + (SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).size());
        holder.productName.setText(productResponseList.get(position).getProductName());

        if (isFirstTime) {
            try {

                double discountPercentage = Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get(position).getSizeWiseProductAmount()) - Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get(position).getSizeWiseProductFinalAmount());
                Log.d("percentage", discountPercentage + "");
                discountPercentage = (discountPercentage / Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get(position).getSizeWiseProductAmount())) * 100;
                if ((int) Math.round(discountPercentage) > 0) {
                    holder.offerPrice.setText(((int) Math.round(discountPercentage) + "% Off"));
                }
                holder.discountPrice.setText("You Pay "+MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get(position).getSizeWiseProductFinalAmount());
                holder.productPrice.setText(MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get(position).getSizeWiseProductAmount());
                //   holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.productName.setText(productResponseList.get(position).getProductName());
//            holder.productUnit.setText(productResponseList.get(position).getSizeWiseProductResponseList().get(position).getUnitName());
        } else {
            try {

                double discountPercentage = Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount()) - Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductFinalAmount());
                Log.d("percentage", discountPercentage + "");
                discountPercentage = (discountPercentage / Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount())) * 100;
                if ((int) Math.round(discountPercentage) > 0) {
                    holder.offerPrice.setText(((int) Math.round(discountPercentage) + "% Off"));
                }
                holder.discountPrice.setText("You Pay "+MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductFinalAmount());
                holder.productPrice.setText(MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount());
                //   holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.productName.setText(productResponseList.get(position).getProductName());
            holder.productUnit.setText(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getUnitName());
        }

        try {

            double discountPercentage = Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount()) - Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductFinalAmount());
            Log.d("percentage", discountPercentage + "");
            discountPercentage = (discountPercentage / Double.parseDouble(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount())) * 100;
            if ((int) Math.round(discountPercentage) > 0) {
                holder.offerPrice.setText(((int) Math.round(discountPercentage) + "% Off"));
            }
            holder.discountPrice.setText("You Pay "+MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductFinalAmount());
            holder.productPrice.setText(MainPage.currency + " " + productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductAmount());
            //   holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Picasso.with(context)
                    .load(productResponseList.get(position).getProductImageResponseList().get(0).getProductImagePath())
                    .placeholder(R.drawable.defaultimage)
                    .into(holder.productImage);
        } catch (Exception e){
            e.printStackTrace();
        }

        if (productResponseList.get(position).getSizeWiseProductResponseList().size() < 2) {
            holder.variantCount.setText("No more size");
        } else {
            holder.variantCount.setText((productResponseList.get(position).getSizeWiseProductResponseList().size() - 1) + " more size");
        }

        if (productResponseList.get(position).getWishListStatus().booleanValue()==true){
            holder.productWish.setBackgroundResource(R.drawable.favourite);
        } else  if (productResponseList.get(position).getWishListStatus().booleanValue()==false){
            holder.productWish.setBackgroundResource(R.drawable.unfavourite);
        }

        holder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDetails productDetail = new ProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("position", Integer.parseInt(productResponseList.get(position).getProductId()));
                productDetail.setArguments(bundle);
                ((MainPage) context).loadFragment(productDetail, true);

            }
        });

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDetails productDetail = new ProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("position", Integer.parseInt(productResponseList.get(position).getProductId()));
                productDetail.setArguments(bundle);
                ((MainPage) context).loadFragment(productDetail, true);

            }
        });

        holder.productWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DetectConnection.checkInternetConnection(context)){

                    if (productResponseList.get(position).getWishListStatus().booleanValue()==false) {

                        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                        pDialog.setTitleText("Please Wait");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.setCustomerId(Integer.valueOf(MainPage.userId));
                        statusResponse.setProductId(Integer.valueOf(productResponseList.get(position).getProductId()));

                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                        Call<Boolean> call = apiInterface.addToWishList(statusResponse);
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                if (response.body().booleanValue() == true) {
                                    pDialog.dismiss();

                                    Toast toast = Toast.makeText(context,"Product Added Into WishList", Toast.LENGTH_LONG);
                                    View view = toast.getView();
                                    view.getBackground().setColorFilter(context.getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                    TextView text = view.findViewById(android.R.id.message);
                                    text.setTextColor(context.getResources().getColor(R.color.white));
                                    toast.show();

                                    ((MainPage) context).removeCurrentFragmentAndMoveBack();
                                    SubCategoryProduct subCategoryProduct = new SubCategoryProduct();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("categoryId", productResponseList.get(position).getCategoryId());
                                    subCategoryProduct.setArguments(bundle);
                                    ((MainPage) context).loadFragment(subCategoryProduct, true);

                                } else if (response.body().booleanValue() == false) {
                                    pDialog.dismiss();
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
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

                    } else if (productResponseList.get(position).getWishListStatus().booleanValue()==true){

                        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                        pDialog.setTitleText("Please Wait");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                        Call<Boolean> call = apiInterface.removeToWishList(productResponseList.get(position).getWishListId());
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                if (response.body().booleanValue() == true) {
                                    pDialog.dismiss();

                                    Toast toast = Toast.makeText(context,"Product Removed From WishList", Toast.LENGTH_LONG);
                                    View view = toast.getView();
                                    view.getBackground().setColorFilter(context.getResources().getColor(R.color.red_900), PorterDuff.Mode.SRC_IN);
                                    TextView text = view.findViewById(android.R.id.message);
                                    text.setTextColor(context.getResources().getColor(R.color.white));
                                    toast.show();

                                    ((MainPage) context).removeCurrentFragmentAndMoveBack();
                                    SubCategoryProduct subCategoryProduct = new SubCategoryProduct();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("categoryId", productResponseList.get(position).getCategoryId());
                                    subCategoryProduct.setArguments(bundle);
                                    ((MainPage) context).loadFragment(subCategoryProduct, true);

                                } else if (response.body().booleanValue() == false) {
                                    pDialog.dismiss();
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
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

                } else {
                    Toasty.warning(context, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DetectConnection.checkInternetConnection(context)){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    CartResponse cartResponse = new CartResponse();
                    cartResponse.setProductId(productResponseList.get(position).getProductId());
                    cartResponse.setCustomerId(MainPage.userId);
                    cartResponse.setSizeWiseProductId(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductId());

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.addToCart(cartResponse);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue()==true){
                                pDialog.dismiss();
                                Toast toast = Toast.makeText(context,"Product Added Into Cart", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(context.getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(context.getResources().getColor(R.color.white));
                                toast.show();
                                Common.getCartCount(context);
                                ((MainPage) context).removeCurrentFragmentAndMoveBack();
                                SubCategoryProduct subCategoryProduct = new SubCategoryProduct();
                                Bundle bundle = new Bundle();
                                bundle.putString("categoryId", productResponseList.get(position).getCategoryId());
                                subCategoryProduct.setArguments(bundle);
                                ((MainPage) context).loadFragment(subCategoryProduct, true);

                            } else if (response.body().booleanValue()==false){
                                pDialog.dismiss();
                                Toasty.error(context, "Failed To Add", Toasty.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("CartError", ""+t.getMessage());
                        }
                    });

                } else {
                    Toasty.warning(context, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

            }
        });


        holder.quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DetectConnection.checkInternetConnection(context)){

                    final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    CartResponse cartResponse = new CartResponse();
                    cartResponse.setProductId(productResponseList.get(position).getProductId());
                    cartResponse.setCustomerId(MainPage.userId);
                    cartResponse.setSizeWiseProductId(productResponseList.get(position).getSizeWiseProductResponseList().get((SubCategoryProduct.selectedPosHashMap.get(SubCategoryProduct.viewPagerCurrentPos)).get(position)).getSizeWiseProductId());

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.addToCart(cartResponse);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response.body().booleanValue()==true){
                                pDialog.dismiss();
                                Toast toast = Toast.makeText(context,"Product Added Into Cart", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(context.getResources().getColor(R.color.green_600), PorterDuff.Mode.SRC_IN);
                                TextView text = view.findViewById(android.R.id.message);
                                text.setTextColor(context.getResources().getColor(R.color.white));
                                toast.show();

                                ((MainPage) context).removeCurrentFragmentAndMoveBack();
                                SubCategoryProduct subCategoryProduct = new SubCategoryProduct();
                                Bundle bundle = new Bundle();
                                bundle.putString("categoryId", productResponseList.get(position).getCategoryId());
                                subCategoryProduct.setArguments(bundle);
                                ((MainPage) context).loadFragment(subCategoryProduct, true);

                            } else if (response.body().booleanValue()==false){
                                pDialog.dismiss();
                                Toasty.error(context, "Failed To Add", Toasty.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pDialog.dismiss();
                            Log.e("CartError", ""+t.getMessage());
                        }
                    });

                } else {
                    Toasty.warning(context, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

            }
        });

    }

    public static void popUpDismiss() {
        popupwindow_obj.dismiss();
    }

    private PopupWindow popupDisplay(int position) {

        final PopupWindow popupWindow = new PopupWindow(context);

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mylayout, null);

        RecyclerView variantsRecyclerView = (RecyclerView) view.findViewById(R.id.variantsRecyclerView);
        setSizeData(variantsRecyclerView, position);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        return popupWindow;

    }

    private void setSizeData(RecyclerView variantsRecyclerView, int position) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        variantsRecyclerView.setLayoutManager(linearLayoutManager);
        ProductSizeAdapter productSizeAdapter = new ProductSizeAdapter(context, productResponseList.get(position).getSizeWiseProductResponseList(), position);
        variantsRecyclerView.setAdapter(productSizeAdapter);

    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }


    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productUnit, quantity, variantCount, discountPrice, offerPrice;
        ImageView minus, add, productImage, shareProduct, productWish;
        @BindView(R.id.variantsLayout)
        RelativeLayout variantsLayout;
        Animation slideUpAnimation,slideDownAnimation;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_dialog);
            slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);

            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            discountPrice = itemView.findViewById(R.id.discountPrice);
            offerPrice = itemView.findViewById(R.id.offerPrice);
            productUnit = itemView.findViewById(R.id.productUnit);
            quantity = itemView.findViewById(R.id.quantity);
            variantCount = itemView.findViewById(R.id.variantCount);
            minus = itemView.findViewById(R.id.minus);
            add = itemView.findViewById(R.id.add);
            productImage = itemView.findViewById(R.id.productImage);
            shareProduct = itemView.findViewById(R.id.shareProduct);
            productWish = itemView.findViewById(R.id.productWish);

        }

    }
}

