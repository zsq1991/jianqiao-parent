/**  
 * @Title:  WebProperties.java   
 * @Package com.ph.shopping.common.core.config.properties   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月29日 下午1:43:01   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.zc.service.confige.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**   
 * @ClassName:  WebProperties   
 * @Description:web相关配置   
 * @author: 李杰
 * @date:   2017年6月29日 下午1:43:01     
 * @Copyright: 2017
 */
@PropertySource("classpath:web.properties")
@ConfigurationProperties(prefix = "web")
@Component
public class WebProperties {
	/**
	 * app线下订单金额签名字符串
	 */
	private String appUnlineOrderMoneySign;
	/**
	 * 服务版本
	 */
	private String serviceVersion;
	/**
	 * ios会员app下载地址
	 */
	private String iosMemberAppUploadUrl;
	/**
	 * ios商户app下载地址
	 */
	private String iosMerchantAppUploadUrl;
	/**
	 * android会员app下载地址
	 */
	private String androidMemberAppUploadUrl;
	/**
	 * android商户app下载地址
	 */
	private String androidMerchantAppUploadUrl;
	
	public String getAppUnlineOrderMoneySign() {
		return appUnlineOrderMoneySign == null ? null : appUnlineOrderMoneySign.trim();
	}

	public void setAppUnlineOrderMoneySign(String appUnlineOrderMoneySign) {
		this.appUnlineOrderMoneySign = appUnlineOrderMoneySign;
	}

	public String getServiceVersion() {
		return serviceVersion == null ? null : serviceVersion.trim();
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getIosMemberAppUploadUrl() {
		return iosMemberAppUploadUrl == null ? null : iosMemberAppUploadUrl.trim();
	}

	public void setIosMemberAppUploadUrl(String iosMemberAppUploadUrl) {
		this.iosMemberAppUploadUrl = iosMemberAppUploadUrl;
	}

	public String getIosMerchantAppUploadUrl() {
		return iosMerchantAppUploadUrl == null ? null : iosMerchantAppUploadUrl.trim();
	}

	public void setIosMerchantAppUploadUrl(String iosMerchantAppUploadUrl) {
		this.iosMerchantAppUploadUrl = iosMerchantAppUploadUrl;
	}

	public String getAndroidMemberAppUploadUrl() {
		return androidMemberAppUploadUrl == null ? null : androidMemberAppUploadUrl.trim();
	}

	public void setAndroidMemberAppUploadUrl(String androidMemberAppUploadUrl) {
		this.androidMemberAppUploadUrl = androidMemberAppUploadUrl;
	}

	public String getAndroidMerchantAppUploadUrl() {
		return androidMerchantAppUploadUrl == null ? null : androidMerchantAppUploadUrl.trim();
	}

	public void setAndroidMerchantAppUploadUrl(String androidMerchantAppUploadUrl) {
		this.androidMerchantAppUploadUrl = androidMerchantAppUploadUrl;
	}
	
}
