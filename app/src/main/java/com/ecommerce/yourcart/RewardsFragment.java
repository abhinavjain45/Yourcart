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
 * Use the {@link RewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecylcerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardsFragment newInstance(String param1, String param2) {
        RewardsFragment fragment = new RewardsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        rewardsRecylcerView = view.findViewById(R.id.rewards_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardsRecylcerView.setLayoutManager(layoutManager);

        List<RewardModal> rewardModalList = new ArrayList<>();
        rewardModalList.add(new RewardModal("Cashback", "Valid Till: 02 April, 2024", "Get FLAT 20% CASHBACK on orders above Rs. 5,999/-"));
        rewardModalList.add(new RewardModal("Discount", "Valid Till: 31 March, 2024", "Get UPTO 60% OFF on orders above Rs. 17,999/-"));
        rewardModalList.add(new RewardModal("Value Added", "Valid Till: 05 March, 2024", "Get FREE Wired Earphones worth Rs. 599/- FREE on Purchase of iPhone 15 Pro"));
        rewardModalList.add(new RewardModal("Discount", "Valid Till: 09 December, 2024", "Get FLAT 10% CASHBACK on orders above Rs. 1,999/-"));
        rewardModalList.add(new RewardModal("BOGO", "Valid Till: 31 December, 2024", "Buy 1 Hoodie & Get 1 Hoodie Absolutely Free on orders above Rs. 999/-"));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModalList, false);
        rewardsRecylcerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        return view;
    }
}