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
package simple.compoment.db.entity;

/**
 * @Description: 数据库的字段
 * @author: ethanchiu@126.com
 * @date: 2013-6-25
 */
public class PropertyEntity
{
	/** 实体成员名*/
	protected String name;
	/** 字段名*/
	protected String columnName;
	/** 数据类型*/
	protected Class<?> type;
	/** 缺省值*/
	protected Object defaultValue;
	/** 是否允许为null*/
	protected boolean isAllowNull = true;
	protected int index; // 暂时不写
	/** 是否主键*/
	protected boolean primaryKey = false;
	/** 是否自增*/
	protected boolean autoIncrement = false;

	public PropertyEntity()
	{

	}

	public PropertyEntity(String name, Class<?> type, Object defaultValue,
			boolean primaryKey, boolean isAllowNull, boolean autoIncrement,
			String columnName)
	{
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.primaryKey = primaryKey;
		this.isAllowNull = isAllowNull;
		this.autoIncrement = autoIncrement;
		this.columnName = columnName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Class<?> getType()
	{
		return type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public boolean isPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	public boolean isAllowNull()
	{
		return isAllowNull;
	}

	public void setAllowNull(boolean isAllowNull)
	{
		this.isAllowNull = isAllowNull;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public boolean isAutoIncrement()
	{
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement)
	{
		this.autoIncrement = autoIncrement;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	@Override
	public String toString()
	{
		return "PropertyEntity [name=" + name + ", columnName=" + columnName
				+ ", type=" + type + ", defaultValue=" + defaultValue
				+ ", isAllowNull=" + isAllowNull + ", index=" + index
				+ ", primaryKey=" + primaryKey + ", autoIncrement="
				+ autoIncrement + "]";
	}
	
}
