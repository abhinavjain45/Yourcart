package com.ecommerce.yourcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModal> cartItemModalList;

    public CartAdapter(List<CartItemModal> cartItemModalList) {
        this.cartItemModalList = cartItemModalList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModalList.get(position).getType()) {
            case 0:
                return CartItemModal.CART_ITEM;
            case 1:
                return CartItemModal.CART_TOTAL;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModal.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartItemViewHolder(cartItemView);
            case CartItemModal.CART_TOTAL:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_breakup_layout, parent, false);
                return new cartTotalAmountViewHolder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModalList.get(position).getType()) {
            case CartItemModal.CART_ITEM:
                int resource = cartItemModalList.get(position).getProductImage();
                String title = cartItemModalList.get(position).getProductTitle();
                int freeCouponsNumber = cartItemModalList.get(position).getFreeCoupons();
                String productPriceText = cartItemModalList.get(position).getProductPrice();
                String cuttedPriceText =  cartItemModalList.get(position).getCuttedPrice();
                int offersAppliedNumber =  cartItemModalList.get(position).getOffersApplied();
                ((cartItemViewHolder)holder).setCartItemDetails(resource, title, freeCouponsNumber, productPriceText, cuttedPriceText, offersAppliedNumber);
                break;
            case CartItemModal.CART_TOTAL:
                String totalItemsNumber = cartItemModalList.get(position).getTotalItems();
                String totalPrice = cartItemModalList.get(position).getTotalItemsPrice();
                String delivery = cartItemModalList.get(position).getDeliveryPrice();
                String grandTotal = cartItemModalList.get(position).getTotalAmount();
                String savedAmount = cartItemModalList.get(position).getSavedAmount();
                ((cartTotalAmountViewHolder)holder).setTotalAmount(totalItemsNumber, totalPrice, delivery, grandTotal, savedAmount);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModalList.size();
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView freeCouponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;

        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_product_image);
            productTitle = itemView.findViewById(R.id.cart_product_title);
            freeCouponIcon = itemView.findViewById(R.id.cart_free_coupon_icon);
            freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            cuttedPrice = itemView.findViewById(R.id.cart_cutted_price);
            offersApplied = itemView.findViewById(R.id.tv_cart_offers_applied);
            couponsApplied = itemView.findViewById(R.id.tv_cart_coupon_applied);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
        }

        private void setCartItemDetails(int resource, String title, int freeCouponsNumber, String productPriceText, String cuttedPriceText, int offersAppliedNumber) {
            productImage.setImageResource(resource);
            ;
            productTitle.setText(title);
            if (freeCouponsNumber > 0) {
                freeCouponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                if (freeCouponsNumber == 1) {
                    freeCoupons.setText("Free " + freeCouponsNumber + " Coupon");
                } else {
                    freeCoupons.setText("Free " + freeCouponsNumber + " Coupons");
                }
            } else {
                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if (offersAppliedNumber > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                if (offersAppliedNumber == 1) {
                    offersApplied.setText(offersAppliedNumber + " Offer Applied");
                } else {
                    offersApplied.setText(offersAppliedNumber + " Offers Applied");
                }
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
        }
    }

    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder {
        private TextView totalCartItems;
        private TextView totalItemsPrice;
        private TextView deliveryAmount;
        private TextView grandTotalAmount;
        private TextView totalSavedPrice;

        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalCartItems = itemView.findViewById(R.id.cart_total_items);
            totalItemsPrice = itemView.findViewById(R.id.cart_total_items_price);
            deliveryAmount = itemView.findViewById(R.id.cart_delivery_charges);
            grandTotalAmount = itemView.findViewById(R.id.cart_total_price);
            totalSavedPrice = itemView.findViewById(R.id.cart_saved_amount);
        }

        private void setTotalAmount(String totalItemsNumber, String totalPrice, String delivery, String grandTotal, String savedAmount) {
            totalCartItems.setText("Price (" + totalItemsNumber + " items):");
            totalItemsPrice.setText(totalPrice);
            deliveryAmount.setText(delivery);
            grandTotalAmount.setText(grandTotal);
            totalSavedPrice.setText("You saved " + savedAmount + " on this order");
        }
    }
}
