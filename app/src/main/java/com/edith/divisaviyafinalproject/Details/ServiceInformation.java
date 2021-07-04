package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-07.
*/

public class ServiceInformation {

    public String serviceID;
    public String serviceTopic, serviceCategory, serviceDetails, servicePrice, serviceImage, serviceRemarks, serviceKeywords;
    public String userName, userTelephone, userAddress, userOccupation, userState;
    public Double userLat, userLng, userRate;

    public ServiceInformation() {
    }

    public ServiceInformation(String serviceID, String serviceTopic, String serviceCategory, String serviceDetails, String servicePrice, String serviceImage, String serviceRemarks, String serviceKeywords, String userName, String userTelephone, String userAddress, String userOccupation, String userState, Double userLat, Double userLng, Double userRate) {
        this.serviceID = serviceID;
        this.serviceTopic = serviceTopic;
        this.serviceCategory = serviceCategory;
        this.serviceDetails = serviceDetails;
        this.servicePrice = servicePrice;
        this.serviceImage = serviceImage;
        this.serviceRemarks = serviceRemarks;
        this.serviceKeywords = serviceKeywords;
        this.userName = userName;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userState = userState;
        this.userLat = userLat;
        this.userLng = userLng;
        this.userRate = userRate;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceTopic() {
        return serviceTopic;
    }

    public void setServiceTopic(String serviceTopic) {
        this.serviceTopic = serviceTopic;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceRemarks() {
        return serviceRemarks;
    }

    public void setServiceRemarks(String serviceRemarks) {
        this.serviceRemarks = serviceRemarks;
    }

    public String getServiceKeywords() {
        return serviceKeywords;
    }

    public void setServiceKeywords(String serviceKeywords) {
        this.serviceKeywords = serviceKeywords;
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
