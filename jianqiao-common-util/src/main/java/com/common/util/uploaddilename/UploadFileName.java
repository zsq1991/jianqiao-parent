package com.common.util.uploaddilename;
 
/**
 * 角色枚举
 * 
 * @author sunhuijie
 *
 * @date 2016年10月13日
 *
 */
public enum UploadFileName {
	MEMBER_HEADIMG {//用户头像上传
		@Override
		public String getName() {
			return "memberheadimg";
		}
	},
	CONSULTATION_IMG {//内容图片
		@Override
		public String getName() {
			return "consultationimg";
		}
	};
	public abstract String getName();
}
