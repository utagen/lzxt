package com.mszlu.xt.sso.domain;

import com.mszlu.xt.model.params.InviteParam;
import com.mszlu.xt.sso.dao.data.Invite;
import com.mszlu.xt.sso.domain.repository.InviteDomainRepository;

public class InviteDomain {

    private InviteDomainRepository inviteDomainRepository;
    private InviteParam inviteParam;

    public InviteDomain(InviteDomainRepository inviteDomainRepository, InviteParam inviteParam) {
        this.inviteDomainRepository = inviteDomainRepository;
        this.inviteParam = inviteParam;
    }

    public void save(Invite invite) {
        inviteDomainRepository.save(invite);
    }
}
