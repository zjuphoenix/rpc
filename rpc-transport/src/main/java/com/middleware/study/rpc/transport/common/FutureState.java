package com.middleware.study.rpc.transport.common;

/**
 * @author wuhaitao
 * @date 2016/5/26 18:13
 */
public enum FutureState {
    DOING,SUCCESS,FAILURE,CANCELLED;
    public boolean isCancelledState() {
        return this == CANCELLED;
    }

    public boolean isSuccessState() {
        return this == SUCCESS;
    }

    public boolean isFailureState(){
        return this == FAILURE;
    }
    public boolean isDoingState() {
        return this == DOING;
    }
}
