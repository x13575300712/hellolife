package com.hellolife.sys.pub;

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


}
