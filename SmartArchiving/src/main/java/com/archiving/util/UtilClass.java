package com.archiving.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FilenameUtils;
//import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.fileupload.FileItem;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.archiving.config.SpringContextHolder;
import com.archiving.config.SpringEnvironmentHolder;
import com.archiving.util.dao.mapper.UtilLegacyMapper;
import com.archiving.util.dto.InsaOptionRowDto;
import com.archiving.util.dto.ScheduleColRowDto;
import com.archiving.util.dto.ScheduleIniRowDto;
import com.archiving.util.dto.ScheduleJobListRowDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilClass {

	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");
	//private Logger logger = LogManager.getLogger(this.getClass().getName() + ".class");
	//private final Logger logger = LogManager.getLogger(this.getClass().getName() + ".class");

	
	public String key = "MagicAriaA1ck0123456789012345678";

	Aria aria = new Aria(key);

	public static String alg = "AES/CBC/PKCS5Padding";
	private final String iv = key.substring(0, 16); // 16byte
	
	/* AES(CBC/PKCS5Padding)로 문자열 암호화(Base64 반환) */
	public String encrypt(String text) throws Exception {
		
	        Cipher cipher = Cipher.getInstance(alg);
	        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
	        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
	        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));	    
	        return Base64.getEncoder().encodeToString(encrypted);
	 }

	/* AES(CBC/PKCS5Padding)로 문자열 복호화(Base64 입력) */
	 public String decrypt(String cipherText) throws Exception {
		 
	        Cipher cipher = Cipher.getInstance(alg);
	        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
	        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

	        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
	        byte[] decrypted = cipher.doFinal(decodedBytes);
	        return new String(decrypted, "UTF-8");
	}
	 
	/* Spring Environment에서 설정값 조회(server.properties/db.properties/dirs/quartz.properties 호환) */
	public String GetCommProperty(String sPropertyFile, String sSection, String sProperty) 
	{
		
		try {
			Environment env = SpringEnvironmentHolder.getEnvironment();
			if (env == null) {
				throw new IllegalStateException("Spring Environment not initialized");
			}

			String sectionKey = (sSection == null) ? "" : sSection.toLowerCase();

			if ("server.properties".equalsIgnoreCase(sPropertyFile)) {
				if ("cryptedServer".equalsIgnoreCase(sProperty)) {
					return env.getProperty("app.server.crypted-server");
				}
				return null;
			}

			if (!"db.properties".equalsIgnoreCase(sPropertyFile)) {
				return null;
			}

			// Legacy: [DIRS] upload_dir*  -> app.upload.*
			if ("dirs".equals(sectionKey)) {
				if ("upload_dir".equalsIgnoreCase(sProperty)) return env.getProperty("app.upload.dir");
				if ("upload_dir2".equalsIgnoreCase(sProperty)) return env.getProperty("app.upload.dir2");
				if ("upload_dir_excel".equalsIgnoreCase(sProperty)) return env.getProperty("app.upload.dir-excel");
				if ("upload_dir_notice".equalsIgnoreCase(sProperty)) return env.getProperty("app.upload.dir-notice");
				return null;
			}

			// Legacy: db.properties keys -> app.db.* mapping
			String mappedKey;
			if ("dbServer".equalsIgnoreCase(sProperty)) mappedKey = "host";
			else if ("port".equalsIgnoreCase(sProperty)) mappedKey = "port";
			else if ("dbName".equalsIgnoreCase(sProperty)) mappedKey = "db-name";
			else if ("userID".equalsIgnoreCase(sProperty)) mappedKey = "username";
			else if ("passwd".equalsIgnoreCase(sProperty)) mappedKey = "password";
			else if ("maxConn".equalsIgnoreCase(sProperty)) mappedKey = "max-pool-size";
			else if ("initConn".equalsIgnoreCase(sProperty)) mappedKey = "min-idle";
			else if ("maxWait".equalsIgnoreCase(sProperty)) mappedKey = "connection-timeout";
			else mappedKey = sProperty;

			return env.getProperty("app.db." + sectionKey + "." + mappedKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String GetCommSimpleProperty(String sPropertyFile, String sProperty) 
	{
		try {
			Environment env = SpringEnvironmentHolder.getEnvironment();
			if (env == null) {
				throw new IllegalStateException("Spring Environment not initialized");
			}

			if ("server.properties".equalsIgnoreCase(sPropertyFile)) {
				if ("cryptedServer".equalsIgnoreCase(sProperty)) {
					String value = env.getProperty("app.server.crypted-server");
					return value == null ? "" : value;
				}
				return "";
			}

			if ("quartz.properties".equalsIgnoreCase(sPropertyFile)) {
				String value = env.getProperty("spring.quartz.properties." + sProperty);
				if (value == null) {
					value = env.getProperty("magic.quartz." + sProperty);
				}
				return (value == null) ? "" : value;
			}

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
    public String getCommRootProperty(String sProperty) {
    	
        ClassLoader cl;
        cl = Thread.currentThread().getContextClassLoader();
        
        String envPath = null;
        String fileSeparator = System.getProperty("file.separator");
//        logger.debug("getCommRootProperty :: fileSeparator:" + fileSeparator );

        if( cl == null ) {
            cl = ClassLoader.getSystemClassLoader();
        }
        
        URL url = cl.getResource( sProperty );
        if (fileSeparator.equals("/")) {
        	envPath = url.getPath().substring(0, url.getPath().length());
        } else {
        	envPath = url.getPath().substring(1, url.getPath().length());
        }
        
        return envPath;
    }

    public boolean commandExecute(String commandPath) {
    	
    	   boolean ret = false;
    	   
    	   List<String> realCommand = new ArrayList<String>();
    	   realCommand.add("sh");
    	   realCommand.add("-c");
    	   realCommand.add(commandPath);
    	   realCommand.add("&"); //background
    	   
    	   ProcessBuilder processBuilder = new ProcessBuilder();
    	   processBuilder.redirectErrorStream(true);
           processBuilder.command(realCommand);
           
           Process process = null;
           StringBuilder output = new StringBuilder(1024);
           
           
           logger.debug("########## ProcessBuilder commandPath :" + commandPath);
           try {
        	   // Run script
               process = processBuilder.start();

               // Read output
               BufferedReader reader = new BufferedReader( new InputStreamReader(process.getErrorStream()));
               String line;
               while ((line = reader.readLine()) != null) {
            	   output.append(line);
               }
               logger.debug(output.toString());
               logger.debug("########## execute End:" );
               
               process.getErrorStream().close();
               process.getInputStream().close();
               process.getOutputStream().close();
               
               process.waitFor();
               reader.close();
 
               ret = true;
           } catch (Exception e) {
        	   logger.debug("########## exception:" + e.getMessage());
               e.printStackTrace();
           }finally {
			if(process != null) {
				try {
					process.destroy();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
           
           return ret;
    }
    public boolean getProcInfo(String processName)
    {
	    	String line;
	    	boolean ret = false;
	        try {
	          // Process p = Runtime.getRuntime().exec("ps -ef | grep java | grep -v grep");
	          Process p = Runtime.getRuntime().exec(new String[] {"sh", "-c", "ps -ef | grep java | grep -v grep |grep " + processName});
	          BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	          while ((line = input.readLine()) != null) {
	            System.out.println("####### line :"+line + " processName : " + processName);
	            //if(processName.equals(line)) {
	            	ret = true;
	            	//break;
	            //}
	          }
	          
	          input.close();
	          p.destroy();
	          
	        } catch (Exception err) {
	          System.out.println(err);
	        }
	        
	        return ret;
    }    
	public String GetManagerProperty(String section, String property) throws Exception {

		// System.out.println("UtilClass:: GetManagerConnection" );

		try {
			Environment env = SpringEnvironmentHolder.getEnvironment();
			if (env == null) {
				throw new IllegalStateException("Spring Environment not initialized");
			}
			String sectionKey = (section == null) ? "" : section.toLowerCase();

			// Legacy: [DIRS] upload_dir* -> app.upload.*
			if ("dirs".equals(sectionKey)) {
				if ("upload_dir".equalsIgnoreCase(property)) return env.getProperty("app.upload.dir");
				if ("upload_dir2".equalsIgnoreCase(property)) return env.getProperty("app.upload.dir2");
				if ("upload_dir_excel".equalsIgnoreCase(property)) return env.getProperty("app.upload.dir-excel");
				if ("upload_dir_notice".equalsIgnoreCase(property)) return env.getProperty("app.upload.dir-notice");
				return null;
			}

			// Reuse the same mapping rules as GetCommProperty for db.properties
			String mappedKey;
			if ("dbServer".equalsIgnoreCase(property)) mappedKey = "host";
			else if ("port".equalsIgnoreCase(property)) mappedKey = "port";
			else if ("dbName".equalsIgnoreCase(property)) mappedKey = "db-name";
			else if ("userID".equalsIgnoreCase(property)) mappedKey = "username";
			else if ("passwd".equalsIgnoreCase(property)) mappedKey = "password";
			else if ("maxConn".equalsIgnoreCase(property)) mappedKey = "max-pool-size";
			else if ("initConn".equalsIgnoreCase(property)) mappedKey = "min-idle";
			else if ("maxWait".equalsIgnoreCase(property)) mappedKey = "connection-timeout";
			else mappedKey = property;

			return env.getProperty("app.db." + sectionKey + "." + mappedKey);
		} catch (Exception e) {
			logger.error("GetManagerConnection ::" + e.getMessage());
			e.printStackTrace(); // TRACE();
			throw e;
		}

	}

	public String getCurrentTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String sCurrTime = dayTime.format(new Date(time));

		//// logger.debug("Current Date: " + sCurrTime);
		// System.out.println("Current Date: " + sCurrTime);

		return sCurrTime;
	}

	public String getCurrentTime_Dash() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yy/MM/dd/ HH:mm:ss");
		String sCurrTime = dayTime.format(new Date(time));

		//// logger.debug("Current Date: " + sCurrTime);
		// System.out.println("Current Date: " + sCurrTime);

		return sCurrTime;
	}
	public String getCurrentTime_Publish() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sCurrTime = dayTime.format(new Date(time));

		return sCurrTime;
	}
	public String getDate(int nDay, String sDash) {
		Calendar cal = Calendar.getInstance();
		String sDay = null;

		cal.add(Calendar.DATE, nDay);
		String sYear = Integer.toString(cal.get(Calendar.YEAR));
		String sMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String sDate = Integer.toString(cal.get(Calendar.DATE));

		if (sMonth.length() == 1)
			sMonth = "0" + sMonth;
		if (sDate.length() == 1)
			sDate = "0" + sDate;

		if (!sDash.equals(""))
			sDay = sYear + sDash + sMonth + sDash + sDate;
		else
			sDay = sYear + sMonth + sDate;
		return sDay;
	}

	public String getStringRight(String sSrc, String sDash, int nLen) {

		String sString = sDash + sSrc;
		// logger.debug("sString :" + sString);
		return sString.substring(sString.length() - nLen, sString.length());
	}

	/*
	 * 2017-02-10 => 20170210
	 */
	public String getDate2String(String sDash) {
		String sDay = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dDate;
		try {
			dDate = format.parse(sDash);
			format = new SimpleDateFormat("yyyyMMdd");
			sDay = format.format(dDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sDay;
	}

	/*
	 * 20170210 => 2017-02-10
	 */
	public String getString2Date(String sDate) {
		String sDash = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date dDate;
		try {
			dDate = format.parse(sDate);
			format = new SimpleDateFormat("yyyy-MM-dd");
			sDash = format.format(dDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sDash;
	}

	public static String nvl(String val) {
		return val == null ? "" : val;
	}

	public String nvl2zero(String val) {
		return nvl(val, "0");
	}

	public String nvl(String val, String rep_val) {
		if (val == null) {
			if (null == rep_val) {
				val = "";
			} else {
				val = rep_val;
			}
		}
		return val;
	}

	public String nvl_trim(String val) {
		return nvl(val).trim();
	}

	public String nvl_trim(String val, String rep_val) {
		return nvl(val, rep_val).trim();
	}

	public String replace(String source, String pattern, String replace) {
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
			}

			sb.append(source.substring(start));

			return sb.toString();
		}

		return "";
	}

	public String getRight(String str, int size) {
		int tmpStringLength = str.length();

		if (size >= tmpStringLength) {
			return str;
		}

		return str.substring(tmpStringLength - size, str.length());
	}

	public String getLeft(String str, int size) {
		int tmpStringLength = str.length();

		if (size >= tmpStringLength) {
			return str;
		}

		return str.substring(0, size);
	}

	public String setLeftPad(String str, int width, char chPad) {
		StringBuffer paddedValue = new StringBuffer();

		for (int i = str.length(); i < width; i++) {
			paddedValue.append(chPad);
		}

		paddedValue.append(str);

		String returnValue = paddedValue.toString();
		returnValue = returnValue.substring(0, width);

		return returnValue;
	}

	public String setRightPad(String str, int width, char chPad) {
		if (str.length() >= width) {
			return str.substring(0, width);
		}

		StringBuffer paddingValue = new StringBuffer();
		for (int i = str.length(); i < width; i++) {
			paddingValue.append(chPad);
		}

		return str + paddingValue.toString();
	}

	public boolean isPattern(String sData, String sPattern) {
		for (int i = 0; i < sData.length(); i++) {
			if (sPattern.indexOf(sData.charAt(i)) < 0) {
				return false;
			}
		}
		return true;
	}

	public byte[] hexFromString(String hex) {
		int len = hex.length();
		int bufLen = (len + 1) / 2;
		byte[] buf = new byte[bufLen];

		int i = 0, j = 0;
		i = len % 2;
		if (i == 1) {
			buf[j++] = (byte) fromDigit(hex.charAt(i++));
		}
		while (i < len) {
			buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) | fromDigit(hex.charAt(i++)));
		}
		return buf;
	}

	public int fromDigit(char ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		}
		if (ch >= 'A' && ch <= 'F') {
			return ch - 'A' + 10;
		}
		if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		}

		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}

	/**
	 * Reads the contents of the given URL and returns it as a string.
	 * 
	 * @param url
	 * @return
	 */
	public String urlToString(URL url) throws IOException {
		StringBuffer sb = new StringBuffer("");
		InputStream is = url.openStream();
		int n = 0;
		do {
			n = is.read();
			if (n >= 0) {
				sb.append((char) n);
			}
		} while (n >= 0);
		is.close();
		return sb.toString();
	}

	public String makeRandomDigit() {
		String rtn = null;
		try {
			char[] retChar = new char[5];
			for (int i = 0; i < retChar.length; i++) {
				double randomDigit = Math.random() * (double) 25.0 % 25.0;
				retChar[i] = (char) (randomDigit + 65);
				if ((char) retChar[i] < 'A' || (char) retChar[i] > 'Z') {
					throw new Exception("Unexpected text : " + retChar[i]);
				}
			}
			rtn = new String(retChar);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return "";
		}
		return rtn;
	}

	public String readClob(String fileName, Writer writerArg) throws FileNotFoundException, IOException {

		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String nextLine = "";
		StringBuffer sb = new StringBuffer();
		while ((nextLine = br.readLine()) != null) {
			System.out.println("Writing: " + nextLine);
			writerArg.write(nextLine);
			sb.append(nextLine);
		}
		// Convert the content into to a string
		String clobData = sb.toString();

		// Return the data.
		return clobData;
	}

	public String encryptSHA256(String str) {
		String sha = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("Encrypt Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}

	public static boolean isGreat(String baseStr, String compareStr) {
		baseStr = nvl(baseStr);
		compareStr = nvl(compareStr);

		int compare = baseStr.compareTo(compareStr);

		if (compare > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGreatOrEqual(String baseStr, String compareStr) {
		baseStr = nvl(baseStr);
		compareStr = nvl(compareStr);

		if (isGreat(baseStr, compareStr) || baseStr.equals(compareStr)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSmall(String baseStr, String compareStr) {
		return !isGreat(baseStr, compareStr) && !nvl(baseStr).equals(compareStr);
	}

	public static boolean isSmallOrEqual(String baseStr, String compareStr) {
		baseStr = nvl(baseStr);
		compareStr = nvl(compareStr);

		if (isSmall(baseStr, compareStr) || baseStr.equals(compareStr)) {
			return true;
		} else {
			return false;
		}
	}

	public class ClassSchedule {
		String job_cd;
		String job_nm;
		String job_schedule;

		public String getJob_cd() {
			return job_cd;
		}

		public void setJob_cd(String job_cd) {
			this.job_cd = job_cd;
		}

		public String getJob_nm() {
			return job_nm;
		}

		public void setJob_nm(String job_nm) {
			this.job_nm = job_nm;
		}

		public String getJob_schedule() {
			return job_schedule;
		}

		public void setJob_schedule(String job_schedule) {
			this.job_schedule = job_schedule;
		}
	}

	public class ClassIni {
		String job_id;
		String job_schedule;

		String src_ip;
		String src_port;
		String src_dbms_cd;
		String src_dbms;
		String src_db;
		String src_user;
		String src_passwd;
		String src_sql;

		String dest_ip;
		String dest_port;
		String dest_dbms_cd;
		String dest_dbms;
		String dest_db;
		String dest_table;
		String dest_user;
		String dest_passwd;

		String job_method_cd;
		String save_preq_cd;
		String save_preq;

		String job_type_cd;
		String job_class;
		String job_path;
		String job_selected;

		String use_yn;

		public String getJob_id() {
			return job_id;
		}

		public void setJob_id(String job_id) {
			this.job_id = job_id;
		}

		public String getJob_schedule() {
			return job_schedule;
		}

		public void setJob_schedule(String job_schedule) {
			this.job_schedule = job_schedule;
		}

		public String getSrc_ip() {
			return src_ip;
		}

		public void setSrc_ip(String src_ip) {
			this.src_ip = src_ip;
		}

		public String getSrc_port() {
			return src_port;
		}

		public void setSrc_port(String src_port) {
			this.src_port = src_port;
		}

		public String getSrc_dbms_cd() {
			return src_dbms_cd;
		}

		public void setSrc_dbms_cd(String src_dbms_cd) {
			this.src_dbms_cd = src_dbms_cd;
		}

		public String getSrc_dbms() {
			return src_dbms;
		}

		public void setSrc_dbms(String src_dbms) {
			this.src_dbms = src_dbms;
		}

		public String getSrc_db() {
			return src_db;
		}

		public void setSrc_db(String src_db) {
			this.src_db = src_db;
		}

		public String getSrc_user() {
			return src_user;
		}

		public void setSrc_user(String src_user) {
			this.src_user = src_user;
		}

		public String getSrc_passwd() {
			return src_passwd;
		}

		public void setSrc_passwd(String src_passwd) {
			this.src_passwd = src_passwd;
		}

		public String getSrc_sql() {
			return src_sql;
		}

		public void setSrc_sql(String src_sql) {
			this.src_sql = src_sql;
		}

		public String getDest_ip() {
			return dest_ip;
		}

		public void setDest_ip(String dest_ip) {
			this.dest_ip = dest_ip;
		}

		public String getDest_port() {
			return dest_port;
		}

		public void setDest_port(String dest_port) {
			this.dest_port = dest_port;
		}

		public String getDest_dbms_cd() {
			return dest_dbms_cd;
		}

		public void setDest_dbms_cd(String dest_dbms_cd) {
			this.dest_dbms_cd = dest_dbms_cd;
		}

		public String getDest_dbms() {
			return dest_dbms;
		}

		public void setDest_dbms(String dest_dbms) {
			this.dest_dbms = dest_dbms;
		}

		public String getDest_db() {
			return dest_db;
		}

		public void setDest_db(String dest_db) {
			this.dest_db = dest_db;
		}

		public String getDest_table() {
			return dest_table;
		}

		public void setDest_table(String dest_table) {
			this.dest_table = dest_table;
		}

		public String getDest_user() {
			return dest_user;
		}

		public void setDest_user(String dest_user) {
			this.dest_user = dest_user;
		}

		public String getDest_passwd() {
			return dest_passwd;
		}

		public void setDest_passwd(String dest_passwd) {
			this.dest_passwd = dest_passwd;
		}

		public String getJob_method_cd() {
			return job_method_cd;
		}

		public void setJob_method_cd(String job_method_cd) {
			this.job_method_cd = job_method_cd;
		}

		public String getSave_preq_cd() {
			return save_preq_cd;
		}

		public void setSave_preq_cd(String save_preq_cd) {
			this.save_preq_cd = save_preq_cd;
		}

		public String getSave_preq() {
			return save_preq;
		}

		public void setSave_preq(String save_preq) {
			this.save_preq = save_preq;
		}

		public String getJob_type_cd() {
			return job_type_cd;
		}

		public void setJob_type_cd(String job_type_cd) {
			this.job_type_cd = job_type_cd;
		}

		public String getJob_class() {
			return job_class;
		}

		public void setJob_class(String job_class) {
			this.job_class = job_class;
		}

		public String getJob_path() {
			return job_path;
		}

		public void setJob_path(String job_path) {
			this.job_path = job_path;
		}

		public String getJob_selected() {
			return job_selected;
		}

		public void setJob_selected(String job_selected) {
			this.job_selected = job_selected;
		}

		public String getUse_yn() {
			return use_yn;
		}

		public void setUse_yn(String use_yn) {
			this.use_yn = use_yn;
		}

	}

	public class ClassIniCol {
		int col_seq;
		String col_cd;
		String col_nm;
		String col_type;
		int col_len;
		String col_date;
		int codeidx;
		String col_enc;

		public int getCol_seq() {
			return col_seq;
		}

		public void setCol_seq(int col_seq) {
			this.col_seq = col_seq;
		}

		public String getCol_cd() {
			return col_cd;
		}

		public void setCol_cd(String col_cd) {
			this.col_cd = col_cd;
		}

		public String getCol_nm() {
			return col_nm;
		}

		public void setCol_nm(String col_nm) {
			this.col_nm = col_nm;
		}

		public String getCol_type() {
			return col_type;
		}

		public void setCol_type(String col_type) {
			this.col_type = col_type;
		}

		public int getCol_len() {
			return col_len;
		}

		public void setCol_len(int col_len) {
			this.col_len = col_len;
		}

		public String getCol_date() {
			return col_date;
		}

		public void setCol_date(String col_date) {
			this.col_date = col_date;
		}

		public int getCodeidx() {
			return codeidx;
		}

		public void setCodeidx(int codeidx) {
			this.codeidx = codeidx;
		}

		public String getCol_enc() {
			return col_enc;
		}

		public void setCol_enc(String col_enc) {
			this.col_enc = col_enc;
		}
	}

	private UtilLegacyMapper utilLegacyMapper() {
		return SpringContextHolder.getBean(UtilLegacyMapper.class);
	}

	private void applyScheduleRowToClassIni(ScheduleIniRowDto r, ClassIni c) {
		if (r == null || c == null) {
			return;
		}
		c.setJob_id(r.getJobId());
		c.setJob_schedule(r.getJobSchedule());
		c.setSrc_ip(r.getSrcIp());
		c.setSrc_port(r.getSrcPort());
		c.setSrc_dbms_cd(r.getSrcDbmsCd());
		c.setSrc_dbms(r.getSrcDbms());
		c.setSrc_db(r.getSrcDb());
		c.setSrc_user(r.getSrcUser());
		c.setSrc_passwd(r.getSrcPasswd());
		c.setSrc_sql(r.getSrcSql());
		c.setUse_yn(r.getUseYn());
		c.setDest_ip(r.getDestIp());
		c.setDest_port(r.getDestPort());
		c.setDest_dbms_cd(r.getDestDbmsCd());
		c.setDest_dbms(r.getDestDbms());
		c.setDest_db(r.getDestDb());
		c.setDest_user(r.getDestUser());
		c.setDest_passwd(r.getDestPasswd());
		c.setJob_method_cd(r.getJobMethodCd());
		c.setDest_table(r.getDestTable());
		c.setSave_preq_cd(r.getSavePreqCd());
		c.setSave_preq(r.getSavePreq());
		c.setJob_type_cd(r.getJobTypeCd());
		c.setJob_class(r.getJobClass());
		c.setJob_path(r.getJobPath());
		c.setJob_selected(r.getJobSelected());
	}

	private static String buildInsaOptionsString(java.util.List<InsaOptionRowDto> rows) {
		StringBuilder sb = new StringBuilder();
		if (rows != null) {
			for (InsaOptionRowDto row : rows) {
				if (row == null) {
					continue;
				}
				sb.append("<option value='").append(row.getEno()).append("'>");
				sb.append(row.getName()).append("/").append(row.getOft()).append("/").append(row.getPzcnm()).append("/").append(row.getBrnm());
				sb.append("</option>");
			}
		}
		ArrayList<String> resultList = new ArrayList<>();
		resultList.add(sb.toString());
		return resultList.toString();
	}

	public ClassIni getConfIni(String sClassName) {
		ClassIni classIni = new ClassIni();
		UtilLegacyMapper m = utilLegacyMapper();
		if (m != null) {
			try {
				for (ScheduleIniRowDto row : m.selectScheduleIniByJobCd(sClassName)) {
					applyScheduleRowToClassIni(row, classIni);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return classIni;
		}
		return classIni;
	}
	
	public ClassIni getIni(String sClass) {
		return getConfIni(sClass);
	}

	public ArrayList<ClassSchedule> getIniSchedule() {
		ArrayList<ClassSchedule> arrayList = new ArrayList<ClassSchedule>();
		UtilLegacyMapper m = utilLegacyMapper();
		if (m == null) {
			return arrayList;
		}
		try {
			for (ScheduleJobListRowDto row : m.selectScheduleJobsForCron()) {
				ClassSchedule classSchedule = new ClassSchedule();
				classSchedule.setJob_cd(row.getJobCd());
				classSchedule.setJob_nm(row.getJobNm());
				classSchedule.setJob_schedule(row.getJobSchedule());
				arrayList.add(classSchedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	public ArrayList<ClassIniCol> getIniCol(String sClass) {
		ArrayList<ClassIniCol> arrayList = new ArrayList<ClassIniCol>();
		UtilLegacyMapper map = utilLegacyMapper();
		if (map == null) {
			return arrayList;
		}
		try {
			for (ScheduleColRowDto row : map.selectScheduleColsByJobCd(sClass)) {
				ClassIniCol classIniCol = new ClassIniCol();
				classIniCol.setCol_seq(row.getColSeq());
				classIniCol.setCol_cd(row.getColCd());
				classIniCol.setCol_nm(row.getColNm());
				classIniCol.setCol_type(row.getColTypeCd());
				classIniCol.setCol_len(row.getColLen());
				classIniCol.setCol_date(row.getColDateYn());
				classIniCol.setCol_enc(row.getColEncYn());
				arrayList.add(classIniCol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	public String getUserProperty(String sProperty) {

		ClassLoader cl;
		cl = Thread.currentThread().getContextClassLoader();
		String envPath = null;

		String fileSeparator = System.getProperty("file.separator");
		/***
		 * System.out.println("==>fileSeparator:"+fileSeparator+":"+fileSeparator.substring(0,
		 * 1)); String javaHome = System.getenv("JAVA_HOME");
		 * System.out.println("==>javaHome:"+javaHome); String osName =
		 * System.getProperty("os.name"); System.out.println("==>osName:"+osName);
		 * String userDir = System.getProperty("user.dir");
		 * System.out.println("==>userDir:"+userDir);
		 ***/

		if (cl == null)
			cl = ClassLoader.getSystemClassLoader();

		URL url = cl.getResource(sProperty);
		// System.out.println("url:"+ url.toString());

		if (fileSeparator.equals("/")) {
			// System.out.println("==>fileSeparator1:"+fileSeparator);
			envPath = url.getPath().substring(0, url.getPath().length());
		} else {
			// System.out.println("==>fileSeparator2:"+fileSeparator);
			envPath = url.getPath().substring(1, url.getPath().length());
		}
		// System.out.println("==>envPath:"+envPath);

		return envPath;
	}

	public String getUserRootProperty(String sProperty) {

		ClassLoader cl;
		cl = Thread.currentThread().getContextClassLoader();
		String envPath = null;

		// System.out.println("UtilClass:: sProperty:"+ sProperty);

		String fileSeparator = System.getProperty("file.separator");
		/*
		 * System.out.println("==>fileSeparator:"+fileSeparator+":"+fileSeparator.
		 * substring(0, 1));
		 * 
		 * String javaHome = System.getenv("JAVA_HOME");
		 * System.out.println("==>javaHome:"+javaHome);
		 * 
		 * String osName = System.getProperty("os.name");
		 * System.out.println("==>osName:"+osName);
		 * 
		 * String userDir = System.getProperty("user.dir");
		 * System.out.println("==>userDir:"+userDir);
		 */

		if (cl == null)
			cl = ClassLoader.getSystemClassLoader();

		URL url = cl.getResource(sProperty);
		// logger.debug("url:"+ url.toString());
		// System.out.println("UtilClass:: url:"+ url.toString());

		if (fileSeparator.equals("/")) {
			// System.out.println("==>fileSeparator1:"+fileSeparator);
			envPath = url.getPath().substring(0, url.getPath().length());
		} else {
			// System.out.println("==>fileSeparator2:"+fileSeparator);
			envPath = url.getPath().substring(1, url.getPath().length());
		}
		// System.out.println("==>envPath:"+envPath);

		return envPath;
	}

	/**
	 * �뙆�씪 �씠由� 異붿텧 �뙆�씪�씠 �엳�뒗吏� 以묐났�뿬遺� 泥댄겕
	 * 
	 * �뙆�씪 �뾽濡쒕뱶 �굹癒몄� �뿕由щ㉫�듃 Map�뿉 ���옣
	 * 
	 * @param iter
	 * @param fMidir
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap getFileUpload(Iterator iter, File fMidir) {

		logger.debug("getFileUpload in ..... logger.debug");

		HashMap file_map = new HashMap();
		try {
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {// �뙆�씪�씠 �븘�땶寃쎌슦
					String key = item.getFieldName();
					String value = item.getString("utf-8");// �븳湲� 爰좎��뒗嫄� 諛⑹� utf-8濡� �옱�꽕�젙
					 System.out.println("key=" + key + " value=" + value);
					file_map.put(key, value);// HashMap �뿉 �떞�뒗�떎
				} else {// �뙆�씪�씤 寃쎌슦 �뿬湲곗꽑 以묐났 濡쒖쭅 泥섎━�룄 諛섎벏�씠 �빐以��떎............
					// String fileName = new String(item.getName().getBytes("8859_1"),"euc-kr");//�븳湲�
					// 怨꾩냽 源⑥쭚
					String originalFileName = item.getName();// �븳湲� 怨꾩냽 源⑥쭚
					logger.debug("getFileUpload original fileName:" + originalFileName);

					String extension = FilenameUtils.getExtension(originalFileName);
					String filenameonly = FilenameUtils.getBaseName(originalFileName);
					logger.debug("getFileUpload filenameonly:" + filenameonly);
					logger.debug("getFileUpload extension:" + extension);
					
					String uniqueFileName = UUID.randomUUID().toString() + "." + extension;

					if (!originalFileName.equals("") && originalFileName != null) {// �뙆�씪�씠由꾩씠 議댁옱�븯怨� �뾽濡쒕뱶�븷 �뙆�씪�씠 議댁옱�븳�떎硫� �뾽濡쒕뱶 �떆�옉

						int idx = originalFileName.lastIndexOf("\\"); // �쐢�룄�슦�쓽 寃쎌슦, c:\ 源뚯� �뼸湲�
						if (idx == -1) {
							idx = originalFileName.lastIndexOf("/"); // �쑀�땳�뒪(由щ늼�뒪)�쓽 寃쎌슦
						}
						originalFileName = originalFileName.substring(idx + 1);
						//String tempfileName = originalFileName;
						
						// ++++++++++++++�뙆�씪以묐났濡쒖쭅 �떆�옉++++++++++++++
						File save_file = new File(fMidir.getPath(), uniqueFileName);
						//if (save_file.exists()) {// �뙆�씪�씠 議댁옱�븳�떎硫�
						//	for (int i = 1; true; i++) {
						//		fileName = filenameonly + "_" + i + "." + extension;
						//		save_file = new File(fMidir.getPath(), fileName);
						//		if (!save_file.exists()) {
									// fileName = i + "_"+ fileName;
						//			break;
						//		} else {
						//			fileName = tempfileName;
						//		}
						//	}
						//}
						file_map.put("originalFilename", originalFileName);
						file_map.put("uniqueFilename", uniqueFileName);
						//fMidir = new File(fMidir.getPath(), uniqueFileName);
						
						save_file.getParentFile().mkdir();
						try (FileOutputStream fos = new FileOutputStream( save_file);
							OutputStreamWriter osw = new OutputStreamWriter( fos, StandardCharsets.UTF_8);
							BufferedWriter writer = new BufferedWriter( osw)){
							String content = item.getString("utf-8");
							logger.debug("item content : " +content);
							writer.write(content);
						}catch( Exception e) {
							logger.debug("Error : " + e.getMessage());
						}
						//item.write(fMidir);
						// ++++++++++++++�뙆�씪以묐났濡쒖쭅 醫낅즺++++++++++++++
					} else {
						file_map.put("originalFilename", null);
					} // End Of FileName if
				}
			}
		} catch (Exception e) {
			logger.debug("Error : " + e.getMessage());
		} finally {
		}
		return file_map;
	}

	public String getDbMsg(String sCode) {
		UtilLegacyMapper map = utilLegacyMapper();
		if (map != null) {
			try {
				String msg = map.selectPostgresErrorMessage(sCode);
				if (msg != null && !msg.isEmpty()) {
					return msg;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Not Found";
	}

    public int SelectCountData(String sQuery) {
    	JdbcTemplate jt = SpringContextHolder.getPrimaryJdbcTemplate();
    	if (jt != null) {
    		try {
    			logger.debug("sQuery : " + sQuery);
    			Integer cnt = jt.query(sQuery, (ResultSetExtractor<Integer>) rs -> {
    				if (rs.next()) {
    					return Integer.parseInt(rs.getString("CNT"));
    				}
    				return -1;
    			});
    			return cnt != null ? cnt : -1;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return -1;
    		}
    	}
    	return -1;
    }
    
    //20241021 : 사용재 행위 로그 저장
    public String setLogSave(HashMap<String, String> hashMap) throws SQLException {

		String method = "";
		String groupId = "";
		String userId = "";
		String userCd = "";
		String userName = "";
		String programId = "";
		String programNm = "";
		String addr = "";
		String programWhere = "";
		String menuUrl = "";
		Map<String , Object> chkCrud = null;

		UtilLegacyMapper map = utilLegacyMapper();
		if (map == null) {
			return null;
		}

		try {

			method 		= hashMap.get("method");
			groupId 	= hashMap.get("groupId");
			userId 		= hashMap.get("userId");
			userCd 		= hashMap.get("userCd");
			userName	= hashMap.get("userName");
			programId 	= hashMap.get("programId") ;
			addr 		= hashMap.get("addr");
			programWhere= hashMap.get("programWhere");
			menuUrl     = hashMap.get("menuUrl");

			if(programWhere != null){
				ObjectMapper mapper = new ObjectMapper();
//	    		chkCrud = mapper.readValue(programWhere, Map.class);
	    		try {
	    	        String trimmed = programWhere.trim();
	    	        if (trimmed.startsWith("{")) {
	    	            // {"crud":"u"} 형태 → Map으로 파싱
	    	            chkCrud = mapper.readValue(trimmed, Map.class);
	    	        } else if (trimmed.startsWith("[")) {
	    	            // [] 또는 [{"user_id":"90001"}] 형태 → List로 파싱 후 첫 번째 요소 사용
	    	            List<Map<String, Object>> list = mapper.readValue(
	    	                trimmed, 
	    	                new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {}
	    	            );
	    	            if (list != null && !list.isEmpty()) {
	    	                chkCrud = list.get(0); // 첫 번째 요소를 Map으로 사용
	    	            }
	    	            // list가 비어있으면 chkCrud = null 유지
	    	        }
	    	    } catch (Exception e) {
	    	        logger.debug("programWhere 파싱 실패, 무시합니다: " + programWhere);
	    	    }
			}

			programNm = map.selectMenuNmByUrlLike(menuUrl);

			if(chkCrud != null && programId != null) {
				if(programNm != null) {
					programNm += " - ";
				} else {
					programNm = "";
				}
				
				if(programId.toLowerCase().indexOf("get") > -1 && !chkCrud.containsKey("crud")) {
					programNm += "조회";
				}
				else if(programId.toLowerCase().indexOf("set") > -1) {
					if (chkCrud.get("crud") != null ) {
						if("u".equals(chkCrud.get("crud").toString().toLowerCase())) {
							programNm += "변경";
						}else if("c".equals(chkCrud.get("crud").toString().toLowerCase())) {
							programNm += "추가";
						}else if("d".equals(chkCrud.get("crud").toString().toLowerCase())) {
							programNm += "삭제";
						}else if("up".equals(chkCrud.get("crud").toString().toLowerCase())) {
							programNm += "엑셀 업로드";
						}else if("ap".equals(chkCrud.get("crud").toString().toLowerCase())) {
							programNm += "신청";
						}
					}
				}
			}

			Integer gid = parseIntOrNull(groupId);
			Integer uid = parseIntOrNull(userId);

			logger.debug("setLogSave programId=" + programId + " programNm=" + programNm);

			map.insertUserLog(gid, uid, userCd, nvl_trim(programWhere), userName, addr, programId, programNm, method);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Integer parseIntOrNull(String s) {
		if (s == null || s.trim().isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
    
    //20241113 : 상위 결재자 검색(팀장,차장,과장급)
    public String searchUserLine(String user_cd) {
		UtilLegacyMapper map = utilLegacyMapper();
		if (map == null) {
			return "[]";
		}
		try {
			return buildInsaOptionsString(map.selectInsaTeamLeadLine(user_cd));
		} catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
    }

    //20250221 : 상위 결재자 검색(팀장,차장,과장급)  1차 승인자  ==> 1차결재: 4급 혹은 기획역(3급 책임자)
    public String searchUserFirstLine(String user_cd) {
		UtilLegacyMapper map = utilLegacyMapper();
		if (map == null) {
			return "[]";
		}
		try {
			return buildInsaOptionsString(map.selectInsaFirstApprovalLine(user_cd));
		} catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
    }

    //20250221 : 상위 결재자 검색(팀장,차장,과장급)  1차 승인자  ==> 2차결재: 3급    WHERE PZCC = '21'
    public String searchUserSecondLine(String user_cd) {
		UtilLegacyMapper map = utilLegacyMapper();
		if (map == null) {
			return "[]";
		}
		try {
			return buildInsaOptionsString(map.selectInsaSecondApprovalLine(user_cd));
		} catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
    }
}
