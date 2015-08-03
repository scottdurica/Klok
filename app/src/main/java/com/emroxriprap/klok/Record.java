package com.emroxriprap.klok;

/**
 * Created by Scott Durica on 8/1/2015.
 */
public class Record {

    private String jobName;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String state;
    private int hours;
    private int date;

    public Record(){

    }

    public Record(String jobName, String addressOne, String addressTwo,
                  String city, String state, int hours, int date) {
        this.jobName = jobName;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.state = state;
        this.hours = hours;
        this.date = date;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
