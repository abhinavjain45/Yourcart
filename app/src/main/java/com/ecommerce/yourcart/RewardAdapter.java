package com.ecommerce.yourcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModal> rewardModalList;

    public RewardAdapter(List<RewardModal> rewardModalList) {
        this.rewardModalList = rewardModalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String rwTitle = rewardModalList.get(position).getRewardItemTitle();
        String rwValidity = rewardModalList.get(position).getRewardItemValidity();
        String rwDetails = rewardModalList.get(position).getRewardItemDetails();

        holder.setRewardsData(rwTitle, rwValidity, rwDetails);
    }

    @Override
    public int getItemCount() {
        return rewardModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rewardTitle;
        private TextView rewardValidity;
        private TextView rewardDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rewardTitle = itemView.findViewById(R.id.reward_item_title);
            rewardValidity = itemView.findViewById(R.id.reward_item_validity);
            rewardDetails = itemView.findViewById(R.id.reward_item_details);
        }

        private void setRewardsData(String rwTitle, String rwValidity, String rwDetails) {
            rewardTitle.setText(rwTitle);
            rewardValidity.setText(rwValidity);
            rewardDetails.setText(rwDetails);
        }
    }
}
