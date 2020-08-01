package com.mlmecommerce.Fragment;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreabaccega.widget.FormEditText;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Adapter.AreaAdapter;
import com.mlmecommerce.Adapter.CityAdapter;
import com.mlmecommerce.Adapter.CountryAdapter;
import com.mlmecommerce.Adapter.PincodeAdapter;
import com.mlmecommerce.Adapter.StateAdapter;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.Extra.RecyclerTouchListener;
import com.mlmecommerce.Model.AddressResponse;
import com.mlmecommerce.Model.AreaResponse;
import com.mlmecommerce.Model.CityResponse;
import com.mlmecommerce.Model.CountryResponse;
import com.mlmecommerce.Model.PinCodeResponse;
import com.mlmecommerce.Model.StateResponse;
import com.mlmecommerce.R;
import com.mlmecommerce.Retrofit.Api;
import com.mlmecommerce.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateAddress extends Fragment {

    View view;
    @BindViews({R.id.houseNo, R.id.buildingName, R.id.landmark, R.id.fullName, R.id.mobileNumber})
    List<FormEditText> formEditTexts;
    @BindViews({R.id.homeType, R.id.workType, R.id.otherType})
    List<TextView> textViews;
    @BindViews({R.id.countryTxt, R.id.stateTxt, R.id.cityTxt, R.id.areatxt, R.id.pincodeTxt})
    List<TextView> textViewList;
    List<CountryResponse> countryResponseList = new ArrayList<>();
    List<StateResponse> stateResponseList = new ArrayList<>();
    List<CityResponse> cityResponseList = new ArrayList<>();
    List<AreaResponse> areaResponseList = new ArrayList<>();
    List<PinCodeResponse> pinCodeResponseList = new ArrayList<>();
    String addressId, fullName, mobileNumber, countryId, countryName, stateId, stateName, cityId, cityName, houseNo, buildingName, areaId, areaName, landMark, pinCodeId, pinCodeName, addressType="", addressStatus;
    Dialog dialog;
    RecyclerView recyclerView;
    TextView close;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        formEditTexts.get(0).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(1).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(2).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(3).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        formEditTexts.get(0).setSelection(formEditTexts.get(0).getText().toString().length());
        formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());
        formEditTexts.get(2).setSelection(formEditTexts.get(2).getText().toString().length());
        formEditTexts.get(3).setSelection(formEditTexts.get(3).getText().toString().length());
        formEditTexts.get(4).setSelection(formEditTexts.get(4).getText().toString().length());

        Bundle bundle = getArguments();
        cityId = bundle.getString("cityId");
        cityName = bundle.getString("cityName");
        fullName = bundle.getString("fullName");
        mobileNumber = bundle.getString("mobileNumber");
        stateId = bundle.getString("stateId");
        stateName = bundle.getString("stateName");
        countryId = bundle.getString("countryId");
        countryName = bundle.getString("countryName");
        pinCodeId = bundle.getString("pinCodeId");
        pinCodeName = bundle.getString("pinCodeName");
        houseNo = bundle.getString("houseNo");
        buildingName = bundle.getString("buildingName");
        areaId = bundle.getString("areaId");
        areaName = bundle.getString("areaName");
        landMark = bundle.getString("landMark");
        addressType = bundle.getString("addressType");
        addressStatus = bundle.getString("addressStatus");
        addressId = bundle.getString("addressId");

        formEditTexts.get(0).setText(houseNo);
        formEditTexts.get(1).setText(buildingName);
        formEditTexts.get(2).setText(landMark);
        formEditTexts.get(3).setText(fullName);
        formEditTexts.get(4).setText(mobileNumber);

        textViewList.get(0).setText(countryName);
        textViewList.get(1).setText(stateName);
        textViewList.get(2).setText(cityName);
        textViewList.get(3).setText(areaName);
        textViewList.get(4).setText(pinCodeName);

        try {
            if (addressType.equals("Home")) {

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(0), R.color.white);

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(1), R.color.black);

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(2), R.color.black);

            } else if (addressType.equals("Work")) {

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(1), R.color.white);

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(0), R.color.black);

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(2), R.color.black);

                addressType = "Work";

            } else if (addressType.equals("Other")) {

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(2), R.color.white);

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(0), R.color.black);

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(1), R.color.black);

                addressType = "Other";

            } else {

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(0), R.color.black);

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(1), R.color.black);

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(2), R.color.black);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return view;

    }

    @OnClick({R.id.countryTxt, R.id.stateTxt, R.id.cityTxt, R.id.areatxt, R.id.pincodeTxt, R.id.homeType, R.id.workType, R.id.otherType, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.countryTxt:

                if (countryResponseList!=null) {

                    Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.country_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });


                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            CountryResponse countryResponse = countryResponseList.get(position);
                            textViewList.get(0).setText(countryResponseList.get(position).getCountryName());
                            countryId = countryResponse.getCountryId();
                            countryName = countryResponse.getCountryName();
                            getStateList(countryId);
                            dialog.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    try {

                        CountryAdapter countryAdapter = new CountryAdapter(getActivity(), countryResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(countryAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        countryAdapter.notifyDataSetChanged();
                        countryAdapter.notifyItemInserted(countryResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    dialog.show();

                } else {
                    textViewList.get(0).setText("");
                    textViewList.get(0).setHint("Select Country");
                    textViewList.get(1).setText("");
                    textViewList.get(1).setHint("Select State");
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                    Toasty.normal(getActivity(), "Select Other Country", Toasty.LENGTH_SHORT).show();
                }

                break;

            case R.id.stateTxt:

                if (stateResponseList!=null) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.country_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            StateResponse stateResponse = stateResponseList.get(position);
                            textViewList.get(1).setText(stateResponseList.get(position).getStateName());
                            stateId = stateResponse.getStateId();
                            stateName = stateResponse.getStateName();
                            getCityList(stateId);
                            dialog.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    try {

                        StateAdapter stateAdapter = new StateAdapter(getActivity(), stateResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(stateAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        stateAdapter.notifyDataSetChanged();
                        stateAdapter.notifyItemInserted(stateResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    dialog.show();

                } else {
                    textViewList.get(1).setText("");
                    textViewList.get(1).setHint("Select State");
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                    Toasty.normal(getActivity(), "Select Other Country", Toasty.LENGTH_SHORT).show();
                }


                break;

            case R.id.cityTxt:

                if (cityResponseList!=null) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.country_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            CityResponse cityResponse = cityResponseList.get(position);
                            textViewList.get(2).setText(cityResponseList.get(position).getCityName());
                            cityId = cityResponse.getCityId();
                            cityName = cityResponse.getCityName();
                            getAreaList(cityId);
                            dialog.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    try {

                        CityAdapter cityAdapter = new CityAdapter(getActivity(), cityResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(cityAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.notifyItemInserted(cityResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();

                } else {
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                    Toasty.normal(getActivity(), "Select Other State", Toasty.LENGTH_SHORT).show();
                }

                break;

            case R.id.areatxt:

                if (areaResponseList!=null) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.country_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            AreaResponse areaResponse = areaResponseList.get(position);
                            textViewList.get(3).setText(areaResponseList.get(position).getAreaName());
                            areaId = areaResponse.getAreaId();
                            areaName = areaResponse.getAreaName();
                            getPinCodeList(areaId);
                            dialog.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    try {

                        AreaAdapter areaAdapter = new AreaAdapter(getActivity(), areaResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(areaAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        areaAdapter.notifyDataSetChanged();
                        areaAdapter.notifyItemInserted(areaResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();
                }else {
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                    Toasty.normal(getActivity(), "Select Other City", Toasty.LENGTH_SHORT).show();
                }


                break;

            case R.id.pincodeTxt:

                if (pinCodeResponseList!=null) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.country_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            PinCodeResponse pinCodeResponse = pinCodeResponseList.get(position);
                            textViewList.get(4).setText(pinCodeResponseList.get(position).getPincodeName());
                            pinCodeId = pinCodeResponse.getPincodeId();
                            pinCodeName = pinCodeResponse.getPincodeName();
                            dialog.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    try {

                        PincodeAdapter pincodeAdapter = new PincodeAdapter(getActivity(), pinCodeResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(pincodeAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        pincodeAdapter.notifyDataSetChanged();
                        pincodeAdapter.notifyItemInserted(pinCodeResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();

                } else {
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                    Toasty.normal(getActivity(), "Select Other Area", Toasty.LENGTH_SHORT).show();
                }

                break;

            case R.id.homeType:

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(0), R.color.white);

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(1), R.color.black);

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(2), R.color.black);


                addressType = "Home";

                break;

            case R.id.workType:

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(1), R.color.white);

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(0), R.color.black);

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(2), R.color.black);

                addressType = "Work";

                break;

            case R.id.otherType:

                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.white));
                textViews.get(2).setBackground(getActivity().getResources().getDrawable(R.drawable.addressborder));
                setTextViewDrawableColor(textViews.get(2), R.color.white);

                textViews.get(0).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(0).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(0), R.color.black);

                textViews.get(1).setTextColor(getActivity().getResources().getColor(R.color.black));
                textViews.get(1).setBackground(getActivity().getResources().getDrawable(R.drawable.quantityback));
                setTextViewDrawableColor(textViews.get(1), R.color.black);

                addressType = "Other";

                break;
            case R.id.save:

                if (DetectConnection.checkInternetConnection(getActivity())){

                    if (formEditTexts.get(3).testValidity() && formEditTexts.get(4).testValidity() && formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity()) {

                        if (!textViewList.get(0).getText().toString().isEmpty()) {

                            if (!textViewList.get(1).getText().toString().isEmpty()) {

                                if (!textViewList.get(2).getText().toString().isEmpty()) {

                                    if (!textViewList.get(3).getText().toString().isEmpty()) {

                                        if (!textViewList.get(4).getText().toString().isEmpty()) {

                                            if (!addressType.equals("")) {
                                                updateAddress(formEditTexts.get(3).getText().toString(), formEditTexts.get(4).getText().toString(), formEditTexts.get(0).getText().toString(), formEditTexts.get(1).getText().toString(), countryId, stateId, cityId, areaId, formEditTexts.get(2).getText().toString(), pinCodeId);
                                            } else {
                                                Toasty.error(getActivity(), "Select Address Type", Toasty.LENGTH_SHORT, true).show();
                                            }
                                        } else {
                                            Toasty.normal(getActivity(), "Select Pincode", Toasty.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toasty.normal(getActivity(), "Select Area", Toasty.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toasty.normal(getActivity(), "Select City", Toasty.LENGTH_SHORT).show();
                                }
                            } else {
                                Toasty.normal(getActivity(), "Select State", Toasty.LENGTH_SHORT).show();
                            }
                        } else {
                            Toasty.normal(getActivity(), "Select Country", Toasty.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

                break;
        }
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getActivity().getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }


    private void updateAddress(String fullName, String mobileNumber, String houseNumber, String buildingName, String countryId, String stateId, String cityId, String areaId, String landMark, String pinCodeId) {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        String fullAddress="";
        if (landMark.isEmpty()) {
            fullAddress = houseNumber + ", " + buildingName  + ", " + areaName + ", " + cityName + "-" + pinCodeName;
        } else {
            fullAddress = houseNumber + ", " + buildingName + ", " + landMark + ", " + areaName + ", " + cityName + "-" + pinCodeName;
        }

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setCustomerAddressFullAddress(fullAddress);
        addressResponse.setCustomerAddressHouseNo(houseNumber);
        addressResponse.setCustomerAddressLandmark(landMark);
        addressResponse.setBuildingName(buildingName);
        addressResponse.setFullName(fullName);
        addressResponse.setMobileNumber(mobileNumber);
        addressResponse.setCustomerAddressStatus(addressStatus);
        addressResponse.setCustomerAddressType(addressType);
        addressResponse.setCustomerId(MainPage.userId);
        addressResponse.setCustomerAddressId(addressId);
        addressResponse.setPincodeId(pinCodeId);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<Boolean> call= apiInterface.updateAddress(addressResponse);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body().booleanValue()==true){
                    pDialog.dismiss();
                    Toasty.normal(getActivity(), "Address Updated", Toasty.LENGTH_SHORT).show();
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new AddressBook(), true);
                } else if (response.body().booleanValue()==false){
                    pDialog.dismiss();
                    Toasty.error(getActivity(), "Try Again", Toasty.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                pDialog.dismiss();
                Log.e("AddressError",""+t.getMessage());

            }
        });

    }

    private void getPinCodeList(String areaId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<PinCodeResponse>> call = apiInterface.getPinCodeList(areaId);
        call.enqueue(new Callback<List<PinCodeResponse>>() {
            @Override
            public void onResponse(Call<List<PinCodeResponse>> call, Response<List<PinCodeResponse>> response) {

                pinCodeResponseList = response.body();
                if (pinCodeResponseList==null){
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                } else {

                    PinCodeResponse pinCodeResponse = pinCodeResponseList.get(0);
                    textViewList.get(4).setText(pinCodeResponseList.get(0).getPincodeName());
                    pinCodeId = pinCodeResponse.getPincodeId();
                    pinCodeName = pinCodeResponse.getPincodeName();

                }
            }

            @Override
            public void onFailure(Call<List<PinCodeResponse>> call, Throwable t) {
                Log.e("CountryError", ""+t.getMessage());
            }
        });

    }

    private void getAreaList(String cityId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<AreaResponse>> call = apiInterface.getAreaList(cityId);
        call.enqueue(new Callback<List<AreaResponse>>() {
            @Override
            public void onResponse(Call<List<AreaResponse>> call, Response<List<AreaResponse>> response) {

                areaResponseList = response.body();
                if (areaResponseList==null){
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                } else {

                    AreaResponse areaResponse = areaResponseList.get(0);
                    textViewList.get(3).setText(areaResponseList.get(0).getAreaName());
                    areaId = areaResponse.getAreaId();
                    areaName = areaResponse.getAreaName();

                    getPinCodeList(areaId);

                }
            }

            @Override
            public void onFailure(Call<List<AreaResponse>> call, Throwable t) {
                Log.e("areaListError", ""+t.getMessage());
            }
        });

    }

    private void getCityList(String stateId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CityResponse>> call = apiInterface.getCityList(stateId);
        call.enqueue(new Callback<List<CityResponse>>() {
            @Override
            public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {

                cityResponseList = response.body();
                if (cityResponseList==null){
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                } else {

                    CityResponse cityResponse = cityResponseList.get(0);
                    textViewList.get(2).setText(cityResponseList.get(0).getCityName());
                    cityId = cityResponse.getCityId();
                    cityName = cityResponse.getCityName();

                    getAreaList(cityId);

                }

            }

            @Override
            public void onFailure(Call<List<CityResponse>> call, Throwable t) {
                Log.e("CountryError", ""+t.getMessage());
            }
        });

    }

    private void getStateList(String countryId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<StateResponse>> call = apiInterface.getStateList(countryId);
        call.enqueue(new Callback<List<StateResponse>>() {
            @Override
            public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {

                stateResponseList = response.body();
                if (stateResponseList==null){
                    textViewList.get(1).setText("");
                    textViewList.get(1).setHint("Select State");
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                } else {
                    for (int i =0;i<stateResponseList.size();i++) {

                        if (stateResponseList.get(i).getStateName().toLowerCase().contains("Maharashtra".toLowerCase().trim())) {

                            StateResponse stateResponse = stateResponseList.get(i);
                            textViewList.get(1).setText(stateResponseList.get(i).getStateName());
                            stateId = stateResponse.getStateId();
                            stateName = stateResponse.getStateName();

                            getCityList(stateId);

                        } else {
                            StateResponse stateResponse = stateResponseList.get(0);
                            textViewList.get(1).setText(stateResponseList.get(0).getStateName());
                            stateId = stateResponse.getStateId();
                            stateName = stateResponse.getStateName();
                            getCityList(stateId);
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                Log.e("CountryError", ""+t.getMessage());
            }
        });

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getCountryList();
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void getCountryList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<List<CountryResponse>> call = apiInterface.getCountryList();
        call.enqueue(new Callback<List<CountryResponse>>() {
            @Override
            public void onResponse(Call<List<CountryResponse>> call, Response<List<CountryResponse>> response) {

                countryResponseList = response.body();
                if (countryResponseList==null){
                    textViewList.get(0).setText("");
                    textViewList.get(0).setHint("Select Country");
                    textViewList.get(1).setText("");
                    textViewList.get(1).setHint("Select State");
                    textViewList.get(2).setText("");
                    textViewList.get(2).setHint("Select City");
                    textViewList.get(3).setText("");
                    textViewList.get(3).setHint("Select Area");
                    textViewList.get(4).setText("");
                    textViewList.get(4).setHint("Select Pincode");
                } else {

                    for (int i =0;i<countryResponseList.size();i++){

                        if (countryResponseList.get(i).getCountryName().toLowerCase().contains("India".toLowerCase().trim())) {

                            CountryResponse countryResponse = countryResponseList.get(i);
                            textViewList.get(0).setText(countryResponseList.get(i).getCountryName());
                            countryId  = countryResponse.getCountryId();
                            countryName  = countryResponse.getCountryName();

                            getStateList(countryId);

                        } else {
                            CountryResponse countryResponse = countryResponseList.get(0);
                            textViewList.get(0).setText(countryResponseList.get(0).getCountryName());
                            countryId  = countryResponse.getCountryId();
                            countryName  = countryResponse.getCountryName();
                            getStateList(countryId);
                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<List<CountryResponse>> call, Throwable t) {
                Log.e("CountryError", ""+t.getMessage());
            }
        });

    }

}

