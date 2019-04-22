package com.hellolife.sys.pub;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class pubfunction {
    public static boolean isEmptyStr(String str) {
        if(str==null) {
            return true;
        }
        if(str.trim().equals("")) {
            return true;
        }
        return false;
    }
    public static int monthSpan(String stratDate,String endDate) {
        int yearS = Integer.parseInt(stratDate.substring(0,4));
        int yearE = Integer.parseInt(endDate.substring(0,4));
        int monthS = Integer.parseInt(stratDate.substring(5,7));
        int monthE = Integer.parseInt(endDate.substring(5,7));
        return (yearE-yearS)*12+monthE-monthS;
    }
    /*
计算给定基准日期的加year年，month月,day天的日期
*/
    public static String newDate(String bdate,int year,int month,int day) throws Exception{
        if(!checkDate(bdate))
            throw new Exception("["+bdate+"] is not correct date format,please use 'YYYY-MM-DD' format");

        String sub_str=null;
        sub_str=bdate.substring(0,4);
        int byear=Integer.parseInt(sub_str);

        sub_str=bdate.substring(5,7);
        int bmonth=Integer.parseInt(sub_str);

        sub_str=bdate.substring(8);
        int bday=Integer.parseInt(sub_str);



        Calendar cal=Calendar.getInstance();

        cal.set(byear,bmonth-1,bday);


        cal.add(Calendar.YEAR,year);
        cal.add(Calendar.MONTH,month);
        cal.add(Calendar.DATE,day);




        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH)+1;
        day=cal.get(Calendar.DATE);



        String yearStr=""+year;
        String monthStr=""+month;
        String dateStr=""+day;


        if(month<10)
            monthStr="0"+month;
        if(day<10)
            dateStr="0"+day;
        return yearStr+"-"+monthStr+"-"+dateStr;
    }

    /**
     * 按360得到给定两个日期之间的天数
     *
     *@param bDay String  开始的日期，格式：YYYY-MM-DD
     *@param eDay String  结束的日期，格式：YYYY-MM-DD
     *
     *@return int  天数
     */
    public static int getDays360(String bDayStr,String eDayStr) throws Exception
    {
        String maxday=getMaxTime(bDayStr,eDayStr);
        int flag=1;
        if(maxday.equals(bDayStr)){
            bDayStr=eDayStr;
            eDayStr=maxday;
            flag=-1;
        }
        int rDay = 0;
        int bYear = Integer.parseInt(bDayStr.substring(0,4));
        int bMonth = Integer.parseInt(bDayStr.substring(5,7));
        int bDay = Integer.parseInt(bDayStr.substring(8));

        int eYear = Integer.parseInt(eDayStr.substring(0,4));
        int eMonth = Integer.parseInt(eDayStr.substring(5,7));
        int eDay = Integer.parseInt(eDayStr.substring(8));

        int rmonth=(eYear-bYear)*12+(eMonth-bMonth);
        rDay=eDay-bDay;

        int days=rmonth*30+rDay;

        if(flag<0){
            if(days<0) return days;
            else return days*flag;
        }else{
            if(days<0) return days*flag;
            else return days;
        }

    }
    /**
     * 取得两个时间段的时间间隔
     * return t2 与t1的间隔天数
     * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getDays365(String t1,String t2) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if(c1.after(c2)){
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<betweenYears;i++){
            int tmp=countDays(c1.get(Calendar.YEAR));
            betweenDays+=countDays(c1.get(Calendar.YEAR));
            c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1));
        }
        return betweenDays;
    }
    public static int countDays(int year){
        int n=0;
        for (int i = 1; i <= 12; i++) {
            n += countDays(i,year);
        }
        return n;
    }
    public static int countDays(int month, int year){
        int count = -1;
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                count = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                count = 30;
                break;
            case 2:
                if(year % 4 == 0)
                    count = 29;
                else
                    count = 28;
                if((year % 100 ==0) & (year % 400 != 0))
                    count = 28;
        }
        return count;
    }

    /*返回两个日期串中较大的一个*/
    public static String getMaxTime(String date1,String date2){
        try{
            String str;
            int posi1,posi2;
            String y1,y2,m1,m2,d1,d2;
            if(!checkDate(date1)){
                System.out.println("incorrect time format:["+date1+"]");
                return null;
            }//end if
            if(!checkDate(date2)){
                System.out.println("incorrect time format:["+date2+"]");
                return null;
            }//end if
            str=date1;
            posi1=date1.indexOf("-");
            str=date1.substring(0,posi1)+"/"+date1.substring(posi1+1);
            posi2=str.indexOf("-");

            y1=date1.substring(0,posi1);
            if(y1.length()==2)
                y1="20"+y1;
            m1=date1.substring(posi1+1,posi2);
            d1=date1.substring(posi2+1);

            str=date2;
            posi1=date2.indexOf("-");
            str=date2.substring(0,posi1)+"/"+date2.substring(posi1+1);
            posi2=str.indexOf("-");

            y2=date2.substring(0,posi1);
            if(y2.length()==2)
                y2="20"+y2;
            m2=date2.substring(posi1+1,posi2);
            d2=date2.substring(posi2+1);

            if(Integer.parseInt(y1)>Integer.parseInt(y2))
                return date1;
            if(Integer.parseInt(y1)<Integer.parseInt(y2))
                return date2;
            if(Integer.parseInt(m1)>Integer.parseInt(m2))
                return date1;
            if(Integer.parseInt(m1)<Integer.parseInt(m2))
                return date2;
            if(Integer.parseInt(d1)>Integer.parseInt(d2))
                return date1;
            if(Integer.parseInt(d1)<Integer.parseInt(d2))
                return date2;
            return date1;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //end get_max_time

    //check if a String is a Date String (yyyy-mm-dd)||(yy-mm-dd)
    public static boolean checkDate(String str){
        int len=str.length();
        int year,month,day;
        char ch='-';
        String sub_str="";
        sub_str=sub_str;
        int flag=-1;//用于判断是否是润年,如果是润年，flag=1;
        try{
            switch(len){
                case 10://yyyy-mm-dd
                    sub_str=str.substring(0,4);
                    year=Integer.parseInt(sub_str);

                    sub_str=str.substring(5,7);
                    month=Integer.parseInt(sub_str);

                    sub_str=str.substring(8);
                    day=Integer.parseInt(sub_str);
                    if(!(str.charAt(4)==ch))
                        return false;
                    if(!(str.charAt(7)==ch))
                        return false;
                    break;
                case 9://yyyy-m-dd or yyyy-mm-d
                    if((str.charAt(4)==ch)&&(str.charAt(6)==ch)){
                        sub_str=str.substring(0,4);
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(5,6);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(7);
                        day=Integer.parseInt(sub_str);
                    }
                    else if((str.charAt(4)==ch)&&(str.charAt(7)==ch)){
                        sub_str=str.substring(0,4);
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(5,7);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(8);
                        day=Integer.parseInt(sub_str);
                    }
                    else return false;
                    break;
                case 8://yy-mm-dd or yyyy-m-d
                    if((str.charAt(2)==ch)&&(str.charAt(5)==ch)){
                        sub_str=str.substring(0,2);
                        sub_str="20"+sub_str;
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(3,5);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(6);
                        day=Integer.parseInt(sub_str);
                    }
                    else if((str.charAt(4)==ch)&&(str.charAt(6)==ch)){
                        sub_str=str.substring(0,4);
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(5,6);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(7);
                        day=Integer.parseInt(sub_str);
                    }
                    else return false;
                    break;
                case 7://yy-mm-d or yy-m-dd
                    if((str.charAt(2)==ch)&&(str.charAt(5)==ch)){
                        sub_str=str.substring(0,2);
                        sub_str="20"+sub_str;
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(3,5);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(6);
                        day=Integer.parseInt(sub_str);
                    }
                    else if((str.charAt(2)==ch)&&(str.charAt(4)==ch)){
                        sub_str=str.substring(0,2);
                        sub_str="20"+sub_str;
                        year=Integer.parseInt(sub_str);

                        sub_str=str.substring(3,4);
                        month=Integer.parseInt(sub_str);

                        sub_str=str.substring(5);
                        day=Integer.parseInt(sub_str);
                    }
                    else return false;
                    break;
                case 6://yy-m-d
                    sub_str=str.substring(0,2);
                    sub_str="20"+sub_str;
                    year=Integer.parseInt(sub_str);

                    sub_str=str.substring(3,4);
                    month=Integer.parseInt(sub_str);

                    sub_str=str.substring(5);
                    day=Integer.parseInt(sub_str);
                    if(!(str.charAt(2)==ch))
                        return false;
                    if(!(str.charAt(4)==ch))
                        return false;
                    break;
                default:
                    return false;
            }//end switch

            if(year<0||year>5000)//check year
                return false;
            if((year%4)==0)
                flag=1;//如果是润年
            if(month<1||month>12)//check month
                return false;
            switch(month){//check day
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(day<1||day>31)
                        return false;
                    break;
                case 2:
                    if(flag==-1)
                        if(day<1||day>28)
                            return false;
                    if(flag==1)//如果是润年
                        if(day<1||day>29)
                            return false;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(day<1||day>30)
                        return false;
                    break;
                default:
                    return false;
            }//end switch
        }//end try
        catch(NumberFormatException e){
            e.printStackTrace();
            return false;
        }//end catch
        return true;
    }//end checkDate

}
