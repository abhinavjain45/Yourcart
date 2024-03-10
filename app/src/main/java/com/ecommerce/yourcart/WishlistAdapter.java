package com.ecommerce.yourcart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModal> wishlistModalList;
    private Boolean wishlist;

    public WishlistAdapter(List<WishlistModal> wishlistModalList, Boolean wishlist) {
        this.wishlistModalList = wishlistModalList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = wishlistModalList.get(position).getWishlistProductImage();
        String productTitle = wishlistModalList.get(position).getWishlistProductTitle();
        long freeCouponCount = wishlistModalList.get(position).getWishlistFreeCouponNumber();
        String averageRating = wishlistModalList.get(position).getProductAverageRating();
        long totalRatingNumber = wishlistModalList.get(position).getProductTotalRatings();
        String displayPrice = wishlistModalList.get(position).getWishlistProductPrice();
        String displayCuttedPrice = wishlistModalList.get(position).getWishlistCuttedPrice();
        Boolean paymentMethod = wishlistModalList.get(position).getAvailablePaymentMethod();

        holder.setWishlistData(resource, productTitle, freeCouponCount, averageRating, totalRatingNumber, displayPrice, displayCuttedPrice, paymentMethod);
    }

    @Override
    public int getItemCount() {
        return wishlistModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView wishlistProductImage;
        private TextView wishlistProductTitle;
        private TextView freeCouponsNumber;
        private ImageView couponIcon;
        private TextView ratingAverage;
        private TextView totalRatingCount;
        private TextView wishlistProductPrice;
        private TextView wishlistCuttedPrice;
        private View wishlistPriceCutDivider;
        private TextView paymentMethodAvailable;
        private ImageButton wishlistItemDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlistProductImage = itemView.findViewById(R.id.wishlist_product_image);
            wishlistProductTitle = itemView.findViewById(R.id.wishlist_product_title);
            freeCouponsNumber = itemView.findViewById(R.id.wishlist_free_coupons_text);
            couponIcon = itemView.findViewById(R.id.wishlist_coupon_indicator);
            ratingAverage = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatingCount = itemView.findViewById(R.id.wishlist_total_ratings);
            wishlistProductPrice = itemView.findViewById(R.id.wishlist_product_price);
            wishlistCuttedPrice = itemView.findViewById(R.id.wishlist_cutted_price);
            wishlistPriceCutDivider = itemView.findViewById(R.id.wishlist_cutted_price_divider);
            paymentMethodAvailable = itemView.findViewById(R.id.wishlist_payment_method);
            wishlistItemDeleteButton = itemView.findViewById(R.id.wishlist_delete_button);
        }

        private void setWishlistData(String resource, String productTitle, long freeCouponCount, String averageRating, long totalRatingNumber, String displayPrice, String displayCuttedPrice, Boolean paymentMethod) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.productplaceholder)).into(wishlistProductImage);
            wishlistProductTitle.setText(productTitle);
            if (freeCouponCount!= 0) {
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponCount == 1) {
                    freeCouponsNumber.setText("Free " +freeCouponCount+ " Coupon");
                } else {
                    freeCouponsNumber.setText("Free " +freeCouponCount+ " Coupons");
                }
            } else {
                couponIcon.setVisibility(View.GONE);
                freeCouponsNumber.setVisibility(View.GONE);
            }
            ratingAverage.setText(averageRating);
            totalRatingCount.setText("(" +totalRatingNumber+ ") Ratings");
            wishlistProductPrice.setText("Rs. "+displayPrice+"/-");
            wishlistCuttedPrice.setText("Rs. "+displayCuttedPrice+"/-");
            if (paymentMethod) {
                paymentMethodAvailable.setVisibility(View.VISIBLE);
            } else {
                paymentMethodAvailable.setVisibility(View.GONE);
            }

            if (wishlist) {
                wishlistItemDeleteButton.setVisibility(View.VISIBLE);
            } else {
                wishlistItemDeleteButton.setVisibility(View.GONE);
            }
            wishlistItemDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
