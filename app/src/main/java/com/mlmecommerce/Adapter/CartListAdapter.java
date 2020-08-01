package com.mlmecommerce.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Fragment.Checkout;
import com.mlmecommerce.Fragment.MyCart;
import com.mlmecommerce.Fragment.ProductDetails;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    Context context;
    List<CartResponse> cartResponseList;
    double totalAmount = 0f, amountPayable;
    public static String totalAmountPayable;
    double tax=0f;


    public CartListAdapter(Context context, List<CartResponse> cartResponseList) {

        this.context = context;
        this.cartResponseList = cartResponseList;
        for(int position = 0 ;position<cartResponseList.size();position++){
            totalAmount = totalAmount + (Double.parseDouble(cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductFinalAmount()) * Double.parseDouble(cartResponseList.get(position).getCartQty()));
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_list, null);
        MyViewHolder CartListViewHolder = new MyViewHolder(view);
        return CartListViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if (position == cartResponseList.size() - 1) {

            holder.totalAmount.setVisibility(View.VISIBLE);
            holder.txtGurantee.setText(Html.fromHtml(context.getResources().getString(R.string.secure_payment_text)));

            holder.textViews.get(0).setText("Subtotal (" + cartResponseList.size() + " items) :");
            holder.textViews.get(1).setText(MainPage.currency + " " + totalAmount);

            amountPayable=totalAmount;

            holder.textViews.get(2).setText(MainPage.currency + " 0.0");
            holder.textViews.get(5).setText("Tax (0%)");

            holder.textViews.get(3).setText(MainPage.currency + " 0.0");
            holder.textViews.get(4).setText(MainPage.currency + " " + (String.format("%.2f", (amountPayable))));
            totalAmountPayable = (String.format("%.2f", (amountPayable)));
            Log.d("totalAmountPayable", totalAmountPayable);

        } else

            holder.totalAmount.setVisibility(View.GONE);
            holder.productName1.setText(cartResponseList.get(position).getProductName()+"-"+cartResponseList.get(position).getSubCategoryName());
            holder.price1.setText(MainPage.currency + " " + cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductFinalAmount());
            holder.quantity.setText("" + cartResponseList.get(position).getCartQty());
            try {
                Picasso.with(context)
                        .load(cartResponseList.get(position).getProductImageList().getProductImagePath())
                        .placeholder(R.drawable.defaultimage)
                        .into(holder.image1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!cartResponseList.get(position).getSizeWiseProductResponse().getSizeName().equalsIgnoreCase("")) {
                holder.size.setText("Size: " + cartResponseList.get(position).getSizeWiseProductResponse().getSizeName()+" "+cartResponseList.get(position).getSizeWiseProductResponse().getUnitName());
                holder.size.setVisibility(View.VISIBLE);
            } else {
                holder.size.setVisibility(View.GONE);
            }

            try {
                double discountPercentage = Double.parseDouble(cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductAmount()) - Double.parseDouble(cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductFinalAmount());
                Log.d("percentage", discountPercentage + "");
                discountPercentage = (discountPercentage / Double.parseDouble(cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductAmount())) * 100;
                if ((int) Math.round(discountPercentage) > 0)
                {
                    holder.discountPercentage1.setText(((int) Math.round(discountPercentage) + "% Off"));
                }

                holder.actualPrice1.setText(MainPage.currency + " " + cartResponseList.get(position).getSizeWiseProductResponse().getSizeWiseProductAmount());
                holder.actualPrice1.setPaintFlags(holder.actualPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            } catch (Exception e) {
                e.printStackTrace();
            }


        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDetails productDetail = new ProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("position", Integer.parseInt(cartResponseList.get(position).getProductId()));
                productDetail.setArguments(bundle);
                ((MainPage) context).loadFragment(productDetail, true);

            }
        });

        holder.productName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDetails productDetail = new ProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("position", Integer.parseInt(cartResponseList.get(position).getProductId()));
                productDetail.setArguments(bundle);
                ((MainPage) context).loadFragment(productDetail, true);

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                Call<Boolean> call = apiInterface.addProductCart(cartResponseList.get(position).getCartId());
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if (response.body().booleanValue()==true) {
                            progressDialog.dismiss();
                            ((MainPage) context).removeCurrentFragmentAndMoveBack();
                            ((MainPage) context).loadFragment(new MyCart(), true);
                        } else if (response.body().booleanValue()==false){
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("DeleteCart", ""+t.getMessage());
                    }
                });

            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                Call<Boolean> call = apiInterface.minusProductCart(cartResponseList.get(position).getCartId());
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if (response.body().booleanValue()==true) {
                            progressDialog.dismiss();
                            ((MainPage) context).removeCurrentFragmentAndMoveBack();
                            ((MainPage) context).loadFragment(new MyCart(), true);
                        } else if (response.body().booleanValue()==false){
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("DeleteCart", ""+t.getMessage());
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                Call<Boolean> call = apiInterface.deleteCart(cartResponseList.get(position).getCartId());
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        try {
                            if (response.body().booleanValue() == true) {
                                progressDialog.dismiss();
                                ((MainPage) context).removeCurrentFragmentAndMoveBack();
                                ((MainPage) context).loadFragment(new MyCart(), true);
                            } else if (response.body().booleanValue() == false) {
                                progressDialog.dismiss();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("DeleteCart", ""+t.getMessage());
                    }
                });

            }
        });

        holder.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainPage)context).loadFragment(new Checkout(), true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image1;
        ImageView delete, add, minus;
        TextView productName1, price1, actualPrice1, discountPercentage1, quantity, size, txtGurantee, proceed;
        CardView cardView1;
        @BindView(R.id.totalAmount)
        LinearLayout totalAmount;
        @BindViews({R.id.txtPrice, R.id.price, R.id.delivery,  R.id.tax,  R.id.amountPayable,  R.id.txtTax})
        List<TextView> textViews;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            image1 = (ImageView) itemView.findViewById(R.id.productImage1);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            add = (ImageView) itemView.findViewById(R.id.add);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            productName1 = (TextView) itemView.findViewById(R.id.productName1);
            size = (TextView) itemView.findViewById(R.id.size);
            proceed = (TextView) itemView.findViewById(R.id.proceed);
            price1 = (TextView) itemView.findViewById(R.id.price1);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            txtGurantee = (TextView) itemView.findViewById(R.id.txtGurantee);
            actualPrice1 = (TextView) itemView.findViewById(R.id.actualPrice1);
            discountPercentage1 = (TextView) itemView.findViewById(R.id.discountPercentage1);
            cardView1 = (CardView) itemView.findViewById(R.id.cardView1);

        }
    }
}
