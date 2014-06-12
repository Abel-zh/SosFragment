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
package simple.framework.mvc.model;

import common.base.BaseEntity;

/**
 * @Description: 请求的数据
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class RequestEntity extends BaseEntity
{
	private static final long serialVersionUID = 444834403356593608L;
	private Object tag;
	private int arg1;
	private int arg2;
	private Object data;

	public RequestEntity()
	{
	}
	
	public RequestEntity(Object tag, int arg1, int arg2, Object data)
	{
		super();
		this.tag = tag;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.data = data;
	}

	public Object getTag()
	{
		return tag;
	}

	public void setTag(Object tag)
	{
		this.tag = tag;
	}

	public int getArg1()
	{
		return arg1;
	}

	public void setArg1(int arg1)
	{
		this.arg1 = arg1;
	}

	public int getArg2()
	{
		return arg2;
	}

	public void setArg2(int arg2)
	{
		this.arg2 = arg2;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

}
