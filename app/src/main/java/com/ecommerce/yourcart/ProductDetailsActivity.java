package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.MainActivity.showCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private TextView productTitle;
    private TextView productAverageRatingMinview;
    private TextView productTotalRatingsMiniview;
    private TextView productPrice;
    private TextView productCuttedPrice;
    private TextView rewardTitle;
    private TextView rewardBody;
    private FloatingActionButton addToWishlistButton;
    private static Boolean ALREADY_ADDED_TO_WISHLIST = false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;


    /////rating layout
    private LinearLayout rateNowContainer;
    private LinearLayout ratingsNumberContainer;
    private LinearLayout ratingsProgressBarContainer;
    private TextView totalRatingsCount;
    private TextView totalRatingsFigure;
    private TextView averageRating;
    /////rating layout

    ////coupons dialog
    public static TextView couponTitle;
    public static TextView couponValidity;
    public static TextView couponDetails;
    private static RecyclerView couponsRecyclerView;
    private static LinearLayout selectedCoupon;
    ////coupons dialog

    ////Product Description
    private ConstraintLayout productDescriptionLayout;
    private ConstraintLayout productDescriptionTabLayout;
    private List<ProductSpecificationModal> productSpecificationModalList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;
    private TextView productOnlyDescriptionBody;
    ////Product Description

    private Button buyNowButton;
    private Button couponRedeemButton;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishlistButton = findViewById(R.id.add_to_wishlist_button);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowButton = findViewById(R.id.buy_now_button);
        couponRedeemButton = findViewById(R.id.coupon_redemption_button);
        productTitle = findViewById(R.id.product_title);
        productAverageRatingMinview = findViewById(R.id.tv_product_rating_miniview);
        productTotalRatingsMiniview = findViewById(R.id.total_ratings_miniview);
        productPrice = findViewById(R.id.product_price);
        productCuttedPrice = findViewById(R.id.cutted_price);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDescriptionLayout = findViewById(R.id.product_details_container);
        productDescriptionTabLayout = findViewById(R.id.product_details_tabs_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatingsCount = findViewById(R.id.total_ratings_count);
        ratingsNumberContainer = findViewById(R.id.ratings_numbers_count_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progress_bar_container);
        averageRating = findViewById(R.id.average_rating);

        firebaseFirestore = FirebaseFirestore.getInstance();

        List<String> productImages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document("ujKpZSLeSLulTaScJzUu")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            long numberOfProductImages = (long) documentSnapshot.getLong("numberOfProductImages");
                            for (long x = 1; x <= numberOfProductImages; x++) {
                                productImages.add(documentSnapshot.get("productImage"+x).toString());
                            }
                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);

                            productTitle.setText(documentSnapshot.get("productTitle").toString());
                            productAverageRatingMinview.setText(documentSnapshot.get("productAverageRating").toString());
                            productTotalRatingsMiniview.setText("("+(long) documentSnapshot.get("productTotalRatings")+") Ratings");
                            productPrice.setText("Rs. "+documentSnapshot.get("productPrice").toString()+"/-");
                            productCuttedPrice.setText("Rs. "+documentSnapshot.get("productCuttedPrice").toString()+"/-");
                            rewardTitle.setText(documentSnapshot.get("freeCouponTitle").toString());
                            rewardBody.setText(documentSnapshot.get("freeCouponBody").toString());

                            if ((boolean) documentSnapshot.get("useTabLayout")) {
                                productDescriptionTabLayout.setVisibility(View.VISIBLE);
                                productDescriptionLayout.setVisibility(View.GONE);

                                productDescription = documentSnapshot.get("productDescription").toString();
                                productOtherDetails = documentSnapshot.get("productOtherDetails").toString();

                                long numberOfProductSpecificationTitles = (long) documentSnapshot.get("numberOfProductSpecificationTitles");
                                for (long x = 1; x <= numberOfProductSpecificationTitles; x++) {
                                    productSpecificationModalList.add(new ProductSpecificationModal(0, documentSnapshot.get("productSpecificationTitle"+x).toString()));

                                    for (long y = 1; y <= (long) documentSnapshot.get("numberOfFieldsInSpecificationTitle"+x); y++) {
                                        productSpecificationModalList.add(new ProductSpecificationModal(1, documentSnapshot.get("fieldName"+y+"SpecificationTitle"+x).toString(), documentSnapshot.get("fieldValue"+y+"SpecificationTitle"+x).toString()));
                                    }
                                }
                            } else {
                                productDescriptionTabLayout.setVisibility(View.GONE);
                                productDescriptionLayout.setVisibility(View.VISIBLE);

                                productOnlyDescriptionBody.setText(productDescription);
                            }

                            averageRating.setText(documentSnapshot.get("productAverageRating").toString());
                            totalRatingsCount.setText((long) documentSnapshot.get("productTotalRatings")+ " Ratings");
                            for (int x = 0; x < 5; x++) {
                                TextView individualRatings = (TextView) ratingsNumberContainer.getChildAt(x);
                                individualRatings.setText(String.valueOf((long) documentSnapshot.get("total"+(5-x)+"StarRatings")));

                                ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("productTotalRatings")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get("total"+(5-x)+"StarRatings"))));
                            }
                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("productTotalRatings")));

                            productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModalList));
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        if (ALREADY_ADDED_TO_WISHLIST) {
            addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
        } else {
            addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
        }
        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_ADDED_TO_WISHLIST) {
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                } else {
                    ALREADY_ADDED_TO_WISHLIST = true;
                    addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                }
            }
        });

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ///////rating layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }
        //////rating layout

        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

        ///// Coupon Dialog
        Dialog checkCouponDialog = new Dialog(ProductDetailsActivity.this);
        checkCouponDialog.setContentView(R.layout.coupon_redeem_dialog);
        checkCouponDialog.setCancelable(true);
        checkCouponDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView openCouponRecyclerView = checkCouponDialog.findViewById(R.id.coupon_dialog_toggle_button);
        couponsRecyclerView = checkCouponDialog.findViewById(R.id.coupon_dialog_recycler_view);
        selectedCoupon = checkCouponDialog.findViewById(R.id.coupon_dialog_selected_coupon);

        couponTitle = checkCouponDialog.findViewById(R.id.reward_item_title);
        couponValidity = checkCouponDialog.findViewById(R.id.reward_item_validity);
        couponDetails = checkCouponDialog.findViewById(R.id.reward_item_details);

        TextView originalPrice = checkCouponDialog.findViewById(R.id.coupon_dialog_original_price);
        TextView discountedPrice = checkCouponDialog.findViewById(R.id.coupon_dialog_discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModal> rewardModalList = new ArrayList<>();
        rewardModalList.add(new RewardModal("Cashback", "Valid Till: 02 April, 2024", "Get FLAT 20% CASHBACK on orders above Rs. 5,999/-"));
        rewardModalList.add(new RewardModal("Discount", "Valid Till: 31 March, 2024", "Get UPTO 60% OFF on orders above Rs. 17,999/-"));
        rewardModalList.add(new RewardModal("Value Added", "Valid Till: 05 March, 2024", "Get FREE Wired Earphones worth Rs. 599/- FREE on Purchase of iPhone 15 Pro"));
        rewardModalList.add(new RewardModal("Discount", "Valid Till: 09 December, 2024", "Get FLAT 10% CASHBACK on orders above Rs. 1,999/-"));
        rewardModalList.add(new RewardModal("BOGO", "Valid Till: 31 December, 2024", "Buy 1 Hoodie & Get 1 Hoodie Absolutely Free on orders above Rs. 999/-"));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModalList, true);
        couponsRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        openCouponRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        ///// Coupon Dialog

        couponRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCouponDialog.show();
            }
        });
    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerView.getVisibility() == View.GONE) {
            couponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    private void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
            if (x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}