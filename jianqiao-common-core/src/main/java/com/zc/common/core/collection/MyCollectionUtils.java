package com.zc.common.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

/**
 * 我的集合工具
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-22 下午1:36:14
 * 
 */
public class MyCollectionUtils {

	/**
	 * 转换成list类型
	 * 
	 * @param iterable
	 * @return
	 */
	public static <T> List<T> toList(Iterable<T> iterable) {
		List<T> list = new ArrayList<T>();
		CollectionUtils.addAll(list, iterable.iterator());
		return list;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null) && (!collection.isEmpty());
	}

	/**
	 * 集合的循环操作
	 * 
	 * @param collection
	 * @param consumer
	 */
	public static <T> void forEach(Collection<T> collection, Consumer<T> consumer) {
		collection.parallelStream().forEach(consumer);
	}

	/**
	 * 集合的过滤
	 * 
	 * @param collection
	 * @param consumer
	 */
	public static <T> Stream<T> forEach(Collection<T> collection, Predicate<T> predicate) {
		return collection.parallelStream().filter(predicate);
	}
}
