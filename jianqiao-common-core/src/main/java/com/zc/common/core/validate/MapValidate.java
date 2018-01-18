package com.zc.common.core.validate;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.zc.common.core.regexp.ValiRegexp;
import com.zc.common.core.string.MyStringUtils;

/**
 * 关于map的验证操作
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-10 下午9:04:57
 * 
 */
public class MapValidate {
	/**
	 * 
	 * @param map
	 * @param valis 
	 *            为mapname@null;required;max:?;min:?;maxleng:?;minleng:?;norequired
	 *            -显示的错误内容 为null代表不进行验证
	 * @param valiRegexps 这个值要与上面的数组相对应,如果不做正则验证可以为null,如果最后不需要验证的可以不写
	 * @return
	 */
	public static boolean validatorMap(final Map<String, String> map, final String[] valis,
			final ValiRegexp[] valiRegexps) {
		int i = 0;
		int j = 0;
		// 遍历string数组进行验证
		for (String valiString : valis) {
			// 获取map对应的值
			String value = map.get(StringUtils.substringBefore(valiString, "@"));
			String[] valiss = StringUtils.split(StringUtils.substringAfter(valiString, "@"), ";");

			for (String string2 : valiss) {
				// 是否不为空
				if (StringUtils.indexOf(string2, "required") != -1) {
					if (StringUtils.isNotBlank(value)) {
                        j++;
                    }
				}
				// 最大值判断
				else if (StringUtils.indexOf(string2, "max") != -1) {
					String max = StringUtils.substringAfter(string2, ":");
					if (NumberUtils.isNumber(value)) {
						if (NumberUtils.toInt(value) - NumberUtils.toInt(max) <= 0) {
                            j++;
                        }
					}
				}
				// 最小值判断
				else if (StringUtils.indexOf(string2, "min") != -1) {
					String min = StringUtils.substringAfter(string2, ":");
					if (NumberUtils.isNumber(value)) {
						if (NumberUtils.toInt(value) - NumberUtils.toInt(min) >= 0) {
                            j++;
                        }
					}
				}
				// 最大长度判断
				else if (StringUtils.indexOf(string2, "maxleng") != -1) {
					String maxleng = StringUtils.substringAfter(string2, ":");
					if (StringUtils.length(value) <= NumberUtils.toInt(maxleng)) {
                        j++;
                    }
				}
				// 最小长度判断
				else if (StringUtils.indexOf(string2, "minleng") != -1) {
					String minleng = StringUtils.substringAfter(string2, ":");
					if (StringUtils.length(value) >= NumberUtils.toInt(minleng)) {
                        j++;
                    }
				}
				// 不做处理判断
				else if (StringUtils.indexOf(string2, "norequired") != -1) {
					j++;
				}
				// 进行正则验证
				if (i < valiRegexps.length && valiRegexps[i] != null) {
					if (!MyStringUtils.valiRegexp(value, valiRegexps[i].getName())) {
						j--;
					}
				}
			}
			i++;
		}
		return i == j ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 对list<map<String,String>> 进行验证过滤
	 * 
	 * @param maps 需要验证的集合对象
	 * @param valis 
	 *            为mapname@null;required;max:?;min:?;maxleng:?;minleng:?;norequired
	 *            -显示的错误内容 为null代表不进行验证
	 * @param valiRegexps 这个值要与上面的数组相对应,如果不做正则验证可以为null,如果最后不需要验证的可以不写
	 * @param errorMaps
	 */
	@SuppressWarnings("rawtypes")
	public static void validatorListMap(final List<Map> maps, final String[] valis,
			final ValiRegexp[] valiRegexps, final List<Map<String, String>> errorMaps) {
		// 过滤出验证的的集合
		CollectionUtils.filter(maps, new Predicate() {

			@Override
            @SuppressWarnings("unchecked")
			public boolean evaluate(Object arg0) {
				// 验证数据
				boolean result = validatorMap((Map<String, String>) arg0, valis, valiRegexps);
				if (!result) {
					errorMaps.add((Map) arg0);
				}
				return result;
			}
		});
	}
}
