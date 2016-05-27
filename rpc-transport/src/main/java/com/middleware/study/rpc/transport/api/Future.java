package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/25 22:55
 */
public interface Future {
    /**
     * 是否完成：success or throw exception
     * @return
     */
    boolean isDone();

    /**
     * 是否执行成功
     * @return
     */
    boolean isSuccess();

    /**
     * 任务是否取消
     * @return
     */
    boolean isCancelled();

    /**
     * 取消任务
     * @return
     */
    boolean cancel();

    /**
     * 得到正常response后回掉方法
     * @param response
     */
    void onSuccess(Response response);

    /**
     * 得到异常response后回掉方法
     * @param response
     */
    void onFailure(Response response);

    /**
     * 如果任务执行成功，返回结果
     * 超时、任务被取消时抛出异常
     * @return
     */
    Object getValue();

    /**
     * 如果任务执行完成并且抛出异常，返回异常
     * @return
     */
    Exception getException();

    /**
     * 添加listener，当任务success,failure,timeout,cancel时listener方法被调用
     * @param futureListener
     */
    void addListener(FutureListener futureListener);
}
