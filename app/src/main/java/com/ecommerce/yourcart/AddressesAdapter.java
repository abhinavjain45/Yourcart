package com.ecommerce.yourcart;

import static com.ecommerce.yourcart.DeliveryActivity.SELECT_ADDRESS;
import static com.ecommerce.yourcart.MyAccountFragment.MANAGE_ADDRESS;
import static com.ecommerce.yourcart.MyAddressesActivity.refreshItem;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private List<AddressesModal> addressesModalList;
    private int MODE;
    private int preSelectedAddressPosition;

    public AddressesAdapter(List<AddressesModal> addressesModalList, int MODE) {
        this.addressesModalList = addressesModalList;
        this.MODE = MODE;
        preSelectedAddressPosition = DataBaseQueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder holder, int position) {
        String userFullName = addressesModalList.get(position).getAddressFullname();
        String userMobileNumber = addressesModalList.get(position).getAddressMobileNumber();
        String userAlternateMobileNumber = addressesModalList.get(position).getAddressAlternateMobileNumber();
        String userFullAddress = addressesModalList.get(position).getAddress();
        String userPincode = addressesModalList.get(position).getPincode();
        Boolean selectedAddress = addressesModalList.get(position).getSelectedAddress();

        holder.setAddressData(userFullName, userMobileNumber, userAlternateMobileNumber, userFullAddress, userPincode, selectedAddress, position);

    }

    @Override
    public int getItemCount() {
        return addressesModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView address;
        private TextView pincode;
        private ImageView addressSelectedIconView;
        private LinearLayout addressesOptionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.all_addresses_name);
            address = itemView.findViewById(R.id.all_addresses_address);
            pincode = itemView.findViewById(R.id.all_addresses_pincode);
            addressSelectedIconView = itemView.findViewById(R.id.default_selected_icon_view);
            addressesOptionContainer = itemView.findViewById(R.id.addresses_option_container);
        }

        private void setAddressData(String userFullName, String userMobileNumber, String userAlternateMobileNumber, String userFullAddress, String userPincode, Boolean selectedAddress, int position) {
            if (TextUtils.isEmpty(userAlternateMobileNumber)) {
                fullName.setText(userFullName + " - " + userMobileNumber);
            } else {
                fullName.setText(userFullName + " - " + userMobileNumber + " or " + userAlternateMobileNumber);
            }
            address.setText(userFullAddress);
            pincode.setText(userPincode);

            if (MODE == SELECT_ADDRESS) {
                addressSelectedIconView.setImageResource(R.drawable.tickicon);
                if (selectedAddress) {
                    addressSelectedIconView.setVisibility(View.VISIBLE);
                    preSelectedAddressPosition = position;
                } else {
                    addressSelectedIconView.setVisibility(View.GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedAddressPosition != position) {
                            addressesModalList.get(position).setSelectedAddress(true);
                            addressesModalList.get(preSelectedAddressPosition).setSelectedAddress(false);
                            refreshItem(preSelectedAddressPosition, position);
                            preSelectedAddressPosition = position;
                            DataBaseQueries.selectedAddress = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_ADDRESS) {
                addressesOptionContainer.setVisibility(View.GONE);
                addressSelectedIconView.setImageResource(R.drawable.three_dot_icon);
                addressSelectedIconView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressesOptionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedAddressPosition, preSelectedAddressPosition);
                        preSelectedAddressPosition = position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedAddressPosition, preSelectedAddressPosition);
                        preSelectedAddressPosition = -1;
                    }
                });
            }
        }
    }
}
