package common.exception;
/**
 * @Description: 无SD卡异常
 * @author: ethanchiu@126.com
 * @date: 2013-7-30
 */
public class NoSDCardException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoSDCardException(){
		
	}
	
	public NoSDCardException(String detailMessage){
		super(detailMessage);
	}
	
	
}
