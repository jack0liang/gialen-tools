package com.gialen.tools.service.exception;

public class ToolsServiceException extends RuntimeException {

    //无参构造方法
    public ToolsServiceException(){
        super();
    }

    //有参的构造方法
    public ToolsServiceException(String message){
        super(message);
    }

    // 用指定的详细信息和原因构造一个新的异常
    public ToolsServiceException(String message, Throwable cause){
        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public ToolsServiceException(Throwable cause) {
        super(cause);
    }
}
