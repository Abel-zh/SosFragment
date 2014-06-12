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
 * @Description: 返回的数据
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class ResponseEntity extends BaseEntity
{
	private static final long serialVersionUID = 2715141726276497343L;
	private Object tag;
	private Object data;
	private Object data2;

	private String activityKey;
	private int activityKeyResID;

	public ResponseEntity()
	{
	}

	public ResponseEntity(Object tag, Object data)
	{
		this.tag = tag;
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

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
	
	public Object getData2() {
		return data2;
	}

	public void setData2(Object data2) {
		this.data2 = data2;
	}

	public String getActivityKey()
	{
		return activityKey;
	}

	public void setActivityKey(String activityKey)
	{
		this.activityKey = activityKey;
	}

	@Override
	public String toString()
	{
		return "Response [tag=" + tag + ", data=" + data + ", activityKey="
				+ activityKey + ", activityKeyResID=" + activityKeyResID + "]";
	}

}
