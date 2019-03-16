
package com.hellolife.sys.enums;

public enum ExceptionMsg {
	SUCCESS("000000", "操作成功"),
	FAILED("999999","操作失败"),
	ACCOUNTNOTHAVE("000001","账号不存在"),
	PASSWORDERROR("000002","密码不正确"),
	VERIFICATIONERROR("000003","验证码错误")
    ;
   private ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;
    
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    
}

