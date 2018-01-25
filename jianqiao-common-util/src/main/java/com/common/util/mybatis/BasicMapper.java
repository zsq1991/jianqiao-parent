package com.common.util.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 配置Mybatis Map基类
 * @author CloudKwb
 *
 * @param <T>
 */
public interface BasicMapper<T> {

	/**
	 * 添加实体
	 * @param t 实体类
	 * @return
	 */
	@InsertProvider(type = BaseSqlProvider.class, method = "insert")  
	@SelectKey(statement="SELECT last_insert_id()", keyProperty="id", before=false, resultType=long.class)
	public int insert(T t);

	/**
	 * 根据id修改实体
	 * @param t 实体类
	 * @return
	 */
	@UpdateProvider(type = BaseSqlProvider.class, method = "updateById")  
	public int updateById(T t);

	/**
	 * 根据id删除实体
	 * @param t 实体类
	 * @return
	 */
	@UpdateProvider(type = BaseSqlProvider.class, method = "deleteById")  
	public int deleteById(T t);

	/**
	 * 根据id删除实体
	 * @param t 实体类
	 * @return
	 */
	@DeleteProvider(type = BaseSqlProvider.class, method = "deleteRealById")  
	public int deleteRealById(T t);

	/**
	 * 根据id查询实体
	 * @param t 实体类
	 * @return
	 */
	@SelectProvider(type = BaseSqlProvider.class, method = "findTById")
	public T findTById(T t);

	/**
	 * 查询所以
	 * @param t 实体类
	 * @return
	 */
	@SelectProvider(type = BaseSqlProvider.class, method = "findAll")
	public  List<T> findAll(T t);
	
	
}
