package com.hellolife.rzzl.dao;

public class RetInfoDetail {
    long id;

    String endDate;

    String retDate;

    String startDate;

    double retBal;

    double retInt;

    double retAmt;

    int calldays;

    double lvamt;

    long prjId;

    public long getPrjId() {
        return prjId;
    }

    public void setPrjId(long prjId) {
        this.prjId = prjId;
    }

    public double getLvamt() {
        return lvamt;
    }

    public void setLvamt(double lvamt) {
        this.lvamt = lvamt;
    }

    public int getCalldays() {
        return calldays;
    }

    public void setCalldays(int calldays) {
        this.calldays = calldays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getRetBal() {
        return retBal;
    }

    public void setRetBal(double retBal) {
        this.retBal = retBal;
    }

    public double getRetInt() {
        return retInt;
    }

    public void setRetInt(double retInt) {
        this.retInt = retInt;
    }

    public double getRetAmt() {
        return retAmt;
    }

    public void setRetAmt(double retAmt) {
        this.retAmt = retAmt;
    }
}
