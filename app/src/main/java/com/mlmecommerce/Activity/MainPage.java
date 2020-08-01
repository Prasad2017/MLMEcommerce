package com.mlmecommerce.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mlmecommerce.Activity.BottomBar.CustomBottomBar;
import com.mlmecommerce.Activity.BottomBar.CustomBottomItem;
import com.mlmecommerce.Activity.BottomBar.ItemAdapter;
import com.mlmecommerce.Extra.Common;
import com.mlmecommerce.Fragment.AddressBook;
import com.mlmecommerce.Fragment.CategoryProduct;
import com.mlmecommerce.Fragment.Checkout;
import com.mlmecommerce.Fragment.Home;
import com.mlmecommerce.Fragment.MyCart;
import com.mlmecommerce.Fragment.MyProfile;
import com.mlmecommerce.Fragment.MyWishList;
import com.mlmecommerce.Fragment.Settings;
import com.mlmecommerce.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainPage extends AppCompatActivity {


    public static ImageView menu, back;
    public static DrawerLayout drawerLayout;
    public static TextView title, cartCount;
    public static LinearLayout toolbarContainer;
    public static String userId, cartId, currency = "₹", defaultAddress, firstName, middleName, lastName, mobileNumber, emailId, profileImage;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    public static BottomNavigationView bottomNavigationView;
    public static ProgressBar progressBar;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarContainer = findViewById(R.id.toolbar_container);
        initViews();

        userId = Common.getSavedUserData(MainPage.this, "userId");
        cartId = Common.getSavedUserData(MainPage.this, "cartId");

        loadFragment(new Home(), false);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        loadFragment(new Home(), false);
                        break;

                    case R.id.category:
                        loadFragment(new CategoryProduct(), true);
                        break;

                    case R.id.cart:
                        loadFragment(new MyCart(), true);
                        break;

                    case R.id.favourite:
                        loadFragment(new MyWishList(), true);
                        break;

                    case R.id.profile:
                        loadFragment(new Settings(), true);
                        break;

                }

                return false;
            }
        });

    }

    @SuppressLint("CutPasteId")
    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        title = findViewById(R.id.title);
        cartCount = (TextView) findViewById(R.id.cartCount);
        menu = (ImageView) findViewById(R.id.menu);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        back = findViewById(R.id.back);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

    }

    @SuppressLint("RtlHardcoded")
    @OnClick({R.id.menu, R.id.back, R.id.cart, R.id.cartCount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                if (!MainPage.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    MainPage.drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.back:
                removeCurrentFragmentAndMoveBack();
                break;
            case R.id.cart:
            case R.id.cartCount:
                if (!Home.swipeRefreshLayout.isRefreshing())
                    loadFragment(new MyCart(), true);

                break;

        }
    }


    @Override
    public void onBackPressed() {
        // double press to exit
        if (menu.getVisibility() == View.VISIBLE) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        } else {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toasty.normal(MainPage.this, "Press back once more to exit", Toasty.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void lockUnlockDrawer(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
        if (lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            menu.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        }

    }

    public void removeCurrentFragmentAndMoveBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}