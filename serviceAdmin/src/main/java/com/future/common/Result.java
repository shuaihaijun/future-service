package com.future.common;

import java.io.Serializable;

/**
 * 业务信息接受对象
 * Created by CS on 2016/5/16.
 */
public class Result implements Serializable{
	private static final long serialVersionUID = 4085034847079194271L;
	/*
     * 表示接口的调用成功或失败
     * 1=成功，非1表示失败
     */
    private Long code;
    private String desc;
    private Object o;

    public Result(){
    }
    public Result(Long code){
        this.code=code;
        if(code==1){
            this.desc="调用成功";
        }else{
            this.desc="调用失败";
        }
        this.o=null;
    }
    public Result(Long code, String desc){
        this.code=code;
        this.desc=desc;
        this.o=null;
    }
    public Result(Long code, String desc, Object t){
        this.code=code;
        this.desc=desc;
        this.o=t;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getT() {
        return o;
    }

    public void setT(Object t) {
        this.o = t;
    }
}
