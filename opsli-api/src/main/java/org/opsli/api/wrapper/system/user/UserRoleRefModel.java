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
package org.opsli.api.wrapper.system.user;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;

import java.io.Serializable;

/**
 * 用户 - 角色表
 *
 * @author Parker
 * @date 2020-09-16 17:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleRefModel implements Serializable {

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(50)
    private String userId;

    /** 角色数组 */
    @ApiModelProperty(value = "权限数组")
    private String[] roleIds;

    /** 默认角色ID */
    @ApiModelProperty(value = "默认角色")
    @Validator({ValidatorType.IS_NOT_NULL})
    private String defRoleId;

}
