package common.globe;


/**
 * @Description: http相关常量
 * @author: ethanchiu@126.com
 * @date: May 12, 2014
 */
public interface HttpConst {
	int category = 1;
	int version = 1;
	int platform = 1; //1 表示android
	int context	= 2;
	int option = 0;

	int succeed = 1; //响应代码 1成功，2失败
	int fail = 2; //响应代码 1成功，2失败
}
