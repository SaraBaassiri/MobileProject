package com.example.homsi.psf;

import java.text.NumberFormat;

public class PSpot {

    public double price;
    public int counter;
    public String status;
    public String address;
    public String startDate;
    public String startTime;
    public String endTime;
    public String endDate;
    public Boolean availability = true;
    public String owner;
    public String reserve;
    public String zip;
    public String state;
    public String listingHash;
    public String ratings;


    // creates empty parking spot
    public PSpot(){
        price = 0;
        address = null;
        startDate = null;
        endDate = null;
        startTime = null;
        endTime = null;
        owner = "";
        reserve = null;
        zip = null;
        state = null;
        counter = 0;
        listingHash = "";
    }

    // creates parking spot based on the parameters
    public PSpot(double price, String address,String state, String zip,  String startDate, String endDate, String startTime,
                 String endTime, Boolean availability,String owner, String reserve, int counter, String listingHash) {
        this.price = price;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.startDate = startDate;
        this.endDate=endDate;
        this.startTime = startTime;
        this.endTime= endTime;
        this.availability = availability;
        this.owner = owner;
        this.reserve = reserve;
        this.counter = counter;
        this.listingHash = listingHash;
    }

    public double getPrice()
    {
        return price;
    }
    public String getAddress()
    {
        return address;
    }
    public String getstartDate()
    {
        return startDate;
    }
    public String getendDate()
    {
        return endDate;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public String getZip()
    {
        return zip;
    }
    public String getOwner() { return owner; }
    public String getListingHash() { return listingHash; }
    public String getRatings() { return ratings; }

    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setAddress(String address) { this.address = address; }
    public void setStartTime(String time) { this.startTime = time; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setAvailability(boolean availability){this.availability = availability;}
    public void setOwner(String owner){this.owner=owner;}

    public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return "Parking Spot"+ "\n" + "Address: " +  getAddress() +"\n" + "Zip: " + getZip()  + "\n" + "Cost: " + formatter.format(getPrice()) + "\n" + "Start Date: " + getstartDate() + "\n" + "End Date: " + getendDate() + "\n"+ "Starting at: " + getStartTime() +"\n" + "Ending at: " + getEndTime()+ "\n" + "Available: "+ availability +"\n" + "Number of times booked: " + counter;
    }
    public String ownerToString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return "Parking Spot" + "\n"+"Owner:"+owner +"\n"+ " " +"Price: "+  formatter.format(getPrice())
                + "\n" + "Address: " +  getAddress() +"\n" + "Zip: " + getZip() + "\n" + "Start Time: "
                + startTime +"\n" + "End Time: " + endTime + "\n" + "Start Date: " + startDate + "\n"
                + "End Date: " + endDate +"\n"+ "Available: "+ availability + "\n"
                + "\n" + "Listing Hash= " + listingHash;
    }
    public String rentToString() {
        return "Parking Spot" + "\n"+"Owner:"+owner +"\n"+"Renter: " + reserve+ "\n"+ "$" + " " + getPrice() + "\n" + "Address: " +  getAddress() + "\n" + "Start Time: "+ startTime +"\n" + "End Time: " + endTime + "\n" + "Start Date: " + startDate + "\n" + "End Date: " + endDate +"\n"+ "Available: "+ availability;
    }

}