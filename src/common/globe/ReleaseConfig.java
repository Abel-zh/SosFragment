package common.globe;
/**
 * @Description: 发布时相关的配置
 * @author: ethanchiu@126.com
 * @date: Apr 30, 2014
 */
public class ReleaseConfig {
	
	/** 测试代码开关 发布时为false */
	public static final boolean TEST_CODE = true;
	
	/** 日志开关，发布的时候改为false */
	public static final boolean LOG_DEBUG = true;
	
	/** 放到sd卡中数据的默认路径 */
	public static final String SDCARD_PATH = "Sosino";

	/** 默认数据库的名字 */
	public static final String DB_NAME = "sosino.db";
	
	/** 系统缓存*/
	public static final String SYSTEM_CACHE = "simple_systemcache";
	
}
