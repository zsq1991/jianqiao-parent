package com.zc.common.core.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import com.zc.common.core.utils.CommonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

/**
 * 初始化银联的参数
 *
 * @author zc
 *
 */
@Component
public class InitParams {

	private static final String DES3KEY="8B479A1FABB42BA44BBA7BC3599597BA";

	private Properties properties = new Properties();

	/**
	 * 加载配置文件
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream("/init.properties");

		if(inputStream == null){
			IOUtils.closeQuietly(inputStream);
			return;
		}

		properties.load(inputStream);



		Map<String,String> map=new HashMap<String,String>();
		Iterator<Object> keys = properties.keySet().iterator();
		while(keys.hasNext()) {
			String key= (String)keys.next();
			String key1= new String((byte[]) BASE64.decode(key),"UTF-8");
			key1=CommonUtils.decrypt3DES(key1, DES3KEY);
			if(properties.getProperty(key)!=null){
				String value= new String((byte[]) BASE64.decode(properties.getProperty(key)+""),"UTF-8");
				String value1 = CommonUtils.decrypt3DES(value, DES3KEY);
				map.put(key1, value1);
			}
		}
		properties.putAll(map);
		IOUtils.closeQuietly(inputStream);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private final static Base64 BASE64=new Base64();

}
