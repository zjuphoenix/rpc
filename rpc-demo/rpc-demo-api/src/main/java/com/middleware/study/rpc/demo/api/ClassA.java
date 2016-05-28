package com.middleware.study.rpc.demo.api;

import java.util.List;
import java.util.Map;

/**
 * @author wuhaitao
 * @date 2016/5/27 23:58
 */
public class ClassA {
    private int code;
    private String msg;
    private Map<Integer,String> map;
    private List<String> list;

    public ClassA() {
    }

    public ClassA(int code, String msg, Map<Integer, String> map, List<String> list) {
        this.code = code;
        this.msg = msg;
        this.map = map;
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ClassA{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", map=" + map +
                ", list=" + list +
                '}';
    }
}
