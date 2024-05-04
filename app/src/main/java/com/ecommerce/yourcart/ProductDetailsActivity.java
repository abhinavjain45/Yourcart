package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.MainActivity.showCart;
import static com.ecommerce.yourcart.RegisterActivity.setSignUpFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean runningCartQuery = false;
    public static boolean runningWishlistQuery = false;
    public static boolean runningRatingQuery = false;
    public static Activity productDetailsActivity;
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private TextView productTitle;
    private TextView productAverageRatingMinview;
    private TextView productTotalRatingsMiniview;
    private TextView productPrice;
    private TextView productCuttedPrice;
    private TextView rewardTitle;
    private TextView rewardBody;
    public static FloatingActionButton addToWishlistButton;
    public static Boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static Boolean ALREADY_ADDED_TO_CART = false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;


    /////rating layout
    public static int initialRating;
    public static LinearLayout rateNowContainer;
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

    private Dialog signInDialog;
    private Dialog loadingDialog;

    ////Product Description
    private ConstraintLayout productDescriptionLayout;
    private ConstraintLayout productDescriptionTabLayout;
    private List<ProductSpecificationModal> productSpecificationModalList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;
    private TextView productOnlyDescriptionBody;
    ////Product Description

    private LinearLayout addToCartButton;
    public static MenuItem cartItem;
    private Button buyNowButton;
    private LinearLayout couponRedeemLayout;
    private Button couponRedeemButton;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    public static String productID;
    private TextView badgeCount;

    private DocumentSnapshot documentSnapshot;

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
        addToCartButton = findViewById(R.id.add_to_cart_button);
        buyNowButton = findViewById(R.id.buy_now_button);
        couponRedeemLayout = findViewById(R.id.coupon_redemption_layout);
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

        initialRating = -1;

        ////Loading Dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        ////Loading Dialog

        firebaseFirestore = FirebaseFirestore.getInstance();

        List<String> productImages = new ArrayList<>();
        productID = String.valueOf(getIntent().getStringExtra("PRODUCT_ID"));
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            documentSnapshot = task.getResult();
                            long numberOfProductImages = (long) documentSnapshot.get("numberOfProductImages");
                            for (long x = 1; x <= numberOfProductImages; x++) {
                                productImages.add(documentSnapshot.get("productImage" + x).toString());
                            }
                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);

                            productTitle.setText(documentSnapshot.get("productTitle").toString());
                            productAverageRatingMinview.setText(documentSnapshot.get("productAverageRating").toString());
                            productTotalRatingsMiniview.setText("(" + (long) documentSnapshot.get("productTotalRatings") + ") Ratings");
                            productPrice.setText("Rs. " + documentSnapshot.get("productPrice").toString() + "/-");
                            productCuttedPrice.setText("Rs. " + documentSnapshot.get("productCuttedPrice").toString() + "/-");
                            rewardTitle.setText(documentSnapshot.get("freeCouponTitle").toString());
                            rewardBody.setText(documentSnapshot.get("freeCouponBody").toString());

                            if ((boolean) documentSnapshot.get("useTabLayout")) {
                                productDescriptionTabLayout.setVisibility(View.VISIBLE);
                                productDescriptionLayout.setVisibility(View.GONE);

                                productDescription = documentSnapshot.get("productDescription").toString();
                                productOtherDetails = documentSnapshot.get("productOtherDetails").toString();

                                long numberOfProductSpecificationTitles = (long) documentSnapshot.get("numberOfProductSpecificationTitles");
                                for (long x = 1; x <= numberOfProductSpecificationTitles; x++) {
                                    productSpecificationModalList.add(new ProductSpecificationModal(0, documentSnapshot.get("productSpecificationTitle" + x).toString()));

                                    for (long y = 1; y <= (long) documentSnapshot.get("numberOfFieldsInSpecificationTitle" + x); y++) {
                                        productSpecificationModalList.add(new ProductSpecificationModal(1, documentSnapshot.get("fieldName" + y + "SpecificationTitle" + x).toString(), documentSnapshot.get("fieldValue" + y + "SpecificationTitle" + x).toString()));
                                    }
                                }
                            } else {
                                productDescriptionTabLayout.setVisibility(View.GONE);
                                productDescriptionLayout.setVisibility(View.VISIBLE);

                                productOnlyDescriptionBody.setText(productDescription);
                            }

                            averageRating.setText(documentSnapshot.get("productAverageRating").toString());
                            totalRatingsCount.setText((long) documentSnapshot.get("productTotalRatings") + " Ratings");
                            for (int x = 0; x < 5; x++) {
                                TextView individualRatings = (TextView) ratingsNumberContainer.getChildAt(x);
                                individualRatings.setText(String.valueOf((long) documentSnapshot.get("total" + (5 - x) + "StarRatings")));

                                ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("productTotalRatings")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get("total" + (5 - x) + "StarRatings"))));
                            }
                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("productTotalRatings")));

                            productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModalList));

                            if (currentUser != null) {
                                if (DataBaseQueries.ratings.size() == 0) {
                                    DataBaseQueries.loadRatingList(ProductDetailsActivity.this);
                                }
                                if (DataBaseQueries.cartlist.size() == 0) {
                                    DataBaseQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                                }
                                if (DataBaseQueries.wishlist.size() == 0) {
                                    DataBaseQueries.loadWishlistData(ProductDetailsActivity.this, loadingDialog, false);
                                } else {
                                    loadingDialog.dismiss();
                                }
                            } else {
                                loadingDialog.dismiss();
                            }

                            if (DataBaseQueries.ratedProductIDs.contains(productID)) {
                                int index = DataBaseQueries.ratedProductIDs.indexOf(productID);
                                initialRating = Integer.parseInt(String.valueOf(DataBaseQueries.ratings.get(index))) - 1;
                                setRating(initialRating);
                            }

                            if (DataBaseQueries.cartlist.contains(productID)) {
                                ALREADY_ADDED_TO_CART = true;
                            } else {
                                ALREADY_ADDED_TO_CART = false;
                            }

                            if (DataBaseQueries.wishlist.contains(productID)) {
                                ALREADY_ADDED_TO_WISHLIST = true;
                                addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorDanger));
                            } else {
                                ALREADY_ADDED_TO_WISHLIST = false;
                                addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.baseGrey));
                            }

                            if ((boolean) documentSnapshot.get("inStock")) {
                                addToCartButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (currentUser == null) {
                                            signInDialog.show();
                                        } else {
                                            if (!runningCartQuery) {
                                                runningCartQuery = true;
                                                if (ALREADY_ADDED_TO_CART) {
                                                    runningCartQuery = false;
                                                    Toast.makeText(ProductDetailsActivity.this, "Already Added to Cart", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object> addProductID = new HashMap<>();
                                                    addProductID.put("productID" + String.valueOf(DataBaseQueries.cartlist.size()), productID);
                                                    addProductID.put("listSize", DataBaseQueries.cartlist.size() + 1);
                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("CART_DATA")
                                                            .update(addProductID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (DataBaseQueries.cartItemModalList.size() != 0) {
                                                                            DataBaseQueries.cartItemModalList.add(0, new CartItemModal(CartItemModal.CART_ITEM,
                                                                                    productID,
                                                                                    documentSnapshot.get("productImage1").toString(),
                                                                                    documentSnapshot.get("productTitle").toString(),
                                                                                    (long) documentSnapshot.get("freeCouponsCount"),
                                                                                    documentSnapshot.get("productPrice").toString(),
                                                                                    documentSnapshot.get("productCuttedPrice").toString(),
                                                                                    (long) 1,
                                                                                    (long) 0,
                                                                                    (long) 0,
                                                                                    (boolean) documentSnapshot.get("inStock")));
                                                                        }

                                                                        ALREADY_ADDED_TO_CART = true;
                                                                        DataBaseQueries.cartlist.add(productID);
                                                                        Toast.makeText(ProductDetailsActivity.this, "Product Added to Cart!", Toast.LENGTH_SHORT).show();
                                                                        invalidateOptionsMenu();
                                                                        runningCartQuery = false;
                                                                    } else {
                                                                        runningCartQuery = false;
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    }
                                });
                            } else {
                                buyNowButton.setVisibility(View.GONE);
                                TextView outOfStock = (TextView) addToCartButton.getChildAt(0);
                                outOfStock.setText("Out of Stock");
                                outOfStock.setTextColor(getResources().getColor(R.color.colorDanger));
                                outOfStock.setCompoundDrawables(null, null, null, null);
                            }

                        } else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!runningWishlistQuery) {
                        runningWishlistQuery = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DataBaseQueries.wishlist.indexOf(productID);
                            DataBaseQueries.removeFromWishlist(index, ProductDetailsActivity.this);

                            addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9F9F9F")));
                        } else {
                            addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorDanger));
                            Map<String, Object> addProductID = new HashMap<>();
                            addProductID.put("productID" + String.valueOf(DataBaseQueries.wishlist.size()), productID);
                            addProductID.put("listSize", DataBaseQueries.wishlist.size() + 1);
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("WISHLIST_DATA")
                                    .update(addProductID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (DataBaseQueries.wishlistModalList.size() != 0) {
                                                    DataBaseQueries.wishlistModalList.add(new WishlistModal(productID,
                                                            documentSnapshot.get("productImage").toString(),
                                                            documentSnapshot.get("productTitle").toString(),
                                                            (long) documentSnapshot.get("freeCouponsCount"),
                                                            documentSnapshot.get("productAverageRating").toString(),
                                                            (long) documentSnapshot.get("productTotalRatings"),
                                                            documentSnapshot.get("productPrice").toString(),
                                                            documentSnapshot.get("productCuttedPrice").toString(),
                                                            (boolean) documentSnapshot.get("codAvailable"),
                                                            (boolean) documentSnapshot.get("inStock")));
                                                }

                                                ALREADY_ADDED_TO_WISHLIST = true;
                                                addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorDanger));
                                                DataBaseQueries.wishlist.add(productID);
                                                Toast.makeText(ProductDetailsActivity.this, "Product Added to Wishlist!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }
                                            runningWishlistQuery = false;
                                        }
                                    });
                        }
                    }
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
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!runningRatingQuery) {
                                runningRatingQuery = true;

                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();
                                if (DataBaseQueries.ratedProductIDs.contains(productID)) {
                                    TextView oldRatings = (TextView) ratingsNumberContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRatings = (TextView) ratingsNumberContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put("total" + (initialRating + 1) + "StarRatings", Long.parseLong(oldRatings.getText().toString()) - 1);
                                    updateRating.put("total" + (starPosition + 1) + "StarRatings", Long.parseLong(finalRatings.getText().toString()) + 1);
                                    updateRating.put("productAverageRating", calculateAverageRating((long) starPosition - initialRating, true));
                                } else {
                                    updateRating.put("total" + (starPosition + 1) + "StarRatings", ((long) documentSnapshot.get("total" + (starPosition + 1) + "StarRatings") + 1));
                                    updateRating.put("productAverageRating", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("productTotalRatings", (long) documentSnapshot.get("productTotalRatings") + 1);
                                }

                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Map<String, Object> rating = new HashMap<>();
                                                    if (DataBaseQueries.ratedProductIDs.contains(productID)) {
                                                        rating.put("rating" + DataBaseQueries.ratedProductIDs.indexOf(productID), (long) starPosition + 1);
                                                    } else {
                                                        rating.put("listSize", (long) DataBaseQueries.ratedProductIDs.size() + 1);
                                                        rating.put("productID" + DataBaseQueries.ratedProductIDs.size(), productID);
                                                        rating.put("rating" + DataBaseQueries.ratedProductIDs.size(), (long) starPosition + 1);
                                                    }

                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("RATINGS_DATA")
                                                            .update(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (DataBaseQueries.ratedProductIDs.contains(productID)) {
                                                                            DataBaseQueries.ratings.set(DataBaseQueries.ratedProductIDs.indexOf(productID), (long) starPosition + 1);

                                                                            TextView oldRatings = (TextView) ratingsNumberContainer.getChildAt(5 - initialRating - 1);
                                                                            TextView finalRatings = (TextView) ratingsNumberContainer.getChildAt(5 - starPosition - 1);

                                                                            oldRatings.setText(String.valueOf(Integer.parseInt(oldRatings.getText().toString()) - 1));
                                                                            finalRatings.setText(String.valueOf(Integer.parseInt(finalRatings.getText().toString()) + 1));
                                                                        } else {
                                                                            DataBaseQueries.ratedProductIDs.add(productID);
                                                                            DataBaseQueries.ratings.add((long) starPosition + 1);

                                                                            productTotalRatingsMiniview.setText("(" + ((long) documentSnapshot.get("productTotalRatings") + 1) + ") Ratings");

                                                                            totalRatingsCount.setText((long) documentSnapshot.get("productTotalRatings") + 1 + " Ratings");

                                                                            TextView individualRatings = (TextView) ratingsNumberContainer.getChildAt(5 - starPosition - 1);
                                                                            individualRatings.setText(String.valueOf(Integer.parseInt(individualRatings.getText().toString()) + 1));

                                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("productTotalRatings") + 1));

                                                                            Toast.makeText(ProductDetailsActivity.this, "Product Rating Submitted!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        for (int x = 0; x < 5; x++) {
                                                                            TextView individualRatingFigures = (TextView) ratingsNumberContainer.getChildAt(x);

                                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                                            progressBar.setMax(maxProgress);
                                                                            progressBar.setProgress(Integer.parseInt(individualRatingFigures.getText().toString()));
                                                                        }
                                                                        initialRating = starPosition;

                                                                        productAverageRatingMinview.setText(calculateAverageRating(0, true));
                                                                        averageRating.setText(calculateAverageRating(0, true));

                                                                        if (DataBaseQueries.wishlist.contains(productID) && DataBaseQueries.wishlistModalList.size() != 0) {
                                                                            int index = DataBaseQueries.wishlist.indexOf(productID);
                                                                            DataBaseQueries.wishlistModalList.get(index).setProductAverageRating(averageRating.getText().toString());
                                                                            DataBaseQueries.wishlistModalList.get(index).setProductTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));
                                                                        }
                                                                    } else {
                                                                        setRating(initialRating);
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    runningRatingQuery = false;
                                                                }
                                                            });
                                                } else {
                                                    runningRatingQuery = false;
                                                    setRating(initialRating);
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
            });
        }
        //////rating layout

        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    DeliveryActivity.fromCart = false;
                    loadingDialog.show();
                    productDetailsActivity = ProductDetailsActivity.this;
                    DeliveryActivity.cartItemModalList = new ArrayList<>();
                    DeliveryActivity.cartItemModalList.add(new CartItemModal(CartItemModal.CART_ITEM,
                            productID,
                            documentSnapshot.get("productImage1").toString(),
                            documentSnapshot.get("productTitle").toString(),
                            (long) documentSnapshot.get("freeCouponsCount"),
                            documentSnapshot.get("productPrice").toString(),
                            documentSnapshot.get("productCuttedPrice").toString(),
                            (long) 1,
                            (long) 0,
                            (long) 0,
                            (boolean) documentSnapshot.get("inStock")));
                    DeliveryActivity.cartItemModalList.add(new CartItemModal(CartItemModal.CART_TOTAL));

                    if (DataBaseQueries.addressesModalList.size() == 0) {
                        DataBaseQueries.loadAddresses(ProductDetailsActivity.this, loadingDialog);
                    } else {
                        loadingDialog.dismiss();
                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }
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

        ////// SignIn Dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInButton = signInDialog.findViewById(R.id.qty_dialog_cancel_button);
        Button dialogSignUpButton = signInDialog.findViewById(R.id.qty_dialog_continue_button);

        Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseButton = true;
                SignUpFragment.disableCloseButton = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseButton = true;
                SignUpFragment.disableCloseButton = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        ////// SignIn Dialog
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            couponRedeemLayout.setVisibility(View.GONE);
        } else {
            couponRedeemLayout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {
            if (DataBaseQueries.ratings.size() == 0) {
                DataBaseQueries.loadRatingList(ProductDetailsActivity.this);
            }
            if (DataBaseQueries.wishlist.size() == 0) {
                DataBaseQueries.loadWishlistData(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }

        if (DataBaseQueries.ratedProductIDs.contains(productID)) {
            int index = DataBaseQueries.ratedProductIDs.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DataBaseQueries.ratings.get(index))) - 1;
            setRating(initialRating);
        }

        if (DataBaseQueries.cartlist.contains(productID)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }

        if (DataBaseQueries.wishlist.contains(productID)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorDanger));
        } else {
            ALREADY_ADDED_TO_WISHLIST = false;
            addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.baseGrey));
        }
        invalidateOptionsMenu();
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

    public static void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
            }
        }
    }

    private String calculateAverageRating(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNumber = (TextView) ratingsNumberContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingNumber.getText().toString()) * x);
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

        cartItem = menu.findItem(R.id.main_cart_icon);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.mipmap.cartwhite);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {
            if (DataBaseQueries.cartlist.size() == 0) {
                DataBaseQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
            } else {
                badgeCount.setVisibility(View.VISIBLE);
                if (DataBaseQueries.cartlist.size() < 99) {
                    badgeCount.setText(String.valueOf(DataBaseQueries.cartlist.size()));
                } else {
                    badgeCount.setText("99");
                }
            }
        }

        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                    showCart = true;
                    startActivity(cartIntent);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            productDetailsActivity = null;
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailsActivity = null;
        super.onBackPressed();
    }
}