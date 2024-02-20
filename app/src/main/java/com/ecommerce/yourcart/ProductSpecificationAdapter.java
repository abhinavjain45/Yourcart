package com.ecommerce.yourcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {
    private List<ProductSpecificationModal> productSpecificationModalList;

    public ProductSpecificationAdapter(List<ProductSpecificationModal> productSpecificationModalList) {
        this.productSpecificationModalList = productSpecificationModalList;
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder holder, int position) {
        String featureTitle = productSpecificationModalList.get(position).getFeatureName();
        String featureDetail = productSpecificationModalList.get(position).getFeatureValue();
        holder.setFeatures(featureTitle, featureDetail);
    }

    @Override
    public int getItemCount() {
        return productSpecificationModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView featureName;
        private TextView featureValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            featureName = itemView.findViewById(R.id.feature_name);
            featureValue = itemView.findViewById(R.id.feature_value);
        }

        private void setFeatures(String featureTitle, String featureDetail){
            featureName.setText(featureTitle);
            featureValue.setText(featureDetail);
        }
    }
}
