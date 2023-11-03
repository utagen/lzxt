package com.mszlu.xt.sso.domain.repository;

import com.mszlu.xt.model.params.InviteParam;
import com.mszlu.xt.sso.dao.data.Invite;
import com.mszlu.xt.sso.dao.mongo.data.InviteMapper;
import com.mszlu.xt.sso.domain.InviteDomain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InviteDomainRepository {
    @Resource
    private InviteMapper inviteMapper;

    public InviteDomain createDomain(InviteParam inviteParam) {
        return new InviteDomain(this,inviteParam);
    }

    public void save(Invite invite) {
        inviteMapper.insert(invite);
    }
}