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
package simple.framework.mvc.controller.command.base;

import simple.framework.mvc.model.IResponseListener;
import simple.framework.mvc.model.RequestEntity;
import simple.framework.mvc.model.ResponseEntity;

/**
 * @Description: 封装Command调度基类管理 实现 ICommand接口。
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public abstract class BaseCommand implements ICommand
{
	private RequestEntity request;
	private ResponseEntity response;
	private IResponseListener responseListener;
	private boolean terminated;

	@Override
	public RequestEntity getRequest()
	{
		return request;
	}

	@Override
	public void setRequest(RequestEntity request)
	{
		this.request = request;
	}

	@Override
	public ResponseEntity getResponse()
	{
		return response;
	}

	@Override
	public void setResponse(ResponseEntity response)
	{
		this.response = response;
	}

	@Override
	public IResponseListener getResponseListener()
	{
		return responseListener;
	}

	@Override
	public void setResponseListener(IResponseListener responseListener)
	{
		this.responseListener = responseListener;
	}

	@Override
	public void setTerminated(boolean terminated)
	{
		this.terminated = terminated;
	}

	@Override
	public boolean isTerminated()
	{
		return terminated;
	}

}
