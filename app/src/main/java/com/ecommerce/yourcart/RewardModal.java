package com.ecommerce.yourcart;

public class RewardModal {
    private String rewardItemTitle;
    private String rewardItemValidity;
    private String rewardItemDetails;

    public RewardModal(String rewardItemTitle, String rewardItemValidity, String rewardItemDetails) {
        this.rewardItemTitle = rewardItemTitle;
        this.rewardItemValidity = rewardItemValidity;
        this.rewardItemDetails = rewardItemDetails;
    }

    public String getRewardItemTitle() {
        return rewardItemTitle;
    }

    public void setRewardItemTitle(String rewardItemTitle) {
        this.rewardItemTitle = rewardItemTitle;
    }

    public String getRewardItemValidity() {
        return rewardItemValidity;
    }

    public void setRewardItemValidity(String rewardItemValidity) {
        this.rewardItemValidity = rewardItemValidity;
    }

    public String getRewardItemDetails() {
        return rewardItemDetails;
    }

    public void setRewardItemDetails(String rewardItemDetails) {
        this.rewardItemDetails = rewardItemDetails;
    }
}
