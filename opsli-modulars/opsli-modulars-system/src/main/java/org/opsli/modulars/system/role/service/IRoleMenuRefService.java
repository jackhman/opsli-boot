/**
 * Copyright 2020 OPSLI 快速开发平台 https://www.opsli.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opsli.modulars.system.role.service;

import org.opsli.modulars.system.menu.entity.SysMenu;

import java.util.List;

/**
 * @BelongsProject: opsli-boot
 * @BelongsPackage: org.opsli.modulars.test.service
 * @Author: Parker
 * @CreateTime: 2020-09-17 13:07
 * @Description: 角色-菜单 接口
 */
public interface IRoleMenuRefService {

    /**
     * 根据角色ID 获得权限（包含菜单）
     * @param roleId
     * @return
     */
    List<SysMenu> getPerms(String roleId);

    /**
     * 保存权限
     * @param roleId
     * @param permsIds
     * @return
     */
    boolean setPerms(String roleId,String[] permsIds);

}
