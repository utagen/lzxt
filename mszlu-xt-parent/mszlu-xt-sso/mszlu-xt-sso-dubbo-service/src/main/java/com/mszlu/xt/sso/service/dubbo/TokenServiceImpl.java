package com.mszlu.xt.sso.service.dubbo;

import com.mszlu.xt.sso.domain.TokenDomain;
import com.mszlu.xt.sso.domain.repository.TokenDomainRepository;
import com.mszlu.xt.sso.service.TokenService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//将此服务注册到nacos注册中心上去
//暴露的服务 TokenService.checkToken() 服务的版本号1.0.0
@DubboService(version = "1.0.0", interfaceClass = TokenService.class)
public class TokenServiceImpl extends AbstractService implements TokenService {

    @Autowired
    private TokenDomainRepository tokenDomainRepository;

    @Override
    public Long checkToken(String token) {
        TokenDomain tokenDomain = tokenDomainRepository.createDomain();
        return tokenDomain.checkToken(token);
    }
}
