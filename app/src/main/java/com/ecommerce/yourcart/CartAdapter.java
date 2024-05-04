package com.ecommerce.yourcart;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModal> cartItemModalList;
    private int lastPosition = -1;
    private TextView cartTotalAmount;
    private Boolean showRemoveButton;

    public CartAdapter(List<CartItemModal> cartItemModalList, TextView cartTotalAmount, Boolean showRemoveButton) {
        this.cartItemModalList = cartItemModalList;
        this.cartTotalAmount = cartTotalAmount;
        this.showRemoveButton = showRemoveButton;
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
                String productID = cartItemModalList.get(position).getProductID();
                String resource = cartItemModalList.get(position).getProductImage();
                String title = cartItemModalList.get(position).getProductTitle();
                Long freeCouponsNumber = cartItemModalList.get(position).getFreeCoupons();
                String productPriceText = cartItemModalList.get(position).getProductPrice();
                String cuttedPriceText =  cartItemModalList.get(position).getCuttedPrice();
                Long offersAppliedNumber =  cartItemModalList.get(position).getOffersApplied();
                boolean inStock = cartItemModalList.get(position).isInStock();

                ((cartItemViewHolder)holder).setCartItemDetails(productID, resource, title, freeCouponsNumber, productPriceText, cuttedPriceText, offersAppliedNumber, inStock, position);
                break;
            case CartItemModal.CART_TOTAL:
                int totalItemsNumber = 0;
                int totalPrice = 0;
                String delivery;
                int grandTotal;
                int savedAmount = 0;

                for (int x = 0; x < cartItemModalList.size(); x++) {
                    if (cartItemModalList.get(x).getType() == CartItemModal.CART_ITEM && cartItemModalList.get(x).isInStock()) {
                        totalItemsNumber++;
                        totalPrice = totalPrice + Integer.parseInt(cartItemModalList.get(x).getProductPrice());
                    }
                }

                if (totalPrice > 1999) {
                    delivery = "FREE";
                    grandTotal = totalPrice;
                } else {
                    delivery = "89";
                    grandTotal = totalPrice + 89;
                }

                ((cartTotalAmountViewHolder)holder).setTotalAmount(totalItemsNumber, totalPrice, delivery, grandTotal, savedAmount);
                break;
            default:
                return;
        }

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
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
        private LinearLayout couponRedemptionLayout;
        private LinearLayout cartDeleteButton;

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
            couponRedemptionLayout = itemView.findViewById(R.id.coupon_redemption_layout);
            cartDeleteButton = itemView.findViewById(R.id.cart_remove_item_button);
        }

        private void setCartItemDetails(String productID, String resource, String title, Long freeCouponsNumber, String productPriceText, String cuttedPriceText, Long offersAppliedNumber, boolean inStock, int position) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.productplaceholder)).into(productImage);
            productTitle.setText(title);
            if (inStock) {
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
                productPrice.setText("Rs. " + productPriceText + "/-");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                cuttedPrice.setText("Rs. " + cuttedPriceText + "/-");
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
                productQuantity.setVisibility(View.VISIBLE);
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog qtyDialog = new Dialog(itemView.getContext());
                        qtyDialog.setContentView(R.layout.quantity_dialog);
                        qtyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        qtyDialog.setCancelable(false);

                        EditText quantityNumber = qtyDialog.findViewById(R.id.quantity_dialog_number);
                        Button qtyDialogCancelButton = qtyDialog.findViewById(R.id.qty_dialog_cancel_button);
                        Button qtyDialogContinueButton = qtyDialog.findViewById(R.id.qty_dialog_continue_button);

                        qtyDialogCancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                qtyDialog.dismiss();
                            }
                        });

                        qtyDialogContinueButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantity.setText("Qty: " + quantityNumber.getText());
                                qtyDialog.dismiss();
                            }
                        });
                        qtyDialog.show();
                    }
                });

                couponRedemptionLayout.setVisibility(View.VISIBLE);
            } else {
                productPrice.setText("Out of Stock");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorDanger));
                cuttedPrice.setText("");
                productQuantity.setVisibility(View.GONE);
                freeCoupons.setVisibility(View.GONE);
                couponsApplied.setVisibility(View.GONE);
                offersApplied.setVisibility(View.GONE);
                freeCouponIcon.setVisibility(View.GONE);
                couponRedemptionLayout.setVisibility(View.GONE);
            }

            if (showRemoveButton) {
                cartDeleteButton.setVisibility(View.VISIBLE);
            } else {
                cartDeleteButton.setVisibility(View.GONE);
            }
            cartDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.runningCartQuery) {
                        ProductDetailsActivity.runningCartQuery = true;

                        DataBaseQueries.removeFromCart(position, itemView.getContext(), cartTotalAmount);
                    }
                }
            });
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

        private void setTotalAmount(int totalItemsNumber, int totalPrice, String delivery, int grandTotal, int savedAmount) {
            totalCartItems.setText("Price (" + totalItemsNumber + " items):");
            totalItemsPrice.setText("Rs. " + totalPrice + "/-");
            if (delivery.equals("FREE")) {
                deliveryAmount.setText(delivery);
            } else {
                deliveryAmount.setText("Rs. " + delivery + "/-");
            }

            grandTotalAmount.setText("Rs. " + grandTotal + "/-");
            cartTotalAmount.setText("Rs. " + grandTotal + "/-");
            totalSavedPrice.setText("You saved Rs. " + savedAmount + "/- on this order");

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if (totalPrice == 0){
                DataBaseQueries.cartItemModalList.remove(DataBaseQueries.cartItemModalList.size()-1);
                parent.setVisibility(View.GONE);
            } else {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }
}
