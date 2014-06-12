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
package simple.compoment.db.util.sql;

import java.lang.reflect.Field;

import simple.compoment.db.entity.NVArrayList;
import simple.compoment.db.util.DBAnnoUtils;

import common.exception.DBException;

/**
 * @Description: 删除sql语句构建器类
 * @author: ethanchiu@126.com
 * @date: 2013-6-21
 */
public class DeleteSqlBuilder extends SqlBuilder
{
	@Override
	public String buildSql() throws DBException, IllegalArgumentException,
			IllegalAccessException
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		stringBuilder.append("DELETE FROM ");
		stringBuilder.append(tableName);
		if (entity == null)
		{
			stringBuilder.append(buildConditionString());
		} else
		{
			stringBuilder.append(buildWhere(buildWhere(this.entity)));
		}
		return stringBuilder.toString();
	}

	/**
	 * 创建Where语句
	 * 
	 * @param entity
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws DBException
	 */
	public NVArrayList buildWhere(Object entity)
			throws IllegalArgumentException, IllegalAccessException,
			DBException
	{
		Class<?> clazz = entity.getClass();
		NVArrayList whereArrayList = new NVArrayList();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields)
		{
			field.setAccessible(true);
			if (!DBAnnoUtils.isTransient(field))
			{
				if (DBAnnoUtils.isBaseDateType(field))
				{
					// 如果ID不是自动增加的
					if (!DBAnnoUtils.isAutoIncrement(field))
					{
						String columnName = DBAnnoUtils.getColumnByField(field);
						if (null != field.get(entity)
								&& field.get(entity).toString().length() > 0)
						{
							whereArrayList.add(
									(columnName != null && !columnName
											.equals("")) ? columnName : field
											.getName(), field.get(entity)
											.toString());
						}
					}
				}
			}
		}
		if (whereArrayList.isEmpty())
		{
			throw new DBException("不能创建Where条件，语句");
		}
		return whereArrayList;
	}
}
