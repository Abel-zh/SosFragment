package common.cmdimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import simple.compoment.log.LogUtil;
import simple.framework.mvc.controller.command.base.AbsCommand;
import simple.framework.mvc.model.RequestEntity;

import com.google.gson.Gson;
import com.naicha.common.HostConfig;
import common.globe.TAGLOG;

/**
 * @Description: 下载指令
 * @author: ethanchiu@126.com
 * @date: Jun 9, 2014
 */
public class CommandDownLoad extends AbsCommand {

	@Override
	protected void executeCommand() {

		RequestEntity request = getRequest();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> param = (HashMap<String, Object>) request.getData();

		String filePath = (String) request.getTag();

		new DownLoadThread(filePath, param).start();
	}

	/**
	 * @Description: 下载线程
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 */
	class DownLoadThread extends Thread {

		private String filePath;
		private Map<String, Object> param;

		public DownLoadThread(String filePath, Map<String, Object> param) {
			this.filePath = filePath;
			this.param = param;
		}

		@Override
		public void run() {
			super.run();

			doDownLoad(param, filePath);
		}
	}

	/**
	 * @Description: 请求方法
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 * @param request
	 *            请求的json
	 * @param filePath
	 *            文件路径
	 */
	private void doDownLoad(Map<String, Object> request, String filePath) {
		try {

			URL url = new URL(HostConfig.getDownLoadPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			OutputStream out = conn.getOutputStream();

			Gson g = new Gson();
			String json = g.toJson(request);
			LogUtil.d(TAGLOG.TAG_HTTP, "request-->" + json);

			out.write(json.getBytes("UTF-8"));
			out.flush();

			InputStream is = conn.getInputStream();

			// byte[] readBuffer = new byte[1024];
			// int readLength = 0;
			// while((readLength = in.read(readBuffer)) != -1) {
			// // WENJINN
			// for (int i = 0; i < readLength; i ++) {
			// System.out.print(readBuffer[i]+",");
			// }
			// LogUtil.e("out put");
			// out.write(readBuffer, 0, readLength);
			// out.flush();
			// }

			LogUtil.d(TAGLOG.TAG_HTTP, "input filePath-->" + filePath);

			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();

			OutputStream os = new FileOutputStream(file);
			byte[] readBuffer = new byte[1024];
			int readLen = 0;
			while ((readLen = is.read(readBuffer)) != -1) {
				os.write(readBuffer, 0, readLen);
				os.flush();
			}

			LogUtil.d(TAGLOG.TAG_HTTP, "download finish");

		} catch (Exception e) {
			e.printStackTrace();
			sendFailureMessage(e);
		}
	}

}
