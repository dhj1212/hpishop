package common.util;

import com.sun.corba.se.impl.io.TypeMismatchException;
import common.system.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
	private static Util instance;
	protected final Logger log = LoggerFactory.getLogger(Util.class);

	public static Util getInstance()
	{
		if (instance != null) {
			return instance;
		}else {
			return instance = new Util();
		}
	}

	/**
	 * 
	 * @方法说明: 判断字符串是否为空 
	 * @参数： @param str
	 * @参数： @return   
	 * @返回值： boolean  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public boolean isEmpty(String str)
	{
		if (str != null && str.trim().length() > 0 && !str.equals("null")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @方法说明: 判断是否不为空
	 * @参数： @param str
	 * @参数： @return   
	 * @返回值： boolean  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public boolean isNotEmpty(String str)
	{
		if (str != null && str.trim().length() > 0 && !str.equals("null")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @方法说明: 字符串转换成日期
	 * @参数： @param strdate
	 * @参数： @return
	 * @参数： @throws AppException   
	 * @返回值： Date  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年8月5日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public Date getFormatDate(String strdate) throws AppException
	{
		if(strdate==null||strdate.equals("")) {
			return null;
		}
		try
		{
			//log.info("strdate===:"+strdate.length());
			String pattern = "";
			if(strdate.length()>18)
			{
				pattern = "yyyy-MM-dd kk:mm:ss";
			}
			else if(strdate.length()>15)
			{
				pattern = "yyyy-MM-dd HH:mm";
			}else{
				pattern = "yyyy-MM-dd";
			}
			SimpleDateFormat formater = new SimpleDateFormat(pattern);

			return formater.parse(strdate);
		} catch (ParseException error)
		{
			log.error("字符串格式化成Date时程序出错：(Util类中的getFormatDate方法)"
					+ error.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明: 格式化日期
	 * @参数： @param date
	 * @参数： @param format
	 * @参数： @return
	 * @参数： @throws Exception   
	 * @返回值： Date  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public Date getFormat(Date date, String format) throws Exception
	{
		try {
			if (date == null || date.equals("") || format == null || format.equals(""))
				return null;
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat(format);
			return formatter.parse(formatter.format(date));
		} catch (Exception ex) {
			log.error("日期转换时程序出错：(Util类中的getFormat方法)" + ex.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @方法说明: 把字符型日期转换成Date
	 * @参数： @param source
	 * @参数： @return   
	 * @返回值： Date  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public Date convertDate(String source)
	{
		SimpleDateFormat sdf = getSimpleDateFormat(source);
		try {
			Date date = sdf.parse(source);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SimpleDateFormat getSimpleDateFormat(String source)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd
																								// HH-mm-ss
			sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", source)) { // yyyy-MM-dd
																								// HH:mm:ss
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd
			sdf = new SimpleDateFormat("yyyy/MM/dd");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd
																								// HH/mm/ss
			sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)) { // yyyyMMdd
			sdf = new SimpleDateFormat("yyyyMMdd");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2} \\d{2}\\d{2}\\d{2}$", source)) { // yyyyMMdd
																							// HHmmss
			sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd
			sdf = new SimpleDateFormat("yyyy.MM.dd");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2} \\d{2}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd
																										// HH.mm.ss
			sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
		} else {
			System.out.println("TypeMismatchException");
			throw new TypeMismatchException();
		}
		return sdf;
	}

	/**
	 * 
	 * @方法说明:  不区分大小写的替换
	 * @参数： @param source
	 * @参数： @param oldstring
	 * @参数： @param newstring
	 * @参数： @return   
	 * @返回值： String  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String IgnoreCaseReplace(String source, String oldstring, String newstring)
	{
		Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		String ret = m.replaceAll(newstring);
		return ret;
	}

	/**
	 * 
	 * @方法说明: 获取文件中文名称
	 * @参数： @param request
	 * @参数： @param filename
	 * @参数： @param code
	 * @参数： @return
	 * @参数： @throws UnsupportedEncodingException   
	 * @返回值： String  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public String getFileNameCode(HttpServletRequest request, String filename, String code)
			throws UnsupportedEncodingException
	{
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			filename = java.net.URLEncoder
					.encode(filename.trim().replace("-", "").replace(" ", "").trim(), code);
		} else {
			filename = new String(filename.trim().replace("-", "").replace(" ", "").getBytes(code),
					"iso-8859-1");
		}
		return filename;
	}

	/**
	 * 
	 * @方法说明: 比较两个日期只差，返回秒
	 * @参数： @param begindate
	 * @参数： @param enddate
	 * @参数： @return
	 * @参数： @throws Exception   
	 * @返回值： long  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public long getdiffdateSec(Date begindate, Date enddate) throws Exception
	{
		begindate = this.getFormat(begindate, "yyyy-MM-dd HH:mm:ss");
		enddate = this.getFormat(enddate, "yyyy-MM-dd HH:mm:ss");
		long sec = 0;
		long time1 = begindate.getTime();
		long time2 = enddate.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		sec = diff / 1000;
		return sec;
	}

	/**
	 * 
	 * @方法说明: 比较开始日期不能大于结束日期
	 * @参数： @param beginTime
	 * @参数： @param endTime
	 * @参数： @return   
	 * @返回值： boolean  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年7月16日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public boolean compareDate(Date beginTime, Date endTime)
	{

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (begin.after(end)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @description: 获取当前年份
	 * @param date
	 * @return int
	 * @throws
	 * @author dhj
	 * @date 2022/5/5 10:55
	*/
	public int getYear(Date date)
	{
		if (date==null){
			return 0;
		}
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	  * 
	  * @方法说明: 判断文件是否超出大小
	  * @参数： @param len 文件长度
	  * @参数： @param size 限制大小
	  * @参数： @param unit 限制单位（B,K,M,G）
	  * @参数： @return   
	  * @返回值： boolean  
	  * @异常：
	  * @作者： duhj
	  * @创建日期 2021年9月24日
	  *
	  *举例：u.checkFileSize(file.length(),100,"M")
	  * 历史记录
	  * 1、修改日期：
	  *    修改人：
	  *    修改内容：
	  */
	 public  boolean checkFileSize(Long len, int size, String unit) {
	//      long len = multipartFile.getSize();
	      double fileSize = 0;
	      if ("B".equals(unit.toUpperCase())) {
	          fileSize = (double) len;
	      } else if ("K".equals(unit.toUpperCase())) {
	          fileSize = (double) len / 1024;
	      } else if ("M".equals(unit.toUpperCase())) {
	          fileSize = (double) len / 1048576;
	          System.out.println("fileSize=="+fileSize);;
	      } else if ("G".equals(unit.toUpperCase())) {
	          fileSize = (double) len / 1073741824;
	      }
	      if (fileSize > size) {
	          return false;
	      }
	      return true;
	 }
	 
	public static void main(String[] args)
	{
		// Date date=DateUtils.getDate();
		// System.out.println(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));

		String title = "33update$%UPdatef<dedelr$efedropddr{rDEL(derr>=";
		// title=Util.IgnoreCaseReplace.replace("update", "").IgnoreCaseReplace("del",
		// "").replaceAll("drop", "").replace("insert", "").replaceAll("\\(", "")
		//// .replaceAll("\\)", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("select",
		// "").replaceAll("\\{", "").
		// .replaceAll("\\{", "").replaceAll("\\$", "");
		title = title.replace("<", "").replace(">", "").replace("=", "").replaceAll("\\{", "")
				.replaceAll("\\}", "").replaceAll("\\(", "").replaceAll("\\)", "")
				.replaceAll("\\$", "").replaceAll("\\%", "");
		title = Util.IgnoreCaseReplace(title, "update", "");
		title = Util.IgnoreCaseReplace(title, "delete", "");
		title = Util.IgnoreCaseReplace(title, "drop", "");
		title = Util.IgnoreCaseReplace(title, "insert", "");
		title = Util.IgnoreCaseReplace(title, "select", "");
		title = Util.IgnoreCaseReplace(title, "create", "");
		title = Util.IgnoreCaseReplace(title, "select", "");
		title = Util.IgnoreCaseReplace(title, "table", "");
		title = Util.IgnoreCaseReplace(title, "alert", "");
		System.out.println(title);
	}

}
