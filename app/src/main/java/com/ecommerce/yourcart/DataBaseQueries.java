package com.ecommerce.yourcart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModal> categoryModalList = new ArrayList<>();

    public static List<List<HomePageModal>> listHomePageModalList = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static List<String> wishlist = new ArrayList<>();
    public static List<WishlistModal> wishlistModalList = new ArrayList<>();

    public static List<String> ratedProductIDs = new ArrayList<>();
    public static List<Long> ratings = new ArrayList<>();

    public static List<String> cartlist = new ArrayList<>();
    public static List<CartItemModal> cartItemModalList = new ArrayList<>();

    public static int selectedAddress = -1;
    public static List<AddressesModal> addressesModalList = new ArrayList<>();

    public static void loadCategoriesForHomeCategorySlider(RecyclerView categoryRecyclerView, final Context context) {
        categoryModalList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModalList.add(new CategoryModal(documentSnapshot.get("categoryIcon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModalList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(RecyclerView homePageRecyclerView, final Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("BANNERS_DATA")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("viewType") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long numberOfBanners = (long) documentSnapshot.get("numberOfBanners");
                                    for (long x = 1; x <= numberOfBanners; x++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("slider" + x + "image").toString(), documentSnapshot.get("slider" + x + "background").toString()));
                                    }
                                    listHomePageModalList.get(index).add(new HomePageModal(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("viewType") == 1) {
                                    listHomePageModalList.get(index).add(new HomePageModal(1, documentSnapshot.get("stripAdImage").toString(), documentSnapshot.get("stripAdBackground").toString()));
                                } else if ((long) documentSnapshot.get("viewType") == 2) {

                                    List<WishlistModal> viewAllProductModalList = new ArrayList<>();

                                    List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
                                    long numberOfProducts = (long) documentSnapshot.get("numberOfProducts");
                                    for (long x = 1; x <= numberOfProducts; x++) {
                                        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("productID" + x).toString(), documentSnapshot.get("productImage" + x).toString(), documentSnapshot.get("productTitle" + x).toString(), documentSnapshot.get("productSpecification" + x).toString(), documentSnapshot.get("productPrice" + x).toString()));

                                        viewAllProductModalList.add(new WishlistModal(documentSnapshot.get("productID"+x).toString(),
                                                documentSnapshot.get("productImage"+x).toString(),
                                                documentSnapshot.get("productTitle"+x).toString(),
                                                (long) documentSnapshot.get("freeCouponsCount"+x),
                                                documentSnapshot.get("productAverageRating"+x).toString(),
                                                (long) documentSnapshot.get("productTotalRatings"+x),
                                                documentSnapshot.get("productPrice"+x).toString(),
                                                documentSnapshot.get("productCuttedPrice"+x).toString(),
                                                (boolean) documentSnapshot.get("codAvailable"+x),
                                                (boolean) documentSnapshot.get("inStock"+x)));
                                    }
                                    listHomePageModalList.get(index).add(new HomePageModal(2, documentSnapshot.get("layoutTitle").toString(), horizontalProductScrollModalList, viewAllProductModalList));
                                } else if ((long) documentSnapshot.get("viewType") == 3) {
                                    List<HorizontalProductScrollModal> gridProductModalList = new ArrayList<>();
                                    long numberOfProducts = (long) documentSnapshot.get("numberOfProducts");
                                    for (long x = 1; x <= numberOfProducts; x++) {
                                        gridProductModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("productID" + x).toString(), documentSnapshot.get("productImage" + x).toString(), documentSnapshot.get("productTitle" + x).toString(), documentSnapshot.get("productSpecification" + x).toString(), documentSnapshot.get("productPrice" + x).toString()));
                                    }
                                    listHomePageModalList.get(index).add(new HomePageModal(3, documentSnapshot.get("layoutTitle").toString(), gridProductModalList));
                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(listHomePageModalList.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishlistData(final Context context, Dialog dialog, final boolean loadProductData) {
        wishlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("WISHLIST_DATA")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (long x = 0; x < (long) task.getResult().get("listSize"); x++) {
                                wishlist.add(task.getResult().get("productID"+x).toString());

                                if (DataBaseQueries.wishlist.contains(ProductDetailsActivity.productID)) {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                                    if (ProductDetailsActivity.addToWishlistButton != null) {
                                        ProductDetailsActivity.addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorDanger));
                                    }
                                } else {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                                    if (ProductDetailsActivity.addToWishlistButton != null) {
                                        ProductDetailsActivity.addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.baseGrey));
                                    }
                                }

                                if (loadProductData) {
                                    wishlistModalList.clear();
                                    final String productID = task.getResult().get("productID"+x).toString();
                                    firebaseFirestore.collection("PRODUCTS").document(productID)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        wishlistModalList.add(new WishlistModal(productID,
                                                                task.getResult().get("productImage1").toString(),
                                                                task.getResult().get("productTitle").toString(),
                                                                (long) task.getResult().get("freeCouponsCount"),
                                                                task.getResult().get("productAverageRating").toString(),
                                                                (long) task.getResult().get("productTotalRatings"),
                                                                task.getResult().get("productPrice").toString(),
                                                                task.getResult().get("productCuttedPrice").toString(),
                                                                (boolean) task.getResult().get("codAvailable"),
                                                                (boolean) task.getResult().get("inStock")));

                                                        WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }

    public static void removeFromWishlist(int index, final Context context) {
        final String removedProductId = wishlist.get(index);
        wishlist.remove(index);
        Map<String, Object> updateWishlist = new HashMap<>();

        for (int x = 0; x < wishlist.size(); x++) {
            updateWishlist.put("productID"+x, wishlist.get(x));
        }
        updateWishlist.put("listSize", (long) wishlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("WISHLIST_DATA")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (wishlistModalList.size() != 0) {
                                wishlistModalList.remove(index);
                                WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            Toast.makeText(context, "Product Removed from Wishlist!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (ProductDetailsActivity.addToWishlistButton != null) {
                                ProductDetailsActivity.addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorDanger));
                            }
                            wishlist.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.runningWishlistQuery = false;
                    }
                });
    }

    public static void loadRatingList(final Context context) {
        if (!ProductDetailsActivity.runningRatingQuery) {
            ProductDetailsActivity.runningRatingQuery = true;
            ratedProductIDs.clear();
            ratings.clear();

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("RATINGS_DATA")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (long x = 0; x < (long) task.getResult().get("listSize"); x++) {
                                    ratedProductIDs.add(task.getResult().get("productID"+x).toString());
                                    ratings.add((long) task.getResult().get("rating"+x));

                                    if (task.getResult().get("productID"+x).toString().equals(ProductDetailsActivity.productID)) {
                                        ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating"+x))) - 1;
                                        if (ProductDetailsActivity.rateNowContainer != null) {
                                            ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                        }
                                    }
                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                            ProductDetailsActivity.runningRatingQuery = false;
                        }
                    });
        }
    }

    public static void loadCartList(final Context context, Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount) {
        cartlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("CART_DATA")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (long x = 0; x < (long) task.getResult().get("listSize"); x++) {
                                cartlist.add(task.getResult().get("productID"+x).toString());

                                if (DataBaseQueries.cartlist.contains(ProductDetailsActivity.productID)) {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                                } else {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                                }

                                if (loadProductData) {
                                    cartItemModalList.clear();
                                    final String productID = task.getResult().get("productID"+x).toString();
                                    firebaseFirestore.collection("PRODUCTS").document(productID)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @SuppressLint("NotifyDataSetChanged")
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        int index = 0;
                                                        if (cartlist.size() >= 2) {
                                                            index = cartlist.size() - 2;
                                                        }
                                                        cartItemModalList.add(index, new CartItemModal(CartItemModal.CART_ITEM,
                                                                productID,
                                                                task.getResult().get("productImage1").toString(),
                                                                task.getResult().get("productTitle").toString(),
                                                                (long) task.getResult().get("freeCouponsCount"),
                                                                task.getResult().get("productPrice").toString(),
                                                                task.getResult().get("productCuttedPrice").toString(),
                                                                (long) 1,
                                                                (long) 1,
                                                                (long) 1,
                                                                (boolean) task.getResult().get("inStock")));

                                                        if (cartlist.size() == 1) {
                                                            cartItemModalList.add(new CartItemModal(CartItemModal.CART_TOTAL));
//                                                            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
//                                                            parent.setVisibility(View.VISIBLE);
                                                        }
                                                        if (cartlist.size() == 0) {
                                                            cartItemModalList.clear();
                                                        }

                                                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }

                            if (cartlist.size() != 0) {
                                badgeCount.setVisibility(View.VISIBLE);
                            } else {
                                badgeCount.setVisibility(View.INVISIBLE);
                            }
                            if (DataBaseQueries.cartlist.size() < 99) {
                                badgeCount.setText(String.valueOf(DataBaseQueries.cartlist.size()));
                            } else {
                                badgeCount.setText("99");
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }

    public static void removeFromCart(int index, final Context context, final TextView cartTotalAmount) {
        final String removedProductId = cartlist.get(index);
        cartlist.remove(index);
        Map<String, Object> updateCartlist = new HashMap<>();

        for (int x = 0; x < cartlist.size(); x++) {
            updateCartlist.put("productID"+x, cartlist.get(x));
        }
        updateCartlist.put("listSize", (long) cartlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("CART_DATA")
                .set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (cartItemModalList.size() != 0) {
                                cartItemModalList.remove(index);
                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                            }
                            if (cartlist.size() == 0) {
                                LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                parent.setVisibility(View.GONE);
                                cartItemModalList.clear();
                            }
                            Toast.makeText(context, "Product Removed from Cart!", Toast.LENGTH_SHORT).show();
                        } else {
                            cartlist.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.runningCartQuery = false;
                    }
                });
    }

    public static void loadAddresses(final Context context, Dialog dialog) {
        addressesModalList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES_DATA")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            Intent deliveryIntent;
                            if ((long) task.getResult().get("listSize") == 0) {
                                deliveryIntent = new Intent(context, AddAddressActivity.class);
                                deliveryIntent.putExtra("INTENT", "deliveryIntent");
                            } else {
                                for (long x = 1; x < (long) task.getResult().get("listSize") + 1; x++) {
                                    addressesModalList.add(new AddressesModal(task.getResult().get("fullName"+x).toString(),
                                            task.getResult().get("address"+x).toString(),
                                            task.getResult().get("pincode"+x).toString(),
                                            (boolean) task.getResult().get("selected"+x)));

                                    if ((boolean) task.getResult().get("selected"+x)) {
                                        selectedAddress = Integer.parseInt(String.valueOf(x - 1));
                                    }
                                }
                                deliveryIntent = new Intent(context, DeliveryActivity.class);
                            }
                            context.startActivity(deliveryIntent);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }

    public static void clearData() {
        categoryModalList.clear();
        listHomePageModalList.clear();
        loadedCategoriesNames.clear();
        wishlist.clear();
        wishlistModalList.clear();
        cartlist.clear();
        cartItemModalList.clear();
    }
}
