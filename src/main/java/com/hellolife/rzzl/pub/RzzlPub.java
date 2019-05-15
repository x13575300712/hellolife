package com.hellolife.rzzl.pub;

import com.hellolife.rzzl.dao.CallRetInfo;
import com.hellolife.rzzl.dao.RetInfoDetail;
import com.hellolife.sys.pub.Calculator;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;

import java.util.ArrayList;
import java.util.List;

public class RzzlPub {
    Calculator calculator = new Calculator();
    public List<RetInfoDetail>  callEveryRet(CallRetInfo callRetInfo,int useTerm,double useAmt) throws Exception{
        int terms = callTerms(callRetInfo);
        String retway = callRetInfo.getRetway();
        List<RetInfoDetail> callList = callRetMonth(callRetInfo);//j计算还款区间
        switch(retway){
            case "1"://等额
                double retRate = callRetRate(callRetInfo.getRate()/100,callRetInfo.getRetspan());
                double everyRetbal = callRetbal(callRetInfo.getPrjamt(),terms,retRate);
                callRetDetail(callRetInfo,callList,retway,everyRetbal);
                break;
            case "2"://等本
                String patten = callRetInfo.getPrjamt()+"/"+terms;
                everyRetbal = calculator.calculate(patten);
                callRetDetail(callRetInfo,callList,retway,everyRetbal);
                break;
        }
        return callList;
    }
    /**
     * 计算每期还款额
     *
     */
    public void  callRetDetail(CallRetInfo callRetInfo, List<RetInfoDetail> callList,String way,double everyRet) throws Exception{
        if(callList.size()>0){
            double lvamt = callRetInfo.getPrjamt();
            double rate = callRetInfo.getRate()/100;
            int dayRateDays = callRetInfo.getDayratedays();
            for(RetInfoDetail retInfoDetail : callList){
                String startDate = retInfoDetail.getStartDate();
                String endDate = retInfoDetail.getEndDate();
                int days = 0;
                if(callRetInfo.getYeardays()==365){
                    days = pubfunction.getDays365(startDate,endDate);
                }else {
                    days = pubfunction.getDays360(startDate,endDate);
                }
                String patten = lvamt+"*"+days+"*"+rate+"/"+dayRateDays;
                double retInt = calculator.calculate(patten);
                switch(way){
                    case "1"://等额
                        double retAmt = everyRet-retInt;
                        retInfoDetail.setLvamt(lvamt);
                        retInfoDetail.setRetAmt(retAmt);
                        retInfoDetail.setRetInt(retInt);
                        retInfoDetail.setRetBal(everyRet);
                        retInfoDetail.setCalldays(days);
                        lvamt = lvamt-retAmt;
                        break;
                    case "2"://等本
                        double retBal = retInt+everyRet;
                        retInfoDetail.setLvamt(lvamt);
                        retInfoDetail.setRetAmt(everyRet);
                        retInfoDetail.setRetInt(retInt);
                        retInfoDetail.setRetBal(retBal);
                        retInfoDetail.setCalldays(days);
                        lvamt = lvamt - everyRet;
                        break;
                }
            }
        }
    }

    /**
     * 计算还款区间
     *
     */
    public List<RetInfoDetail>  callRetMonth(CallRetInfo callRetInfo) throws  Exception{
        String startDate = callRetInfo.getBgdt();//开始日期
        String firstDate = callRetInfo.getFistdate();// 第一次还款日期
        String endDate = callRetInfo.getEnddt();//结束日期
        int retSpan = callRetInfo.getRetspan();//还款间隔
        long prjId = callRetInfo.getId();//关系表主键

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);//id生成器

        List<RetInfoDetail> callList = new ArrayList<>();

        RetInfoDetail theFirst = new RetInfoDetail();//第一笔固定为开始日到首期结息日
        theFirst.setEndDate(firstDate);
        theFirst.setRetDate(firstDate);
        theFirst.setStartDate(startDate);
        theFirst.setPrjId(prjId);
        long id  = idWorker.nextId();
        theFirst.setId(id);

        theFirst.setStartDate(startDate);
        callList.add(theFirst);

        RetInfoDetail theLoop;
        String loopStartDate = firstDate;
        while(true){
            id  = idWorker.nextId();
            theLoop = new RetInfoDetail();
            theLoop.setStartDate(loopStartDate);
            theLoop.setId(id);
            theLoop.setPrjId(prjId);
            theFirst.setPrjId(callRetInfo.getId());
            loopStartDate = pubfunction.newDate(loopStartDate, 0, retSpan, 0);
            if(pubfunction.monthSpan(endDate,loopStartDate)>=0){
                theLoop.setEndDate(endDate);
                theLoop.setRetDate(endDate);
                callList.add(theLoop);
                break;
            }
            theLoop.setEndDate(loopStartDate);
            theLoop.setRetDate(loopStartDate);
            callList.add(theLoop);
        }
        return callList;
    }

    /**
     * 计算期数
     *
     * */
    public int callTerms(CallRetInfo callRetInfo){
        int term = 0;
        int  retspan = callRetInfo.getRetspan();
        int months = pubfunction.monthSpan(callRetInfo.getFistdate(),callRetInfo.getEnddt());
        if(months%retspan!=0){//若最后一期月数不满一期按一期算
            term = months/retspan+1;
        }else{
            term = months/retspan;
        }
        return term+1;//从第一次还息日开始计算，所以term需加一
    }
    /**
     * 计算每期租金
     *
     * */
    public double callRetbal(double prjamt, int terms, double retRate){
        double retbal = 0;
        if(1==1){//后付
            String patten = prjamt+"*"+Math.pow((1+retRate),terms)+"*"+retRate+"/("+Math.pow((1+retRate),terms)+"-"+1+")";
            retbal = calculator.calculate(patten);
        }else{//先付
            String patten = prjamt+"*"+Math.pow((1+retRate),terms-1)+"*"+retRate+"/("+Math.pow((1+retRate),terms)+"-"+1+")";
            retbal = calculator.calculate(patten);
        }
        return retbal;//
    }
    /**
     * 计算期利率
     *
     * */
    public double callRetRate(double rate, int retspan){
        String patten = rate+"/12*"+retspan;
        return calculator.calculate(patten);
    }
}
