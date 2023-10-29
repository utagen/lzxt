package com.mszlu.xt.common.service;


import com.mszlu.xt.common.model.BusinessCodeEnum;
import com.mszlu.xt.common.model.CallResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author Jarno
 */
@Component
@Slf4j
public class ServiceTemplateImpl implements ServiceTemplate {

    @Override
    public <T> CallResult<T> execute(TemplateAction<T> action) {
        try{
            CallResult<T> callResult = action.checkParam();
            if(callResult==null){
                log.warn("execute: Null result while checkParam");
                return CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getMsg());
            }
            if(!callResult.isSuccess()){
                return callResult;
            }
            callResult = action.checkBiz();
            if(callResult==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.warn("execute: Null result while checkBiz");
                return CallResult.fail(BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if(!callResult.isSuccess()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return callResult;
            }
            long start = System.currentTimeMillis();
            CallResult<T> cr= action.doAction();
            log.info("execute datasource method run time:{}ms", System.currentTimeMillis() - start);
            if (cr == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return CallResult.fail(BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getMsg());
            }
            if (!cr.isSuccess()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return cr;
            }
            if(cr.isSuccess()){
                action.finishUp(cr);
            }
            return cr;
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("excute error", e);
            //是不是每次都需要查看 error.log才能发现 代码的问题?
            // 主动发现错误日志，肯定是在这触发的？
            // 需求：一旦出现error，发短信或者发邮件通知对应的开发人员
            // 要求：注意效率，发邮件或者发短信一般都比较慢，error日志在生产环境还是很频繁的，影响程序的性能
            return CallResult.fail();
        }
    }

    @Override
    public <T> CallResult<T> executeQuery(TemplateAction<T> action) {
        try{
            CallResult<T> callResult = action.checkParam();
            if(callResult==null){
                log.warn("executeQuery: Null result while checkParam");
                return CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getMsg());
            }
            if(!callResult.isSuccess()){
                return callResult;
            }
            callResult = action.checkBiz();
            if(callResult==null){
                log.warn("executeQuery: Null result while checkBiz");
                return CallResult.fail(BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if(!callResult.isSuccess()){
                return callResult;
            }
            long start = System.currentTimeMillis();
            CallResult<T> cr= action.doAction();
            log.info("executeQuery datasource method run time:{}ms", System.currentTimeMillis() - start);
            if (cr == null){
                return CallResult.fail(BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getMsg());
            }
            if (!cr.isSuccess()){
                return cr;
            }
            if(cr.isSuccess()){
                action.finishUp(cr);
            }
            return cr;
        }catch(Exception e){
            e.printStackTrace();
            log.error("executeQuery error", e);
            return CallResult.fail();
        }
    }
}