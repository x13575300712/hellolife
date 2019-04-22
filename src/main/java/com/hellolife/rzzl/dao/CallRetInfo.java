package com.hellolife.rzzl.dao;

import java.io.Serializable;
import java.util.Date;

public class CallRetInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Double prjamt;

    private Double rate;

    private String bgdt;

    private String enddt;

    private String fistdate;

    private Integer retspan;

    private Integer yeardays;

    private Integer dayratedays;

    private String retway;

    private String prjname;

    public String getPrjname() {
        return prjname;
    }

    public void setPrjname(String prjname) {
        this.prjname = prjname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrjamt() {
        return prjamt;
    }

    public void setPrjamt(Double prjamt) {
        this.prjamt = prjamt;
    }

    public String getBgdt() {
        return bgdt;
    }

    public void setBgdt(String bgdt) {
        this.bgdt = bgdt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }

    public String getFistdate() {
        return fistdate;
    }

    public void setFistdate(String fistdate) {
        this.fistdate = fistdate;
    }

    public Integer getRetspan() {
        return retspan;
    }

    public void setRetspan(Integer retspan) {
        this.retspan = retspan;
    }

    public Integer getYeardays() {
        return yeardays;
    }

    public void setYeardays(Integer yeardays) {
        this.yeardays = yeardays;
    }

    public Integer getDayratedays() {
        return dayratedays;
    }

    public void setDayratedays(Integer dayratedays) {
        this.dayratedays = dayratedays;
    }

    public String getRetway() {
        return retway;
    }

    public void setRetway(String retway) {
        this.retway = retway;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
    @Override
    public String toString() {
        return "CallRetInfo{" +
                "id=" + id +
                ", prjamt=" + prjamt +
                ", rate=" + rate +
                ", bgdt='" + bgdt + '\'' +
                ", enddt='" + enddt + '\'' +
                ", fistdate='" + fistdate + '\'' +
                ", retspan=" + retspan +
                ", yeardays=" + yeardays +
                ", dayratedays=" + dayratedays +
                ", retway='" + retway + '\'' +
                ", prjname='" + prjname + '\'' +
                '}';
    }
}
