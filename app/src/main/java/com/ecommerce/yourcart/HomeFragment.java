package com.ecommerce.yourcart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;

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

        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        final List<CategoryModal> categoryModalList = new ArrayList<CategoryModal>();
        categoryModalList.add(new CategoryModal("link", "Home"));
        categoryModalList.add(new CategoryModal("link", "Electronics"));
        categoryModalList.add(new CategoryModal("link", "Appliances"));
        categoryModalList.add(new CategoryModal("link", "Furniture"));
        categoryModalList.add(new CategoryModal("link", "Fashion"));
        categoryModalList.add(new CategoryModal("link", "Toys"));
        categoryModalList.add(new CategoryModal("link", "Sports"));
        categoryModalList.add(new CategoryModal("link", "Wall Arts"));
        categoryModalList.add(new CategoryModal("link", "Books"));
        categoryModalList.add(new CategoryModal("link", "Footwear"));

        categoryAdapter = new CategoryAdapter(categoryModalList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        /////// Banner Slider
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.slider1, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider3, "#077AE4"));
        /////// Banner Slider

        /////// Horizontal Product Layout
        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));
        /////// Horizontal Product Layout

        /////////////////////////////////////////
        testing = view.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModal> homePageModalList = new ArrayList<>();
        homePageModalList.add(new HomePageModal(0, sliderModelList));
        homePageModalList.add(new HomePageModal(1, R.mipmap.slider3, "#000000"));
        homePageModalList.add(new HomePageModal(2, "Deals of the Day", horizontalProductScrollModalList));
        homePageModalList.add(new HomePageModal(3, "#trending", horizontalProductScrollModalList));
        homePageModalList.add(new HomePageModal(2, "Latest This Week", horizontalProductScrollModalList));
        homePageModalList.add(new HomePageModal(3, "#fashion_week", horizontalProductScrollModalList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModalList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /////////////////////////////////////////

        return view;
    }
}