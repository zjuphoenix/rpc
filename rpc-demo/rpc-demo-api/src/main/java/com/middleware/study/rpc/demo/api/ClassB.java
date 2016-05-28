package com.middleware.study.rpc.demo.api;

/**
 * @author wuhaitao
 * @date 2016/5/28 0:00
 */
public class ClassB {
    private int code;
    private ClassA classA;

    public ClassB() {
    }

    public ClassB(int code, ClassA classA) {
        this.code = code;
        this.classA = classA;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ClassA getClassA() {
        return classA;
    }

    public void setClassA(ClassA classA) {
        this.classA = classA;
    }

    @Override
    public String toString() {
        return "ClassB{" +
                "code=" + code +
                ", classA=" + classA.toString() +
                '}';
    }
}
