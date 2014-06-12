package com.naicha.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naicha.vo.JsonEntity;
import common.base.BaseEntity;
import common.cmdimpl.CommandHttp;
import common.globe.HttpConst;

/**
 * @Description: 注册http任务
 * @author: ethanchiu@126.com
 * @date: Dec 24, 2013
 */
public class DoHttpPostTask {

	public interface CommandId {

		/** 意见反馈 */
		public static final int COMMAND_USER_FEEDBACK = 7;

	}

	public static void doTask(int taskId, String json, CommandHttp commandHttp) {
		Gson gson = new Gson();

		JsonEntity<? extends BaseEntity> response = null;

		switch (taskId) {

		case CommandId.COMMAND_USER_FEEDBACK: {
			response = gson.fromJson(json, new TypeToken<JsonEntity>() {
			}.getType());
			break;
		}

		default:
			break;
		}

		if (response == null || response.getCode() == HttpConst.fail) {
			commandHttp.sendFailureMessage(response);
		} else if (response.getCode() == HttpConst.succeed) {
			if (response.getData() != null) {
				commandHttp.sendSuccessMessage(response.getData());
			} else {
				commandHttp.sendSuccessMessage(response);
			}
		}

	}

}
