/*
 * Copyright (C) 2013  ethanchiu@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simple.compoment.db.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import simple.compoment.db.entity.PKPropertyEntity;
import simple.compoment.db.entity.PropertyEntity;
import simple.compoment.db.entity.TableInfoEntity;
import simple.compoment.log.LogUtil;

import common.exception.DBException;

/**
 * @Description: 数据库表工厂类
 * @author: ethanchiu@126.com
 * @date: 2013-6-21
 */
public class TableInfofactory
{
	/**所有表实体 表名为键，表信息为值的HashMap*/
	private static final HashMap<String, TableInfoEntity> tableInfoEntityMap = new HashMap<String, TableInfoEntity>();

	private TableInfofactory()
	{
	}

	private static TableInfofactory instance;

	/**
	 * 获得数据库表工厂
	 * 
	 * @return 数据库表工厂
	 */
	public static TableInfofactory getInstance()
	{
		if (instance == null)
		{
			instance = new TableInfofactory();
		}
		return instance;
	}

	/**
	 * 获得指定表信息，如果没有就新建一个表
	 * 
	 * @param clazz 实体类型
	 * @return 表信息
	 * @throws DBException
	 */
	public TableInfoEntity getTableInfoEntity(Class<?> clazz)
			throws DBException
	{
		if (clazz == null)
			throw new DBException("表信息获取失败，因为class为null");
		TableInfoEntity tableInfoEntity = tableInfoEntityMap.get(clazz
				.getName());
		if (tableInfoEntity == null)
		{
			//===========设置表名 begin=====================
			tableInfoEntity = new TableInfoEntity();
			tableInfoEntity.setTableName(DBAnnoUtils.getTableName(clazz));
			tableInfoEntity.setClassName(clazz.getName());
			//===========设置表名 end=====================
			
			//===========设置主键 begin =====================
			Field idField = DBAnnoUtils.getPrimaryKeyField(clazz);
			if (idField != null)
			{
				PKPropertyEntity pkProperyEntity = new PKPropertyEntity();
				pkProperyEntity.setColumnName(DBAnnoUtils
						.getColumnByField(idField));
				pkProperyEntity.setName(idField.getName());
				pkProperyEntity.setType(idField.getType());
				pkProperyEntity.setAutoIncrement(DBAnnoUtils
						.isAutoIncrement(idField));
				
				LogUtil.d(this, "pkProperyEntity-->" + pkProperyEntity.toString());
				tableInfoEntity.setPkProperyEntity(pkProperyEntity);
			} else//否则没有主键
			{
				tableInfoEntity.setPkProperyEntity(null);
			}
			//===========设置主键 end =====================
			
			//===========设置其他字段 begin =====================
			List<PropertyEntity> propertyList = DBAnnoUtils
					.getPropertyList(clazz);
			LogUtil.d(this, "propertyList-->" + propertyList.toString());
			if (propertyList != null)
			{
				tableInfoEntity.setPropertieArrayList(propertyList);
			}
			//===========设置其他字段 end =====================
			
			tableInfoEntityMap.put(clazz.getName(), tableInfoEntity);
		}
		if (tableInfoEntity == null
				|| tableInfoEntity.getPropertieArrayList() == null
				|| tableInfoEntity.getPropertieArrayList().size() == 0)
		{
			throw new DBException("不能创建+" + clazz + "的表信息");
		}
		return tableInfoEntity;
	}
}
