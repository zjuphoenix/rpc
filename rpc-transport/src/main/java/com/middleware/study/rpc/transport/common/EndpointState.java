package com.middleware.study.rpc.transport.common;

/**
 * @author wuhaitao
 * @date 2016/5/26 16:34
 */
public enum EndpointState {
    UNINIT,INIT,ALIVE,UNALIVE,CLOSE;

    public boolean isInitState(){
        return this == INIT;
    }

    public boolean isUnInitState(){
        return this == UNINIT;
    }

    public boolean isAliveState(){
        return this == ALIVE;
    }

    public boolean isUnAliveState(){
        return this == UNALIVE;
    }

    public boolean isCloseState(){
        return this == CLOSE;
    }
}
