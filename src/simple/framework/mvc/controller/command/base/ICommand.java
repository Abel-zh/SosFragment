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
 * @Description: 定义业务需要的操作， 然后定义个抽象的实现类进行包装一下， 
 * 最后使用的时候，仅仅需要继承被包装的抽象类。
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public interface ICommand
{
	RequestEntity getRequest();

	void setRequest(RequestEntity request);

	ResponseEntity getResponse();

	void setResponse(ResponseEntity response);

	void execute();

	IResponseListener getResponseListener();

	void setResponseListener(IResponseListener listener);

	void setTerminated(boolean terminated);

	boolean isTerminated();

}
