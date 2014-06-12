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

import java.util.ArrayList;
import java.util.List;

import common.base.BaseEntity;

/**
 * @Description: 表实体：包括主键，字段，表名等
 * @author: ethanchiu@126.com
 * @date: 2013-6-25
 */
public class TableInfoEntity extends BaseEntity
{
	private static final long serialVersionUID = 488168612576359150L;
	/** 表名 */
	private String tableName = "";
	/** 对应的实体名*/
	private String className = "";
	/** 主键字段*/
	private PKPropertyEntity pkProperyEntity = null;
	/** 其他字段*/
	ArrayList<PropertyEntity> propertieArrayList = new ArrayList<PropertyEntity>();

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public ArrayList<PropertyEntity> getPropertieArrayList()
	{
		return propertieArrayList;
	}

	public void setPropertieArrayList(List<PropertyEntity> propertyList)
	{
		this.propertieArrayList = (ArrayList<PropertyEntity>) propertyList;
	}

	public PKPropertyEntity getPkProperyEntity()
	{
		return pkProperyEntity;
	}

	public void setPkProperyEntity(PKPropertyEntity pkProperyEntity)
	{
		this.pkProperyEntity = pkProperyEntity;
	}

}
