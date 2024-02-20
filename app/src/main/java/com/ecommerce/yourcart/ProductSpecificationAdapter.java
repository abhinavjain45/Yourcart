package com.ecommerce.yourcart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {
    private List<ProductSpecificationModal> productSpecificationModalList;

    public ProductSpecificationAdapter(List<ProductSpecificationModal> productSpecificationModalList) {
        this.productSpecificationModalList = productSpecificationModalList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModalList.get(position).getType()) {
            case 0:
                return ProductSpecificationModal.SPECIFICATION_TITLE;
            case 1:
                return ProductSpecificationModal.SPECIFICATION_BODY;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ProductSpecificationModal.SPECIFICATION_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDp(16, parent.getContext()), setDp(16, parent.getContext()), setDp(16, parent.getContext()), setDp(8, parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new ViewHolder(title);

            case ProductSpecificationModal.SPECIFICATION_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
                return new ViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder holder, int position) {
        switch (productSpecificationModalList.get(position).getType()) {
            case ProductSpecificationModal.SPECIFICATION_TITLE:
                holder.setTitle(productSpecificationModalList.get(position).getTitle());
                break;
            case ProductSpecificationModal.SPECIFICATION_BODY:
                String featureTitle = productSpecificationModalList.get(position).getFeatureName();
                String featureDetail = productSpecificationModalList.get(position).getFeatureValue();
                holder.setFeatures(featureTitle, featureDetail);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return productSpecificationModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void setTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);
        }

        private void setFeatures(String featureTitle, String featureDetail) {
            featureName = itemView.findViewById(R.id.feature_name);
            featureValue = itemView.findViewById(R.id.feature_value);
            featureName.setText(featureTitle);
            featureValue.setText(featureDetail);
        }
    }

    private int setDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
