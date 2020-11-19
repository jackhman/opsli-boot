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
package org.opsli.modulars.creater.table.service.impl;

import org.opsli.common.enums.DictType;
import org.opsli.common.exception.ServiceException;
import org.opsli.common.utils.WrapperUtil;
import org.opsli.core.base.service.impl.CrudServiceImpl;
import org.opsli.core.creater.msg.CreaterMsg;
import org.opsli.modulars.creater.column.service.ITableColumnService;
import org.opsli.modulars.creater.column.wrapper.CreaterTableColumnModel;
import org.opsli.modulars.creater.general.actuator.SQLActuator;
import org.opsli.modulars.creater.table.entity.CreaterTable;
import org.opsli.modulars.creater.table.mapper.TableMapper;
import org.opsli.modulars.creater.table.service.ITableService;
import org.opsli.modulars.creater.table.wrapper.CreaterTableAndColumnModel;
import org.opsli.modulars.creater.table.wrapper.CreaterTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @BelongsProject: opsli-boot
 * @Author: Parker
 * @CreateTime: 2020-09-16 17:34
 * @Description: 代码生成器 - 表 接口实现类
 */
@Service
public class TableServiceImpl extends CrudServiceImpl<TableMapper, CreaterTable, CreaterTableModel>
        implements ITableService {

    @Autowired(required = false)
    private TableMapper mapper;

    @Autowired
    private ITableColumnService iTableColumnService;

    @Autowired(required = false)
    private SQLActuator sqlActuator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreaterTableModel insert(CreaterTableModel model) {
        if(model == null) return null;

        CreaterTable entity = super.transformM2T(model);
        // 唯一验证
        Integer count = mapper.uniqueVerificationByTableName(entity);
        if(count != null && count > 0){
            // 重复
            throw new ServiceException(CreaterMsg.EXCEPTION_TABLE_NAME_REPEAT);
        }

        // 新增后 默认未同步
        model.setIzSync(
                DictType.NO_YES_NO.getCode().charAt(0));

        // 默认旧表名称为当前新增名称（用于删除表操作）
        model.setOldTableName(model.getTableName());

        return super.insert(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreaterTableModel update(CreaterTableModel model) {
        if(model == null) return null;

        CreaterTable entity = super.transformM2T(model);
        // 唯一验证
        Integer count = mapper.uniqueVerificationByTableName(entity);
        if(count != null && count > 0){
            // 重复
            throw new ServiceException(CreaterMsg.EXCEPTION_TABLE_NAME_REPEAT);
        }

        CreaterTableModel oldModel = this.get(model.getId());


        // 修改后 默认未同步
        model.setIzSync(
                DictType.NO_YES_NO.getCode().charAt(0));

        // 默认旧表名称为 修改前表名 便于改表后删除操作
        model.setOldTableName(oldModel.getTableName());

        return super.update(model);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAny(CreaterTableAndColumnModel model){
        CreaterTableModel tableModel = WrapperUtil.transformInstance(model, CreaterTableModel.class);
        // 保存表头数据
        tableModel = this.insert(tableModel);
        if(tableModel != null){
            // 删除旧字段 全部新增
            iTableColumnService.delByTableId(tableModel.getId());

            // 保存 表结构数据
            List<CreaterTableColumnModel> columnList = model.getColumnList();
            for (CreaterTableColumnModel tableColumnModel : columnList) {
                tableColumnModel.setTableId(tableModel.getId());
            }
            iTableColumnService.insertBatch(columnList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAny(CreaterTableAndColumnModel model){
        CreaterTableModel tableModel = WrapperUtil.transformInstance(model, CreaterTableModel.class);
        // 保存表头数据
        tableModel = this.update(tableModel);
        if(tableModel != null){
            // 删除旧字段 全部新增
            iTableColumnService.delByTableId(tableModel.getId());

            // 保存 表结构数据
            List<CreaterTableColumnModel> columnList = model.getColumnList();
            for (CreaterTableColumnModel tableColumnModel : columnList) {
                tableColumnModel.setTableId(tableModel.getId());
            }
            iTableColumnService.insertBatch(columnList);
        }

        sqlActuator.execute("select * from sys_user");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdAny(String id) throws Exception{
        // 删除表头
        boolean delFlag = super.delete(id);
        if(!delFlag){
            throw new Exception();
        }
        // 删除字段
        iTableColumnService.delByTableId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdsAny(String[] ids) throws Exception{
        // 删除表头
        boolean delFlag = super.deleteAll(ids);
        if(!delFlag){
            throw new Exception();
        }

        // 删除字段
        iTableColumnService.delByTableIds(ids);
    }

    @Override
    public void renewSyncState(String id) {
        mapper.renewSyncState(id);
    }

}


