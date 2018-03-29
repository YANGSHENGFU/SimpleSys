package com.sys.bend;

/**
 * Created by LY on 2018/3/27.
 */

public class RequestJson {

    private int reqType = 0 ;
    private Perception perception ;
    private UserInfo userInfo = new UserInfo();

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public Perception getmPerception() {
        return perception;
    }

    public void setmPerception(Perception mPerception) {
        this.perception = mPerception;
    }
}
