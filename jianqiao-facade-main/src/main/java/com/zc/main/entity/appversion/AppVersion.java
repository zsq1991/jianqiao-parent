package com.zc.main.entity.appversion;


import com.zc.common.core.orm.hibernate.IdEntity;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @description: 软件更新版本信息
 * @author:  ZhaoJunBiao
 * @date:  2018/1/18 16:41
 * @version: 1.0.0
 */
@Alias("alq_app_version")
public class AppVersion extends IdEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "version")
	private String version;//版本号
	@Column(name = "attachment_id")
	private Long attachmentId;//文件
	@Column(name = "is_used")
	private Integer isUsed;//是否使用该版本 1是  0不是
	@Column(name = "is_must_to_update")
	private Integer isMustToUpdate;//是否强制更新 1强制更新 0不强制更新
	@Column(name = "message")
	private String  message;//更新信息
	@Column(name = "apply_name")
	private String   applyName;//应用名称
	@Column(name = "apply_aplat")
	private String    applyAplat;//应用平台
	@Column(name = "is_delete")
	private Integer isDelete;// 0或null未删除，1删除

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getIsMustToUpdate() {
		return isMustToUpdate;
	}

	public void setIsMustToUpdate(Integer isMustToUpdate) {
		this.isMustToUpdate = isMustToUpdate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyAplat() {
		return applyAplat;
	}

	public void setApplyAplat(String applyAplat) {
		this.applyAplat = applyAplat;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
