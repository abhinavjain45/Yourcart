package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.DataBaseQueries.categoryModalList;
import static com.ecommerce.yourcart.DataBaseQueries.firebaseFirestore;
import static com.ecommerce.yourcart.DataBaseQueries.listHomePageModalList;
import static com.ecommerce.yourcart.DataBaseQueries.loadCategoriesForHomeCategorySlider;
import static com.ecommerce.yourcart.DataBaseQueries.loadFragmentData;
import static com.ecommerce.yourcart.DataBaseQueries.loadedCategoriesNames;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private List<CategoryModal> categoryModalPlaceholderList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private List<HomePageModal> homePageModalPlaceholderList = new ArrayList<>();
    private HomePageAdapter adapter;
    private ImageView noInternetImage;
    private Button connectionRetryButton;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout_refresh_main);
        noInternetImage = view.findViewById(R.id.no_internet_indicator);
        connectionRetryButton = view.findViewById(R.id.connection_retry_button);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.primary), getContext().getResources().getColor(R.color.primary), getContext().getResources().getColor(R.color.primary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        categoryModalPlaceholderList.add(new CategoryModal("null", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));
        categoryModalPlaceholderList.add(new CategoryModal("", ""));

        List<SliderModel> sliderModelPlaceholderList = new ArrayList<>();
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelPlaceholderList.add(new SliderModel("null", "#FFFFFF"));

        List<HorizontalProductScrollModal> horizontalProductScrollModalPlaceholderList = new ArrayList<>();
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalPlaceholderList.add(new HorizontalProductScrollModal("", "", "", "", ""));

        homePageModalPlaceholderList.add(new HomePageModal(0, sliderModelPlaceholderList));
        homePageModalPlaceholderList.add(new HomePageModal(1, "", "#FFFFFF"));
        homePageModalPlaceholderList.add(new HomePageModal(2, "", horizontalProductScrollModalPlaceholderList, new ArrayList<WishlistModal>()));
        homePageModalPlaceholderList.add(new HomePageModal(3, "", horizontalProductScrollModalPlaceholderList));

        categoryAdapter = new CategoryAdapter(categoryModalPlaceholderList);

        adapter = new HomePageAdapter(homePageModalPlaceholderList);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetImage.setVisibility(View.GONE);
            connectionRetryButton.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            if (categoryModalList.size() == 0) {
                loadCategoriesForHomeCategorySlider(categoryRecyclerView, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModalList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (listHomePageModalList.size() == 0) {
                loadedCategoriesNames.add("HOME");
                listHomePageModalList.add(new ArrayList<HomePageModal>());
                loadFragmentData(homePageRecyclerView, getContext(), 0, "home");
            } else {
                adapter = new HomePageAdapter(listHomePageModalList.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        } else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.nointernetindicator).into(noInternetImage);
            noInternetImage.setVisibility(View.VISIBLE);
            connectionRetryButton.setVisibility(View.VISIBLE);
        }

        ////// Refresh Layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });
        ////// Refresh Layout

        connectionRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });

        return view;
    }

    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModalList.clear();
        listHomePageModalList.clear();
        loadedCategoriesNames.clear();

        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetImage.setVisibility(View.GONE);
            connectionRetryButton.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModalPlaceholderList);
            adapter = new HomePageAdapter(homePageModalPlaceholderList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategoriesForHomeCategorySlider(categoryRecyclerView, getContext());
            loadedCategoriesNames.add("HOME");
            listHomePageModalList.add(new ArrayList<HomePageModal>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "home");

        } else {
            MainActivity.drawer.setDrawerLockMode(1);
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.mipmap.nointernetindicator).into(noInternetImage);
            noInternetImage.setVisibility(View.VISIBLE);
            connectionRetryButton.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}