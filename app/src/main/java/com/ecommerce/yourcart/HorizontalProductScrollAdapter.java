package com.ecommerce.yourcart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModal> horizontalProductScrollModalList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        int resource = horizontalProductScrollModalList.get(position).getProductImage();
        String title = horizontalProductScrollModalList.get(position).getProductTitle();
        String specification = horizontalProductScrollModalList.get(position).getProductSpecification();
        String price = horizontalProductScrollModalList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductSpecification(specification);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollModalList.size() > 8) {
            return 8;
        } else {
            return horizontalProductScrollModalList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productSpecification;
        private TextView productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.horizontal_scroll_product_image);
            productTitle = itemView.findViewById(R.id.horizontal_scroll_product_title);
            productSpecification = itemView.findViewById(R.id.horizontal_scroll_product_specification);
            productPrice = itemView.findViewById(R.id.horizontal_scroll_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }

        private void setProductImage(int resource) {
            if (productImage != null)  {
                productImage.setImageResource(resource);
            }
        }

        private void setProductTitle(String title) {
            if (productTitle != null) {
                productTitle.setText(title);
            }
        }

        private void setProductSpecification(String specification) {
            if (productSpecification != null) {
                productSpecification.setText(specification);
            }
        }

        private void setProductPrice(String price) {
            if (productPrice != null) {
                productPrice.setText(price);
            }
        }
    }
}
