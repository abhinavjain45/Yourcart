package com.ecommerce.yourcart;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModal> myOrderItemModalList;

    public MyOrderAdapter(List<MyOrderItemModal> myOrderItemModalList) {
        this.myOrderItemModalList = myOrderItemModalList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        int resource = myOrderItemModalList.get(position).getOrdersProductImage();
        int rating = myOrderItemModalList.get(position).getRatings();
        String title = myOrderItemModalList.get(position).getOrdersProductTitle();
        String orderStatusText = myOrderItemModalList.get(position).getOrderStatus();

        holder.setOrderData(resource, title, orderStatusText, rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModalList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView myOrderProductImage;
        private ImageView myOrderStatusIndicator;
        private TextView myOrderProductTitle;
        private TextView myOrderStatus;
        private LinearLayout rateNowContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myOrderProductImage = itemView.findViewById(R.id.my_order_product_image);
            myOrderProductTitle = itemView.findViewById(R.id.my_order_product_title);
            myOrderStatusIndicator = itemView.findViewById(R.id.order_status_indicator);
            myOrderStatus = itemView.findViewById(R.id.tv_order_status);
            rateNowContainer = itemView.findViewById(R.id.rate_now_container);
        }

        private void setOrderData(int resource, String title, String orderStatusText, int rating) {
            myOrderProductImage.setImageResource(resource);
            myOrderProductTitle.setText(title);
            if (orderStatusText.equals("Cancelled")) {
                myOrderStatusIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorDanger)));
            } else {
                myOrderStatusIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));
            }
            myOrderStatus.setText(orderStatusText);

            ////// Ratings Layout
            setRating(rating);
            for (int x = 0; x < rateNowContainer.getChildCount(); x++){
                final int starPosition = x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
            ////// Ratings Layout
        }

        private void setRating(int starPosition) {
            for (int x = 0; x < rateNowContainer.getChildCount(); x++){
                ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                if (x <= starPosition){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                }
            }
        }
    }
}
