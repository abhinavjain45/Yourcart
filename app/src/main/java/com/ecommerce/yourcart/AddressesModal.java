package com.ecommerce.yourcart;

public class AddressesModal {
    private String addressFullname;
    private String address;
    private String pincode;
    private Boolean selectedAddress;

    public AddressesModal(String addressFullname, String address, String pincode, Boolean selectedAddress) {
        this.addressFullname = addressFullname;
        this.address = address;
        this.pincode = pincode;
        this.selectedAddress = selectedAddress;
    }

    public String getAddressFullname() {
        return addressFullname;
    }

    public void setAddressFullname(String addressFullname) {
        this.addressFullname = addressFullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Boolean getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Boolean selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
}
