package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-03.
*/

public class ProfileInformation {

    private String userName;
    private String userTelephone;
    private String userAddress;
    private String userOccupation;
    private String userPassword;
    private String userImage;
    private String userState;
    private Double userLat;
    private Double userLng;
    private Double userRate;

    public ProfileInformation(String userName, String userTelephone, String userAddress, String userOccupation, String userPassword, String userImage, String userState, Double userLat, Double userLng, Double userRate) {
        this.userName = userName;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userPassword = userPassword;
        this.userImage = userImage;
        this.userState = userState;
        this.userLat = userLat;
        this.userLng = userLng;
        this.userRate = userRate;
    }

    public ProfileInformation() {
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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
