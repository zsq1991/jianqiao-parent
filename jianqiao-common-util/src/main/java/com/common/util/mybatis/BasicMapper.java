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

	@InsertProvider(type = BaseSqlProvider.class, method = "insert")  
	@SelectKey(statement="SELECT last_insert_id()", keyProperty="id", before=false, resultType=long.class)
	public int insert(T t);
	
	@UpdateProvider(type = BaseSqlProvider.class, method = "updateById")  
	public int updateById(T t);
	
	@UpdateProvider(type = BaseSqlProvider.class, method = "deleteById")  
	public int deleteById(T t);
	
	@DeleteProvider(type = BaseSqlProvider.class, method = "deleteRealById")  
	public int deleteRealById(T t);
	
	@SelectProvider(type = BaseSqlProvider.class, method = "findTById")
	public T findTById(T t);
	
	@SelectProvider(type = BaseSqlProvider.class, method = "findAll")
	public  List<T> findAll(T t);
	
	
}
