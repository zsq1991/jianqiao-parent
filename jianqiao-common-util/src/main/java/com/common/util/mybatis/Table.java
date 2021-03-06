package com.common.util.mybatis;

import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @category 表对象
 * 
 * @author Neo
 * 
 */
public class Table {

	/**
	 * 表名
	 */
	String name;

	/**
	 * 字段类型
	 */
	Map<String, String> fields = new HashMap<String, String>();

	public Iterator<String> getKeys() {

		return fields.keySet().iterator();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField(String key) {

		return fields.get(key);

	}

	public void setField(String key, String value) {

		fields.put(key, value);

	}
	
	public static Table build(Class<?> clazz) throws Exception {

		Table table = new Table();

		Alias alias = clazz.getAnnotation(Alias.class);

		if (alias == null) {

			throw new Exception(clazz.getSimpleName()
					+ ": not annotation @Alias");
		}

		table.setName(alias.value());

		Field[ ] fields = clazz.getFields( );
		for ( Field field : fields ){
			String fieldName=field.getName();
			if("id".equals(fieldName)){
				Column column=field.getAnnotation(Column.class);
				if(column==null){
					continue;
				}
				String name=column.name();
				Class typeClazz=field.getType();
				if(name==null || "".equals(name)){
					throw new Exception(fieldName
							+ ": not name @Column");
				}
				String type=getType(fieldName,typeClazz);
				table.setField(fieldName,type);
			}
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field:declaredFields) {
			Column column=field.getAnnotation(Column.class);
			if(column==null){
				continue;
			}
			String name=column.name();
			String fieldName=field.getName();
			Class typeClazz=field.getType();
			if(name==null || "".equals(name)){
				throw new Exception(fieldName
						+ ": not name @Column");
			}
			String type=getType(fieldName,typeClazz);
			table.setField(name,type);
		}
		return table;
	}


	public static Table buildinsert(Class<?> clazz) throws Exception {

		Table table = new Table();

		Alias alias = clazz.getAnnotation(Alias.class);

		if (alias == null) {

			throw new Exception(clazz.getSimpleName()
					+ ": not annotation @Alias");
		}

		table.setName(alias.value());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields) {
			Column column=field.getAnnotation(Column.class);
			if(column==null){
				continue;
			}
			String name=column.name();
			String fieldName=field.getName();
			Class typeClazz=field.getType();
			if(name==null || "".equals(name)){
				throw new Exception(fieldName
						+ ": not name @Column");
			}
			String type=getType(fieldName,typeClazz);
			table.setField(name,type);
		}
		return table;
	}


	public static Table build(Object obj) throws Exception {

		return build(obj.getClass());

	}

	public static Table buildinsert(Object obj) throws Exception {

		return buildinsert(obj.getClass());

	}
	
	private static String getFieldName(String methodName){
		
		if(methodName.length()>0){
			
			return methodName.substring(0,1).toLowerCase()+methodName.substring(1);
			
		}
		
		return null;
		
	}
	
	private static String getType(String column,Class<?> clazz) throws Exception{
		
		String txt = null;
		String intStr="int";
		String integerClass="java.lang.Integer";
		String floatStr="float";
		String floatClass="java.lang.Float";
		String longStr="long";
		String longClass="java.lang.Long";
		String doubleStr="double";
		String doubleClass="java.lang.Double";
		String booleanStr="boolean";
		String booleanClass="java.lang.Boolean";
		String stringClass="java.lang.String";
		String dateClass="java.util.Date";
		String voidStr="void";
		if(intStr.equals(clazz.getName()) || integerClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=Integer,jdbcType=INTEGER}";
			
		}else if(floatStr.equals(clazz.getName()) || floatClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=float,jdbcType=FLOAT}";
			
		}else if(longStr.equals(clazz.getName()) || longClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=long,jdbcType=INTEGER}";
			
		}else if(doubleStr.equals(clazz.getName()) || doubleClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=double,jdbcType=DOUBLE}";
			
		}else if(booleanStr.equals(clazz.getName()) || booleanClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=boolean,jdbcType=BOOLEAN}";
			
		}else if(stringClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=string,jdbcType=VARCHAR}";
			
		}else if(dateClass.equals(clazz.getName())){
			
			txt="#{" + column + ",javaType=java.util.Date,jdbcType=TIMESTAMP}";
			
		}else if(voidStr.equals(clazz.getName())){
			
			System.out.println(column + " is void");
			
		}else{
			
			throw new Exception("Table unknow type"+clazz.getName()+"["+column+"]");
			
			
		}
		
		return txt;
		
	}

}
