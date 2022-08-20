/**
 * @(#)QuartzJob.java 1.6 15/09/12
 * MODIFY MEMO:
 * 
 * dhj/2015_09_12/主要修改程序注释，因为程序要按照统一的JAVA编码规范
 * 
 */
package common.system.exception;
/**
 * Description:用于自定义异常
 * 说明：统一处理成继承父类异常
 * @author dhj 
 * @version 1.0
 */
public class AppException extends RuntimeException
{
  /**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = -3050539055104786439L;

/**
   * 主构造方法
   * @param msg 异常捕获的信息
   */
  public AppException(String msg) {
          super(msg);
  }
  
  public AppException(Throwable cause){
		super(cause);
  }
	
  public AppException(String message,Throwable cause){
		super(message,cause);
  }
  
}
