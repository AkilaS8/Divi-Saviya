package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-06.
*/

public class ProductInformation {
    String productID;
    String productName, productCategory, productOrganic, productPrice, productQty, productAvailability, productRemark, productImage;
    String userName, userTelephone, userAddress, userOccupation, userState;
    Double userLat, userLng, userRate;

    public ProductInformation() {
    }

    public ProductInformation(String productID, String productName, String productCategory, String productOrganic, String productPrice, String productQty, String productAvailability, String productRemark, String productImage, String userName, String userTelephone, String userAddress, String userOccupation, String userState, Double userLat, Double userLng, Double userRate) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productOrganic = productOrganic;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productAvailability = productAvailability;
        this.productRemark = productRemark;
        this.productImage = productImage;
        this.userName = userName;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userState = userState;
        this.userLat = userLat;
        this.userLng = userLng;
        this.userRate = userRate;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductOrganic() {
        return productOrganic;
    }

    public void setProductOrganic(String productOrganic) {
        this.productOrganic = productOrganic;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(String productAvailability) {
        this.productAvailability = productAvailability;
    }

    public String getProductRemark() {
        return productRemark;
    }

    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Double getUserLat() {
        return userLat;
    }

    public void setUserLat(Double userLat) {
        this.userLat = userLat;
    }

    public Double getUserLng() {
        return userLng;
    }

    public void setUserLng(Double userLng) {
        this.userLng = userLng;
    }

    public Double getUserRate() {
        return userRate;
    }

    public void setUserRate(Double userRate) {
        this.userRate = userRate;
    }
}
