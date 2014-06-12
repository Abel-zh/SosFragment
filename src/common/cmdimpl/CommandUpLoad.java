package common.cmdimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import simple.compoment.log.LogUtil;
import simple.framework.mvc.controller.command.base.AbsCommand;
import simple.framework.mvc.model.RequestEntity;

import com.google.gson.Gson;
import com.naicha.common.HostConfig;
import com.naicha.util.ValueUtil;
import common.globe.TAGLOG;

/**
 * @Description: 上传文件指令
 * @author: ethanchiu@126.com
 * @date: Jun 9, 2014
 */
public class CommandUpLoad extends AbsCommand{

	String upLoadPath = HostConfig.getUpLoadPath();

	@Override
	protected void executeCommand() {

		RequestEntity request = getRequest();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> param = (HashMap<String, Object>) request.getData();
		
		String filePath = (String) request.getTag();
		
		new UploadThread(param, filePath).start();

	}

	/**
	 * @Description: 上传线程
	 * @author: ethanchiu@126.com
	 * @date: Jun 9, 2014
	 */
	class UploadThread extends Thread{

		private Map<String, Object> request;
		private String filePath;

		public UploadThread(Map<String, Object> request, String filePath){
			this.request = request;
			this.filePath = filePath;
		}

		@Override
		public void run() {
			super.run();

			doUpload(request, filePath);
		}
	}

	/**
	 * @Description: 下载任务
	 * @author: ethanchiu@126.com
	 * @date: Jun 9, 2014
	 * @param request
	 */
	private void doUpload(Map<String, Object> request, String filePath){
		
		OutputStream out = null;
		InputStream input = null;
		try {
			URL url;

			url = new URL(upLoadPath);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			out = conn.getOutputStream();
			// out header

			Gson g = new Gson();
			String json = g.toJson(request);
			LogUtil.d(TAGLOG.TAG_HTTP, "request-->" + json);

			out.write(json.getBytes("UTF-8"));

			out.write("\r\n\r".getBytes("UTF-8"));
			out.flush();

			
			//读取文件
//			String filePath = dbPath + dbName;

			LogUtil.d(TAGLOG.TAG_HTTP, "output filePath-->" + filePath);
			
			InputStream in = new FileInputStream(new File(filePath));

			byte[] readBuffer = new byte[1024];
			int readLength = 0;
			while((readLength = in.read(readBuffer)) != -1) {
				for (int i = 0; i < readLength; i ++) {
					System.out.print(readBuffer[i]+",");
				}
				out.write(readBuffer, 0, readLength);
				out.flush();
			}

			//返回结果
			input = conn.getInputStream();

			BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(input));
			StringBuffer tStringBuffer = new StringBuffer();
			String sTempOneLine = new String("");
			while ((sTempOneLine = tBufferedReader.readLine()) != null){
				tStringBuffer.append(sTempOneLine);
			}

			out.close();
			input.close();

			LogUtil.d(TAGLOG.TAG_HTTP, "response-->" + tStringBuffer.toString());

			//解析json
			JSONObject jsonObj = new JSONObject(tStringBuffer.toString());
			String data = jsonObj.getString("data");
			JSONObject dataObj = new JSONObject(data);
			String fileId = dataObj.getString("fileId");

			if(ValueUtil.isStrNotEmpty(fileId)){

				sendSuccessMessage(fileId);

			}

		} catch (Exception e1) {
			e1.printStackTrace();
			
			sendFailureMessage(e1);
		} 
	}

}
