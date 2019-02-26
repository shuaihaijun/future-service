package com.future.constant;

/**
 * 小程序请求返回code
 */
public enum XcxErrCode {
			SUCCESS("0", "请求成功"),BUSY("-1", "系统繁忙，此时请开发者稍候再试"),
			INVALID("40029","code 无效"),TOO_OFTEN("45011","频率限制，每个用户每分钟100次");

	private final String code;
	private final String msg;

	XcxErrCode(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}
}