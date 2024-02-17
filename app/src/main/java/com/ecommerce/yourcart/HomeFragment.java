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

    ///////// Banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    ///////// Banner Slider

    //////// Strip Ad
    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;
    //////// Strip Ad

    /////// Horizontal Product Layout
    private TextView horizontalLayoutTitle;
    private Button horizontalLayoutViewAllButton;
    private RecyclerView horizontalRecyclerView;
    /////// Horizontal Product Layout

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

        List<CategoryModal> categoryModalList = new ArrayList<CategoryModal>();
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
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);

        sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider3, "#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.slider1, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider3, "#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.slider1, "#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.slider2, "#077AE4"));

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
        /////// Banner Slider

        ////// Strip Ad
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        stripAdContainer = view.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.mipmap.slider3);
        stripAdContainer.setBackgroundColor(Color.parseColor("#000000"));
        ////// Strip Ad

        /////// Horizontal Product Layout
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalLayoutViewAllButton = view.findViewById(R.id.horizontal_scroll_view_all_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recycler_view);

        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 1", "Product Specification", "Rs. 9543/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 2", "Product Specification", "Rs. 9876/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 3", "Product Specification", "Rs. 6754/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 4", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 5", "Product Specification", "Rs. 8765/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product3,"Demo Product 6", "Product Specification", "Rs. 2453/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product1,"Demo Product 7", "Product Specification", "Rs. 1652/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.mipmap.product2,"Demo Product 8", "Product Specification", "Rs. 1987/-"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModalList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        /////// Horizontal Product Layout

        /////// Grid Product Layout
        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllButton = view.findViewById(R.id.grid_product_layout_view_all_button);
        GridView gridView = view.findViewById(R.id.grid_product_layout_grid_view);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModalList));
        /////// Grid Product Layout

        /////////////////////////////////////////
        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModal> homePageModalList = new ArrayList<>();
        homePageModalList.add(new HomePageModal(0, sliderModelList));
        homePageModalList.add(new HomePageModal(1, R.mipmap.slider3, "#000000"));
        homePageModalList.add(new HomePageModal(2, "Deals of the Day", horizontalProductScrollModalList));
        homePageModalList.add(new HomePageModal(3, "#trending", horizontalProductScrollModalList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModalList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /////////////////////////////////////////

        return view;
    }

    /////// Banner Slider

    private void pageLooper(){
        if (currentPage == sliderModelList.size() - 2){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
        if (currentPage == 1){
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
    }

    private void startBannerSlideShow(){
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }

    private void stopBannerSlideShow(){
        timer.cancel();
    }
    /////// Banner Slider

}