package com.mszlu.xt.admin.service.impl;

import com.mszlu.xt.admin.domain.AdminUserDomain;
import com.mszlu.xt.admin.domain.repository.AdminUserDomainRepository;
import com.mszlu.xt.admin.model.AdminUserModel;
import com.mszlu.xt.admin.params.AdminUserParam;
import com.mszlu.xt.admin.service.AdminUserService;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.common.service.AbstractTemplateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserServiceImpl extends AbstractService implements AdminUserService {
    @Autowired
    private AdminUserDomainRepository adminUserDomainRepository;

    @Override
    public AdminUserModel findUserByUsername(String username) {
        AdminUserParam adminUserParam = new AdminUserParam();
        adminUserParam.setUsername(username);
        AdminUserDomain adminUserDomain = adminUserDomainRepository.createDomain(adminUserParam);
        return adminUserDomain.findUserByUsername();
    }

    @Override
    public boolean auth(String requestURI, Long userId) {
        AdminUserDomain adminUserDomain = adminUserDomainRepository.createDomain(new AdminUserParam());
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Boolean>() {
            @Override
            public CallResult<Boolean> doAction() {
                return adminUserDomain.auth(requestURI, userId);
            }
        }).getResult();
    }

    @Override
    public CallResult findRolePage(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findRolePage();
            }
        });
    }

    @Override
    public CallResult permissionAll() {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(null);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.permissionAll();
            }
        });
    }

    @Override
    public CallResult findRoleById(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findRoleById();
            }
        });
    }

    @Override
    @Transactional
    public CallResult updateRole(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.updateRole();
            }
        });
    }

    @Override
    public CallResult findPermissionPage(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findPermissionPage();
            }
        });
    }

    @Override
    public CallResult addPermission(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.addPermission();
            }
        });
    }

    @Override
    public CallResult findPermissionById(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findPermissionById();
            }
        });
    }

    @Override
    public CallResult updatePermission(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.updatePermission();
            }
        });
    }

    @Override
    public CallResult findPage(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findPage();
            }
        });
    }

    @Override
    public CallResult roleAll() {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(null);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.roleAll();
            }
        });
    }

    @Override
    @Transactional
    public CallResult addUser(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.addUser();
            }
        });
    }

    @Override
    public CallResult findUserById(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findUserById();
            }
        });
    }

    @Override
    @Transactional
    public CallResult update(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.update();
            }
        });
    }

    @Override
    public CallResult findMenuPage(AdminUserParam adminUserParam) {

        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findMenuPage();
            }
        });
    }

    @Override
    public CallResult menuAll() {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(null);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.menuAll();
            }
        });
    }

    @Override
    public CallResult saveMenu(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.saveMenu();
            }
        });
    }

    @Override
    public CallResult findMenuById(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.findMenuById();
            }
        });
    }

    @Override
    public CallResult updateMenu(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.updateMenu();
            }
        });
    }

    @Override
    public CallResult userMenuList(AdminUserParam adminUserParam) {
        AdminUserDomain adminUserDomain = this.adminUserDomainRepository.createDomain(adminUserParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return adminUserDomain.userMenuList();
            }
        });
    }
}
