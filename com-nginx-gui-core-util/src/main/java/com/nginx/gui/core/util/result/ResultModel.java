package com.nginx.gui.core.util.result;

import java.io.Serializable;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/3 15:18
 * @Description:
 */
public class ResultModel<T> implements Serializable {
    private boolean isSuccess;

    private T returnValue;

    private String errorCode;

    private String errorMessage;

    public ResultModel(T returnValue){
        this.returnValue = returnValue;
        this.isSuccess = true;
    }

    public ResultModel(String errorCode , String errorMessage){
        this.isSuccess = false;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResultModel(){
        this.isSuccess = true;
    }

    /**
     * 成功返回
     * @param returnValue
     * @param <T>
     * @return
     */
    public static <T> ResultModel<T> success(T returnValue){
        return new ResultModel<>(returnValue);
    }

    /**
     * 成功返回
     * @return
     */
    public static ResultModel success(){
        return new ResultModel();
    }

    /**
     * 失败返回
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static ResultModel failure(String errorCode , String errorMessage){
        return new ResultModel(errorCode , errorMessage);
    }

    /**
     * 无效的签名
     * @return
     */
    public static ResultModel signError(){
        return new ResultModel(ErrorConstants.INVALID_SIGN_CODE,ErrorConstants.INVALID_SIGN_MSG);
    }

    /**
     * 无效参数
     */
    public static ResultModel paramError() {

        return new ResultModel(ErrorConstants.INVALID_PARAM_CODE, ErrorConstants.INVALID_PARAM_MSG);
    }

    /**
     * 权限不足
     */
    public static ResultModel perssionError() {

        return new ResultModel(ErrorConstants.PERMISSION_DENIED_CODE, ErrorConstants.PERMISSION_DENIED_MSG);
    }


    /**
     * 定制错误信息
     */
    public static ResultModel customError(String errorCode, String errorMessage) {
        return new ResultModel(errorCode, errorMessage);
    }

    /**
     * 服务器异常
     */
    public static ResultModel serverError() {

        return new ResultModel(ErrorConstants.SERVER_ERROR_CODE, ErrorConstants.SERVER_ERROR_MSG);
    }

    /**
     * 通用异常,用于业务错误
     */
    public static ResultModel commonError(String msg) {

        return new ResultModel(ErrorConstants.COMMON_ERROR_CODE, msg);
    }

    /**
     * 数据库操作失败
     */
    public static ResultModel dataBaseError() {

        return new ResultModel(ErrorConstants.DATA_BASE_CODE, ErrorConstants.DATA_BASE_MSG);
    }

    /**
     * Token失效
     */
    public static ResultModel tokenError() {

        return new ResultModel(ErrorConstants.INVALID_TOKEN_CODE, ErrorConstants.INVALID_TOKEN_MSG);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
