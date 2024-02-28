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
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemRecyclerView = view.findViewById(R.id.cart_items_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModal> cartItemModalList = new ArrayList<>();
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product1, "Product Title Here", 2, "Rs. 49,999/-", "Rs. 59,999/-", 1, 0, 0));
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product2, "Product Title Here", 0, "Rs. 49,999/-", "Rs. 59,999/-", 1, 1, 0));
        cartItemModalList.add(new CartItemModal(0, R.mipmap.product1, "Product Title Here", 2, "Rs. 49,999/-", "Rs. 59,999/-", 1, 0, 0));
        cartItemModalList.add(new CartItemModal(1, "3", "Rs. 1,49,999/-", "Free", "Rs. 1,49,999/-", "29,999"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModalList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        return view;
    }
}