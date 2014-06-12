package common.cmdimpl;

import java.util.HashMap;
import java.util.Map;

import common.globe.HttpConst;

import simple.framework.mvc.model.RequestEntity;

/**
 * @Description: 网络请求参数
 * @author: ethanchiu@126.com
 * @date: May 28, 2014
 */
public class CommandParam
{
	/**
	 * @author: ethanchiu@126.com
	 * @date: May 28, 2014
	 * @param requestBody 请求体(如不需要体则传null)
	 * @param taskHttpId 任务id
	 * @return
	 */
	public static RequestEntity getRequestEntity(int command, Map<String, Object> requestBody)
	{
		RequestEntity request = new RequestEntity();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", HttpConst.category);
		params.put("version", HttpConst.version);
		params.put("command", command);
		params.put("platform", HttpConst.platform);
		params.put("context", HttpConst.context);
		params.put("option", HttpConst.option);
		
		if (requestBody != null)
			params.put("data", requestBody);
		
		request.setData(params);
		request.setTag(command);
		
		return request;
	}
}
