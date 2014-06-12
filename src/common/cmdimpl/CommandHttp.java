package common.cmdimpl;

import java.net.ConnectException;
import java.util.HashMap;

import org.apache.http.client.HttpResponseException;

import simple.compoment.http.AsyncHttpResponseHandler;
import simple.compoment.http.BaseAsyncHttpClient;
import simple.compoment.log.LogUtil;
import simple.framework.mvc.controller.command.base.AbsCommand;
import simple.framework.mvc.model.RequestEntity;

import com.naicha.common.DoHttpPostTask;
import com.naicha.common.HostConfig;
import com.naicha.vo.JsonEntity;
import common.globe.App;
import common.globe.TAGLOG;

/**
 * @Description: http的请求commd
 * @author: ethanchiu@126.com
 * @date: 2013-7-10
 */
public class CommandHttp extends AbsCommand {

	BaseAsyncHttpClient mSyncHttpClient = App.getApp().getHttpClient();

	public interface HTTP_METHOD {
		int get = 01;
		int post = 02;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeCommand() {
		final int httpMethod = getRequest().getArg1();
		try {
			switch (httpMethod) {
			case HTTP_METHOD.get: {
				doGet();
				break;
			}
			case HTTP_METHOD.post: {
				doPost();
				break;
			}
			default:
				doPost();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: get任务
	 * @author: ethanchiu@126.com
	 * @date: May 9, 2014
	 */
	private void doGet() {

		RequestEntity request = getRequest();

		final int taskId = (Integer) request.getTag();
		String param = (String) request.getData();
		mSyncHttpClient.get(HostConfig.getWeatherPath(param), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				LogUtil.d(TAGLOG.TAG_HTTP, "response-->" + content);
				// doGetTask(taskId, content);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				CommandHttp.this.sendFailureMessage(content);
			}

		});
	}

	/**
	 * @Description: post任务
	 * @author: ethanchiu@126.com
	 * @date: Oct 23, 2013
	 */
	private void doPost() {

		RequestEntity request = getRequest();

		HashMap<String, Object> param = (HashMap<String, Object>) request.getData();

		final int commandId = (Integer) param.get("command");

		mSyncHttpClient.post(HostConfig.getHostPath(null), param, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				LogUtil.d(TAGLOG.TAG_HTTP, "response-->" + content);

				doTask(commandId, content);

			}

			@Override
			public void onFailure(Throwable error, String content) {
				LogUtil.d(TAGLOG.TAG_HTTP, "response error-->" + content + " error-->" + error);

				if (error instanceof HttpResponseException) {
					content = "网络返回异常";
				}

				if (error instanceof ConnectException) {
					content = "网络连接异常";
				}

				Object[] objs = new Object[2];

				@SuppressWarnings("rawtypes")
				JsonEntity jsonEntity = new JsonEntity();
				jsonEntity.setDesc(content);

				objs[0] = jsonEntity;
				objs[1] = error;

				CommandHttp.this.sendFailureMessage(objs);
			}

		});
	}

	private void doTask(int taskId, String json) {
		DoHttpPostTask.doTask(taskId, json, this);
	}

}
