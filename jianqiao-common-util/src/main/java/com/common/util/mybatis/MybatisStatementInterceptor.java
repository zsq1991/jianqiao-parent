package com.common.util.mybatis;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis 语句拦截器
 * 主要是拦截update和create语句,检测语句中是否有created_time和update_time
 *如果没有,那么需要在语句中进行这两个字段的拼接
 *
 * @author 张灿
 */

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisStatementInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisStatementInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();


        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        Date date = new Date();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = sdf.format(date);

        // 分离代理对象链(由于目标类可能被多个插件拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        // 分页参数作为参数对象parameterObject的一个属性
        String sql = boundSql.getSql();

        String sqlCommandType = mappedStatement.getSqlCommandType().name();

        /*if(sql.contains("update_time") || sql.contains("created_time")){

        }else {
            if ("UPDATE".equals(sqlCommandType)) {//如果是“增加”或“更新”操作
                String updateCreatdTime = ",update_time = " + "'"+s1+"'";
                String[] orgSql = sql.split("\nWHERE");
                String fristSql = orgSql[0];
                String secondSql = orgSql[1];
                String newSql = fristSql + updateCreatdTime + "\nWHERE" + secondSql;
                metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
            }
            if ("INSERT".equals(sqlCommandType)) {
                String createdTimeKey= ",created_time ,update_time ";
                String createdTimeValue=",'"+s1+"','"+s1+"'";
                String[] orgSql = sql.split("\\)(\\s+)VALUES?");
                String fristSql = orgSql[0];
                String secondSql = orgSql[1];

                String   secondSqlSubFirst=secondSql.substring(0,secondSql.lastIndexOf(')'));

                String newSql = fristSql + createdTimeKey+")  VALUES"+  secondSqlSubFirst+createdTimeValue+")";
                metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
            }
        }*/

        Object result = invocation.proceed();
        return result;
    }
/*
     public static void main(String args[]) {
         String createdTimeKey= ",created_time ,update_time ";
         String createdTimeValue="'2017-1-1','2017-1-1'";
         String sql="INSERT INTO cc(ccc,vvv) VALUES(1,2)";
         String[] orgSql = sql.split("\\)(\\s+)VALUES?");
         String fristSql = orgSql[0];
         String secondSql = orgSql[1];
         String [] secondSqlSub=secondSql.split("\\)",secondSql.length()-1);


         System.out.println(createdTimeValue+")");
      }
*/


    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {
        System.out.println(arg0.getProperty("someProperty").toString());
    }

}
