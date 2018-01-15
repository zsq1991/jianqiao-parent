package com.common.util.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * 数据处理基类
 * @author CloudKwb
 *
 * @param <T>
 */
public class BaseSqlProvider<T> {

	private static Logger logger= LoggerFactory.getLogger(BaseSqlProvider.class);

	public String deleteById(T t) throws Exception {
		
		String sql="";

		Table table;

		table = Table.build(t);

		BEGIN();
		
		UPDATE(table.getName());

		SET("is_deleted =" + table.getField("is_deleted"));

		WHERE("id = " + table.getField("id"));
		
		sql=SQL();
		logger.info("sql:deleteById:"+sql);

		return sql;

	}
	public String deleteRealById(T t) throws Exception {
		
		String sql="";

		Table table;

		table = Table.build(t);

		BEGIN();
		
		DELETE_FROM(table.getName());

		WHERE("id = " + table.getField("id"));
		
		sql=SQL();
		logger.info("sql:deleteRealById:"+sql);
		return sql;		
		
	}

	public String updateById(T t) throws Exception {
		
		String sql="";

		Table table;

		table = Table.build(t);

		BEGIN();

		UPDATE(table.getName());

		Iterator<String> keys = table.getKeys();

		while (keys.hasNext()) {

			String key = keys.next();

			if (!key.equals("id")) {
				//检验字段是否为空，为空的话就不需要更新了
				String filedValue=FieldMap.getObjectValue(t,(getFiledName(table.getField(key))));
				 if(!filedValue.equals("")){
					 SET(key + "=" + table.getField(key));
				 }
				 if(key.equals("update_time")){
					 SET(key + "= now()" );
					} 
			}

		}
		//SET("update_time =" + "= now()");
		WHERE("id = " + table.getField("id"));

		sql=SQL();
		logger.info("sql:updateById:"+sql);

		return sql;
	}
	
	/**
	 * 获取字段名称
	 * @param oldName
	 * @return
	 */
	private String getFiledName(String oldName){
		int index0=oldName.indexOf("{");
		int index1=oldName.indexOf(",");
		return oldName.substring(index0+1,index1);
	}

	public String insert(T t) throws Exception {

		String sql="";
		
		Table table;

		table = Table.buildinsert(t);

		BEGIN();

		INSERT_INTO(table.getName());

		Iterator<String> keys = table.getKeys();

		while (keys.hasNext()) {

			String key = keys.next();
			if (!key.equals("id")) {
				if(key.equals("created_time")||key.equals("update_time")){
					VALUES(key, "now()");
				} else {
					VALUES(key, table.getField(key));
				  }
			}
			
		}
		//VALUES("created_time", "now()");
		logger.info("sql:insert:"+sql);
		sql=SQL();
		return sql;
	}
	
	public String findTById(T t) throws Exception {
		Table table;
		table = Table.build(t);
		
		
		String sql= "select * from "+table.getName()+" where id ="+table.getField("id");
		logger.info("sql:findTById:"+sql);
		return sql;
	}
	
	public String findAll(T t) throws Exception {
		Table table;
		table = Table.build(t);
		String sql= "select * from "+table.getName();
		logger.info("sql:findAll:"+sql);
		return sql;
	}
	
	
	
	
 
}
