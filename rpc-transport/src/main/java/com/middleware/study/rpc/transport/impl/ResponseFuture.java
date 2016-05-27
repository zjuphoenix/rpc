package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.api.Future;
import com.middleware.study.rpc.transport.api.FutureListener;
import com.middleware.study.rpc.transport.api.Request;
import com.middleware.study.rpc.transport.api.Response;
import com.middleware.study.rpc.transport.common.FutureState;
import com.middleware.study.rpc.transport.exception.ServiceException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuhaitao
 * @date 2016/5/26 12:38
 */
public class ResponseFuture implements Response, Future {

    private volatile FutureState state = FutureState.DOING;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Object result = null;
    private Exception exception = null;
    private Request request;

    public ResponseFuture(Request request) {
        this.request = request;
    }

    @Override
    public boolean isDone() {
        return state.isSuccessState() || state.isSuccessState();
    }

    @Override
    public boolean isSuccess() {
        return state.isSuccessState();
    }

    @Override
    public boolean isCancelled() {
        return state.isCancelledState();
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public void onSuccess(Response response) {
        lock.lock();
        try {
            result = response.getValue();
            state = FutureState.SUCCESS;
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void onFailure(Response response) {
        lock.lock();
        try {
            exception = response.getException();
            state = FutureState.FAILURE;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object getValue() {
        if (state == FutureState.SUCCESS){
            return result;
        }
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        if (exception != null){
            throw new ServiceException(exception);
        }
        return result;
    }

    private Object getValueOrException(){
        if (exception != null){
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public void addListener(FutureListener futureListener) {

    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public long getRequestId() {
        return request.getRequestId();
    }
}
