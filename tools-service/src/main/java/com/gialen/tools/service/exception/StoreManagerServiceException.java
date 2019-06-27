package com.gialen.tools.service.exception;

public class StoreManagerServiceException extends RuntimeException {

    //无参构造方法
    public StoreManagerServiceException(){
        super();
    }

    //有参的构造方法
    public StoreManagerServiceException(String message){
        super(message);
    }

    // 用指定的详细信息和原因构造一个新的异常
    public StoreManagerServiceException(String message, Throwable cause){
        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public StoreManagerServiceException(Throwable cause) {
        super(cause);
    }
}
