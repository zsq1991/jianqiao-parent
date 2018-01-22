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
	/**
	 * 用户头像上传
	 */
	MEMBER_HEADIMG {//用户头像上传
		@java.lang.Override
        @Override
		public String getName() {
			return "memberheadimg";
		}
	},
	/**
	 * 内容图片
	 */
	CONSULTATION_IMG {//内容图片
		@java.lang.Override
        @Override
		public String getName() {
			return "consultationimg";
		}
	};
	public abstract String getName();
}
