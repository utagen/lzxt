package com.mszlu.xt.sso.service.impl;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.common.service.AbstractTemplateAction;
import com.mszlu.xt.model.params.LoginParam;
import com.mszlu.xt.sso.domain.LoginDomain;
import com.mszlu.xt.sso.domain.repository.LoginDomainRepository;
import com.mszlu.xt.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Autowired
    private LoginDomainRepository loginDomainRepository;

    @Override
    public CallResult getQRCodeUrl() {
        LoginDomain loginDomain = loginDomainRepository.createDomain(new LoginParam());
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return loginDomain.buildQrConnectUrl();
            }
        });
    }

    @Override
    @Transactional
    public CallResult wxLoginCallBack(LoginParam loginParam) {
        LoginDomain loginDomain = loginDomainRepository.createDomain(loginParam);
        //带有事务的执行
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> checkBiz() {
                //检查业务参数
                return loginDomain.checkWxLoginCallBackBiz();
            }

            @Override
            public CallResult<Object> doAction() {
                //写业务逻辑的
                return loginDomain.wxLoginCallBack();
            }

            @Override
            public void finishUp(CallResult<Object> callResult) {
                loginDomain.wxLoginCallBackFinishUp(callResult);
            }
        });
    }

    @Override
    public String authorize() {
        LoginDomain loginDomain = loginDomainRepository.createDomain(new LoginParam());
        return loginDomain.buildGzhUrl();
    }

    @Override
    @Transactional
    public CallResult wxGzhLoginCallBack(LoginParam loginParam) {
        LoginDomain loginDomain = loginDomainRepository.createDomain(loginParam);
        //带有事务的执行操作
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>(){

            @Override
            public CallResult<Object> checkBiz() {
                //检查业务参数
                return loginDomain.checkWxLoginCallBackBiz();
            }

            @Override
            public CallResult<Object> doAction() {
                //写业务逻辑的
                return loginDomain.wxGzhLoginCallBack();
            }
        });
    }
}
