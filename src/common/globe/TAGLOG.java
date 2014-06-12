package common.globe;
/**
 * @Description: log跟踪tag
 * @author: ethanchiu@126.com
 * @date: 2013-7-3
 */
public interface TAGLOG
{
	/** 临时TAG*/
	String TAG_COMMON = "tag_common";
	
	/**activity生命周期 Tag*/
	String TAG_ACTIVITY = "tag_activity";
	
	/** fragment生命周期  TAG*/
	String TAG_FRAGMENT = "tag_fragment";
	
	/**数据库*/
	String TAG_DB = "tag_db";
	
	/**广播接收器*/
	String TAG_BROADCAST = "tag_broadcast";
	
	/**所有http请求的跟踪*/
	String TAG_HTTP = "tag_http";
	
	/**所有事件请求跟踪*/
	String TAG_EVENT = "tag_event";
	
}
