package com.mlmecommerce.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.mlmecommerce.Activity.Login;
import com.mlmecommerce.Activity.MainPage;
import com.mlmecommerce.Extra.Blur;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Extra.DetectConnection;
import com.mlmecommerce.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class Settings extends Fragment {

    View view;
    @BindViews({R.id.userName, R.id.emailId})
    List<TextView> textViews;
    @BindView(R.id.viewProfile)
    TextView viewProfile;
    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainPage)getActivity()).loadFragment(new MyProfile(), true);
            }
        });

        textViews.get(0).setText(MainPage.firstName+" "+MainPage.lastName);
        textViews.get(1).setText(MainPage.emailId);
        try {

            Transformation blurTransformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    Bitmap blurred = Blur.fastblur(getActivity(), source, 10);
                    source.recycle();
                    return blurred;
                }

                @Override
                public String key() {
                    return "blur()";
                }
            };

            Picasso.with(getActivity())
                    .load(MainPage.profileImage)
                    .placeholder(R.drawable.profileimg)
                    .transform(blurTransformation)
                    .into(profileImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                            Picasso.with(getActivity())
                                    .load(MainPage.profileImage)
                                    .placeholder(profileImage.getDrawable())
                                    .into(profileImage);

                        }

                        @Override
                        public void onError() {
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

    @OnClick({R.id.cartlayout, R.id.ordersLayout, R.id.addressLayout,R.id.walletLayout,R.id.aboutLayout, R.id.rateLayout, R.id.supportLayout, R.id.feedbackLayout ,R.id.logoutLayout})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cartlayout:
                ((MainPage)getActivity()).loadFragment(new MyCart(), true);
                break;

            case R.id.ordersLayout:
              //  ((MainPage)getActivity()).loadFragment(new MyOrders(), true);
                break;

            case R.id.addressLayout:
                ((MainPage)getActivity()).loadFragment(new AddressBook(), true);
                break;

            case R.id.aboutLayout:
                ((MainPage)getActivity()).loadFragment(new About(), true);
                break;
            case R.id.walletLayout:
                ((MainPage)getActivity()).loadFragment(new wallet(), true);
                break;
            case R.id.rateLayout:
            case R.id.feedbackLayout:
                // perform click on Rate Category
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }

                break;

            case R.id.logoutLayout:
                logout();
                break;


        }
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {
            Common.getProfile(getActivity());
        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void logout() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.logout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView txtyes = dialog.findViewById(R.id.yes);
        TextView txtno = dialog.findViewById(R.id.no);

        txtno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Common.saveUserData(getActivity(), "accessToken", "");
                File file1 = new File("data/data/com.mlmecommerce/shared_prefs/user.xml");
                if (file1.exists()) {
                    file1.delete();
                }

                Intent intent = new Intent(getActivity(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        dialog.show();

    }


}