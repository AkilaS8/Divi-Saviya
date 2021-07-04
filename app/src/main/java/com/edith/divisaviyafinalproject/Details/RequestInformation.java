package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-10.
*/

public class RequestInformation {
    String requestID;
    String requestTopic, requestCategory, requestDescription, requestRemark;
    String userName, userTelephone, userAddress, userOccupation, userState;
    Double userLat, userLng;

    public RequestInformation() {
    }

    public RequestInformation(String requestID, String requestTopic, String requestCategory, String requestDescription, String requestRemark, String userName, String userTelephone, String userAddress, String userOccupation, String userState, Double userLat, Double userLng) {
        this.requestID = requestID;
        this.requestTopic = requestTopic;
        this.requestCategory = requestCategory;
        this.requestDescription = requestDescription;
        this.requestRemark = requestRemark;
        this.userName = userName;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userState = userState;
        this.userLat = userLat;
        this.userLng = userLng;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestTopic() {
        return requestTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public String getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(String requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getRequestRemark() {
        return requestRemark;
    }

    public void setRequestRemark(String requestRemark) {
        this.requestRemark = requestRemark;
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
}
