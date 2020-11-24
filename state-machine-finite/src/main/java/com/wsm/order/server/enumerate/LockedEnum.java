package com.wsm.order.server.enumerate;

public enum LockedEnum {

    LOCKED(1,"锁定"),
    UNLOCK(1,"未锁定"),

    ;


    LockedEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;
    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
