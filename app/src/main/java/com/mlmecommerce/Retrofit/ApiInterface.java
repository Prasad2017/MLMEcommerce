package com.mlmecommerce.Retrofit;

import com.google.gson.JsonObject;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.Model.AreaResponse;
import com.mlmecommerce.Model.BrandResponse;
import com.mlmecommerce.Model.CartResponse;
import com.mlmecommerce.Model.CategoryResponse;
import com.mlmecommerce.Model.CityResponse;
import com.mlmecommerce.Model.CountryResponse;
import com.mlmecommerce.Model.CustomerResponse;
import com.mlmecommerce.Model.FilterResponse;
import com.mlmecommerce.Model.PinCodeResponse;
import com.mlmecommerce.Model.PostOrderResponse;
import com.mlmecommerce.Model.ProductImageResponse;
import com.mlmecommerce.Model.ProductResponse;
import com.mlmecommerce.Model.ProfileResponse;
import com.mlmecommerce.Model.StateResponse;
import com.mlmecommerce.Model.StatusResponse;
import com.mlmecommerce.Model.SubCategoryResponse;
import com.mlmecommerce.Model.WalletResponse;
import com.mlmecommerce.Model.WishListResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @POST("/customerLogin")
    Call<ProfileResponse> Login(@Body CustomerResponse customerResponse);


    @POST("/saveCustomer")
    Call<Boolean> Registration(@Body CustomerResponse customerResponse);


    @GET("/checkCustomerMobileNumber/{customerMobileNo}")
    Call<Boolean> checkCustomerMobileNumber(@Path("customerMobileNo") String mobileNumber);


    @GET("/checkCustomerMail")
    Call<Boolean> checkCustomerMail(@Query("customerMail") String mobileNumber);


    @POST("/customerForgotPassword")
    Call<StatusResponse> customerForgotPassword(@Body CustomerResponse customerResponse);


    @PUT("/customerUpdatePassword")
    Call<Boolean> customerUpdatePassword(@Body CustomerResponse customerResponse);


    @GET("/category/getList")
    Call<List<CategoryResponse>> getCategoryList();


    @GET("/subCategory/getList/{categoryId}")
    Call<List<SubCategoryResponse>> getSubCategoryList(@Path("categoryId") String categoryId);


    @GET("/product/categoryWise/{categoryId}/{customerId}")
    Call<List<ProductResponse>> getCategoryProductList(@Path("categoryId") String categoryId,
                                                       @Path("customerId") String customerId);


    @GET("/product/random/{limit}/{customerId}")
    Call<List<ProductResponse>> getAllCategoryProductList(@Path("limit") String categoryId,
                                                          @Path("customerId") String customerId);


    @GET("/product/subCategoryWise/{subCategoryId}/{customerId}")
    Call<List<ProductResponse>> getSubCategoryProductList(@Path("subCategoryId") String subCategoryId,
                                                          @Path("customerId") String customerId);


    @GET("/product/customerGetProduct/{productId}/{customerId}")
    Call<ProductResponse> getProductDetails(@Path("productId") int position,
                                            @Path("customerId") String customerId);


    @POST("/wishList")
    Call<Boolean> addToWishList(@Body StatusResponse statusResponse);

    @GET("/getCustomerAddress/{customerId}")
    Call<List<AddressResponse>> getAddress(@Path("customerId") String customerId);


    @GET("/country/active")
    Call<List<CountryResponse>> getCountryList();


    @GET("/state/active/country/{countryId}")
    Call<List<StateResponse>> getStateList(@Path("countryId") String countryId);


    @GET("/city/active/state/{stateId}")
    Call<List<CityResponse>> getCityList(@Path("stateId") String stateId);


    @GET("/area/active/city/{cityId}")
    Call<List<AreaResponse>> getAreaList(@Path("cityId") String cityId);


    @GET("/pincode/active/area/{areaId}")
    Call<List<PinCodeResponse>> getPinCodeList(@Path("areaId") String areaId);


    @GET("/wishList/getList/{customerId}")
    Call<List<ProductResponse>> getWishList(@Path("customerId") String userId);


    @GET("/wishList/delete/{wishListId}")
    Call<Boolean> removeToWishList(@Path("wishListId") String wishListId);

    @POST("/cart")
    Call<Boolean> addToCart(@Body CartResponse cartResponse);


    @GET("/cart/getList/{customerId}")
    Call<List<CartResponse>> getCartList(@Path("customerId") String userId);


    @GET("/cart/deleteCart/{cartId}")
    Call<Boolean> deleteCart(@Path("cartId") String cartId);


    @GET("/cart/plusQty/{cartId}")
    Call<Boolean> addProductCart(@Path("cartId") String cartId);


    @GET("/cart/minusQty/{cartId}")
    Call<Boolean> minusProductCart(@Path("cartId") String cartId);


    @GET("/getCustomerProfile/{customerId}")
    Call<CustomerResponse> getProfile(@Path("customerId") String userId);

    @POST("/saveCustomerAddress")
    Call<Boolean> saveAddress(@Body AddressResponse addressResponse);


    @PUT("/updateCustomerAddress")
    Call<Boolean> updateAddress(@Body AddressResponse addressResponse);


    @GET("/makeDefaultAddress/{customerAddressId}/{customerId}")
    Call<Boolean> markAsDefaultAddress(@Path("customerAddressId") String customerAddressId,
                                       @Path("customerId") String userId);


    @GET("/deleteAddress/{customerAddressId}")
    Call<Boolean> removeAddress(@Path("customerAddressId") String customerAddressId);


    @POST("/order")
    Call<Boolean> postOrder(@Body PostOrderResponse postOrderResponse);


    @GET("/cart/getCount/{customerId}")
    Call<Long> getCartCount(@Path("customerId") String userId);


    @GET("/pincode/checkPincode/{pincodeName}")
    Call<Boolean> checkPincode(@Path("pincodeName") String pincode);

    @GET("/brand/active")
    Call<List<BrandResponse>> getBrandList();


    @POST("/product/filterProduct")
    Call<List<ProductResponse>> getFilterList(@Body FilterResponse filterResponse);

    @GET("/getCustomerNameByReferenceId/{referenceId}")
    Call<StatusResponse> checkReferenceId(@Path("referenceId") String referenceId);

    @GET("/debited/customerWallet/{customerId}")
    Call<WalletResponse> getwalletdata(@Path("customerId") String userId);


}

