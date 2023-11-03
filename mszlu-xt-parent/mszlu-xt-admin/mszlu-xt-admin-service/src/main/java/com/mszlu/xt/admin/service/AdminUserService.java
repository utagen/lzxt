package com.mszlu.xt.admin.service;

import com.mszlu.xt.admin.model.AdminUserModel;
import com.mszlu.xt.admin.params.AdminUserParam;
import com.mszlu.xt.common.model.CallResult;

import java.util.List;

public interface AdminUserService {
    AdminUserModel findUserByUsername(String username);

    boolean auth(String requestURI, Long userId);

    CallResult findRolePage(AdminUserParam adminUserParam);

    CallResult permissionAll();

    CallResult findRoleById(AdminUserParam adminUserParam);

    CallResult updateRole(AdminUserParam adminUserParam);

    CallResult findPermissionPage(AdminUserParam adminUserParam);

    CallResult addPermission(AdminUserParam adminUserParam);

    CallResult findPermissionById(AdminUserParam adminUserParam);

    CallResult updatePermission(AdminUserParam adminUserParam);

    CallResult findPage(AdminUserParam adminUserParam);

    CallResult roleAll();

    CallResult addUser(AdminUserParam adminUserParam);

    CallResult findUserById(AdminUserParam adminUserParam);

    CallResult update(AdminUserParam adminUserParam);

    CallResult findMenuPage(AdminUserParam adminUserParam);

    CallResult menuAll();

    CallResult saveMenu(AdminUserParam adminUserParam);

    CallResult findMenuById(AdminUserParam adminUserParam);

    CallResult updateMenu(AdminUserParam adminUserParam);

    CallResult userMenuList(AdminUserParam adminUserParam);
}
