package com.ecommerce.yourcart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
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
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        wishlistRecyclerView = view.findViewById(R.id.wishlist_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModal> wishlistModalList = new ArrayList<>();
        wishlistModalList.add(new WishlistModal(R.mipmap.product1, "Product Title Here", 2, "4.8", 268, "Rs. 49,999/-", "Rs. 59,999/-", "Cash on Delivery"));
        wishlistModalList.add(new WishlistModal(R.mipmap.product2, "Product Title Here", 1, "1.8", 15, "Rs. 4,999/-", "Rs. 9,999/-", "Cash on Delivery"));
        wishlistModalList.add(new WishlistModal(R.mipmap.product3, "Product Title Here", 0, "3.7", 187, "Rs. 9,999/-", "Rs. 19,999/-", "Cash on Delivery"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModalList);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}