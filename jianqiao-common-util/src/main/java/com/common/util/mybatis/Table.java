package com.common.util.mybatis;

import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

	// 表名
	String name;

	// 字段类型
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
		String type="";
		for ( Field field : fields ){

			Column column = field.getAnnotation(Column.class);
			String name = column.name();
			type = getType(name,field.getType());
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

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {

			String column =null;

			String type="";

			if (method.getName().startsWith("get")) {

				column = getFieldName(method.getName().substring(3));

			}

		if(column!=null){

				type=getType(column,method.getReturnType());

				table.setField(column,type);

		}

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
		
		if(clazz.getName().equals("int") || clazz.getName().equals("java.lang.Integer")){
			
			txt="#{" + column + ",javaType=Integer,jdbcType=INTEGER}";
			
		}else if(clazz.getName().equals("float") || clazz.getName().equals("java.lang.Float")){
			
			txt="#{" + column + ",javaType=float,jdbcType=FLOAT}";
			
		}else if(clazz.getName().equals("long") || clazz.getName().equals("java.lang.Long")){
			
			txt="#{" + column + ",javaType=long,jdbcType=INTEGER}";
			
		}else if(clazz.getName().equals("double") || clazz.getName().equals("java.lang.Double")){
			
			txt="#{" + column + ",javaType=double,jdbcType=DOUBLE}";
			
		}else if(clazz.getName().equals("boolean") || clazz.getName().equals("java.lang.Boolean")){
			
			txt="#{" + column + ",javaType=boolean,jdbcType=BOOLEAN}";
			
		}else if(clazz.getName().equals("java.lang.String")){
			
			txt="#{" + column + ",javaType=string,jdbcType=VARCHAR}";
			
		}else if(clazz.getName().equals("java.util.Date")){
			
			txt="#{" + column + ",javaType=java.util.Date,jdbcType=TIMESTAMP}";
			
		}else if(clazz.getName().equals("void")){
			
			System.out.println(column + " is void");
			
		}else{
			
			throw new Exception("Table unknow type"+clazz.getName()+"["+column+"]");
			
			
		}
		
		return txt;
		
	}

}
