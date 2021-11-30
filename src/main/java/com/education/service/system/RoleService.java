package com.education.service.system;

import com.education.common.Result;
import com.education.dto.system.DictionaryDto;
import com.education.dto.system.RoleDto;
import com.education.entity.system.RoleEntity;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-24
 */
public interface RoleService {
    Result queryRoleAllPage(RoleDto roleDto);

    void deleteRole(RoleDto roleDto);

    void editRole(RoleDto roleDto);

    void addRole(RoleDto roleDto);

    Result queryRoleAll(RoleDto roleDto);
}
