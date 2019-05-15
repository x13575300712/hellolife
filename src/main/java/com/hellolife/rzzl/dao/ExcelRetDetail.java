package com.hellolife.rzzl.dao;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ExcelRetDetail {
    @Excel(name = "期数" ,orderNum = "1")
    @NotNull
    private Integer retSn;
    @Excel(name = "结息日" ,format = "yyyy-MM-dd",orderNum = "1")
    @NotNull
    private Date endDate;
    @Excel(name = "还款日" ,format = "yyyy-MM-dd",orderNum = "2")
    @NotNull
    private Date retDate;
    @Excel(name = "租金" ,orderNum = "3")
    @NotNull
    private Double retBal;
    @Excel(name = "租赁利息" ,orderNum = "4")
    @NotNull
    private Double retInt;
    @Excel(name = "本金" ,orderNum = "5")
    @NotNull
    private Double retAmt;
    @Excel(name = "剩余本金" ,orderNum = "6")
    @NotNull
    private Double lvamt;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRetDate() {
        return retDate;
    }

    public void setRetDate(Date retDate) {
        this.retDate = retDate;
    }

    public Double getRetBal() {
        return retBal;
    }

    public void setRetBal(Double retBal) {
        this.retBal = retBal;
    }

    public Double getRetInt() {
        return retInt;
    }

    public void setRetInt(Double retInt) {
        this.retInt = retInt;
    }

    public Double getRetAmt() {
        return retAmt;
    }

    public void setRetAmt(Double retAmt) {
        this.retAmt = retAmt;
    }

    public Double getLvamt() {
        return lvamt;
    }

    public void setLvamt(Double lvamt) {
        this.lvamt = lvamt;
    }
    public Integer getRetSn() {
        return retSn;
    }

    public void setRetSn(Integer retSn) {
        this.retSn = retSn;
    }

    @Override
    public String toString() {
        return "ExcelRetDetail{" +
                "retSn=" + retSn +
                ", endDate='" + endDate + '\'' +
                ", retDate='" + retDate + '\'' +
                ", retBal='" + retBal + '\'' +
                ", retInt='" + retInt + '\'' +
                ", retAmt='" + retAmt + '\'' +
                ", lvamt='" + lvamt + '\'' +
                '}';
    }
}
