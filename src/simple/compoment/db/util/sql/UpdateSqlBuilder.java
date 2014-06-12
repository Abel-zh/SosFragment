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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.http.NameValuePair;

import simple.compoment.db.annotation.PrimaryKey;
import simple.compoment.db.entity.NVArrayList;
import simple.compoment.db.util.DBAnnoUtils;

import common.exception.DBException;
import common.util.check.StringUtils;

/**
 * @Description: 更新sql语句构建器类
 * @author: ethanchiu@126.com
 * @date: 2013-6-21
 */
public class UpdateSqlBuilder extends SqlBuilder
{

	@Override
	public void onPreGetStatement() throws DBException,
			IllegalArgumentException, IllegalAccessException
	{
		if (getUpdateFields() == null)
		{
			setUpdateFields(getFieldsAndValue(entity));
		}
		super.onPreGetStatement();
	}

	@Override
	public String buildSql() throws DBException, IllegalArgumentException,
			IllegalAccessException
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		stringBuilder.append("UPDATE ");
		stringBuilder.append(tableName).append(" SET ");

		NVArrayList needUpdate = getUpdateFields();
		for (int i = 0; i < needUpdate.size(); i++)
		{
			NameValuePair nameValuePair = needUpdate.get(i);
			stringBuilder
					.append(nameValuePair.getName())
					.append(" = ")
					.append(StringUtils.isNumeric(nameValuePair.getValue()
							.toString()) ? nameValuePair.getValue() : "'"
							+ nameValuePair.getValue() + "'");
			if (i + 1 < needUpdate.size())
			{
				stringBuilder.append(", ");
			}
		}
		if (!StringUtils.isEmpty(this.where))
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
					Annotation annotation = field
							.getAnnotation(PrimaryKey.class);
					if (annotation != null)
					{
						String columnName = DBAnnoUtils.getColumnByField(field);
						whereArrayList.add((columnName != null && !columnName
								.equals("")) ? columnName : field.getName(),
								field.get(entity).toString());
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

	/**
	 * 从实体加载,更新的数据
	 * 
	 * @return
	 * @throws DBException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static NVArrayList getFieldsAndValue(Object entity)
			throws DBException, IllegalArgumentException,
			IllegalAccessException
	{
		NVArrayList arrayList = new NVArrayList();
		if (entity == null)
		{
			throw new DBException("没有加载实体类！");
		}
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields)
		{

			if (!DBAnnoUtils.isTransient(field))
			{
				if (DBAnnoUtils.isBaseDateType(field))
				{
					PrimaryKey annotation = field
							.getAnnotation(PrimaryKey.class);
					if (annotation == null || !annotation.autoIncrement())
					{
						String columnName = DBAnnoUtils.getColumnByField(field);
						field.setAccessible(true);
						arrayList
								.add((columnName != null && !columnName
										.equals("")) ? columnName : field
										.getName(),
										field.get(entity) == null ? null
												: field.get(entity).toString());
					}
				}
			}
		}
		return arrayList;
	}
}
