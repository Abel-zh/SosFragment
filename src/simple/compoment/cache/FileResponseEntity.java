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
package simple.compoment.cache;

import common.base.BaseEntity;
import common.util.CusAsyncTask;

public class FileResponseEntity extends BaseEntity
{
	private static final long serialVersionUID = 5525015855679979479L;
	private CusAsyncTask<?, ?, ?> task;
	private Object object;

	public CusAsyncTask<?, ?, ?> getTask()
	{
		return task;
	}

	public void setTask(CusAsyncTask<?, ?, ?> task)
	{
		this.task = task;
	}

	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

}
