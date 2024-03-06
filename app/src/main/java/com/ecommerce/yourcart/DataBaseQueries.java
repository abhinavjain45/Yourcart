package com.ecommerce.yourcart;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataBaseQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModal> categoryModalList = new ArrayList<>();
    public static List<HomePageModal> homePageModalList = new ArrayList<>();

    public static void loadCategoriesForHomeCategorySlider(CategoryAdapter categoryAdapter, Context context) {
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModalList.add(new CategoryModal(documentSnapshot.get("categoryIcon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(HomePageAdapter adapter, Context context) {
        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
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
                                    homePageModalList.add(new HomePageModal(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("viewType") == 1) {
                                    homePageModalList.add(new HomePageModal(1, documentSnapshot.get("stripAdImage").toString(), documentSnapshot.get("stripAdBackground").toString()));
                                } else if ((long) documentSnapshot.get("viewType") == 2) {
                                    List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
                                    long numberOfProducts = (long) documentSnapshot.get("numberOfProducts");
                                    for (long x = 1; x <= numberOfProducts; x++) {
                                        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("productID" + x).toString(), documentSnapshot.get("productImage" + x).toString(), documentSnapshot.get("productTitle" + x).toString(), documentSnapshot.get("productSpecification" + x).toString(), documentSnapshot.get("productPrice" + x).toString()));
                                    }
                                    homePageModalList.add(new HomePageModal(2, documentSnapshot.get("layoutTitle").toString(), horizontalProductScrollModalList));
                                } else if ((long) documentSnapshot.get("viewType") == 3) {
                                    List<HorizontalProductScrollModal> gridProductModalList = new ArrayList<>();
                                    long numberOfProducts = (long) documentSnapshot.get("numberOfProducts");
                                    for (long x = 1; x <= numberOfProducts; x++) {
                                        gridProductModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("productID" + x).toString(), documentSnapshot.get("productImage" + x).toString(), documentSnapshot.get("productTitle" + x).toString(), documentSnapshot.get("productSpecification" + x).toString(), documentSnapshot.get("productPrice" + x).toString()));
                                    }
                                    homePageModalList.add(new HomePageModal(3, documentSnapshot.get("layoutTitle").toString(), gridProductModalList));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
