package com.ecommerce.yourcart;

public class AddressesModal {
    private String addressFullname;
    private String addressMobileNumber;
    private String addressAlternateMobileNumber;
    private String address;
    private String pincode;
    private Boolean selectedAddress;

    public AddressesModal(String addressFullname, String addressMobileNumber, String addressAlternateMobileNumber, String address, String pincode, Boolean selectedAddress) {
        this.addressFullname = addressFullname;
        this.addressMobileNumber = addressMobileNumber;
        this.addressAlternateMobileNumber = addressAlternateMobileNumber;
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

    public String getAddressMobileNumber() {
        return addressMobileNumber;
    }

    public void setAddressMobileNumber(String addressMobileNumber) {
        this.addressMobileNumber = addressMobileNumber;
    }

    public String getAddressAlternateMobileNumber() {
        return addressAlternateMobileNumber;
    }

    public void setAddressAlternateMobileNumber(String addressAlternateMobileNumber) {
        this.addressAlternateMobileNumber = addressAlternateMobileNumber;
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
