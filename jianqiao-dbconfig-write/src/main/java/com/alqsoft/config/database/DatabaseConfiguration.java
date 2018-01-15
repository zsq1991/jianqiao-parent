package com.alqsoft.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.codingapi.tx.datasource.relational.LCNTransactionDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;


/*@项目：tianyi-common-core
 *
 * @描述：数据库配置
 *
 * @作者： chang
 *
 * @创建时间：2017年3月8日
 *
 * @Copyright @2017 by Mr.chang
 */
@Configuration
@PropertySource(value = "classpath:application-write.properties")
public class DatabaseConfiguration implements EnvironmentAware{


	/**
	 * 日志对象
	 */
	private static Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	private  RelaxedPropertyResolver prop;



	@Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid");
    }

    @Override
	public void setEnvironment(Environment environment) {
         prop = new RelaxedPropertyResolver(environment,"spring.datasource.");
	}
	
    /**
	 * @配置数据库连接池配置
	 * @return*/


	@Bean(initMethod="init",destroyMethod="close")
	@Primary
	public   DataSource dataSource(){
		log.info("数据库连接池加载中··············");
		if (StringUtils.isEmpty(prop.getProperty("url"))) {
			log.error("数据库连接池配置错误,请检查spring配置文件");
            throw new ApplicationContextException("数据库连接池配置错误!");
        }else{
        	DruidDataSource druid=new DruidDataSource();
        	//设置连接池连接基本信息
        	druid.setUrl(prop.getProperty("url"));
        	druid.setUsername(prop.getProperty("username"));
        	druid.setPassword(prop.getProperty("password"));
        	druid.setDriverClassName(prop.getProperty("driver-class-name"));
        	//连接池初始化参数设置
        	/*druid.setInitialSize(Integer.valueOf(prop.getProperty("druid.initialSize")));
        	druid.setMinIdle(Integer.valueOf(prop.getProperty("druid.minIdle")));
        	druid.setMaxActive(Integer.valueOf(prop.getProperty("druid.maxActive")));
        	druid.setMaxWait(Integer.valueOf(prop.getProperty("druid.maxWait")));
        	druid.setTimeBetweenConnectErrorMillis(Long.valueOf(prop.getProperty("druid.timeBetweenEvictionRunsMillis")));
        	druid.setMinEvictableIdleTimeMillis(Long.valueOf(prop.getProperty("druid.minEvictableIdleTimeMillis")));
        	druid.setValidationQuery(prop.getProperty("druid.validationQuery"));
        	druid.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("druid.testWhileIdle")));
        	druid.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("druid.testOnBorrow")));
        	druid.setTestOnReturn(Boolean.parseBoolean(prop.getProperty("druid.testOnReturn")));
        	druid.setConnectionProperties(prop.getProperty("druid.connectionProperties"));*/
        	 try {
        		 druid.setFilters("stat,wall,slf4j,config");
             } catch (SQLException e) {
                 log.error("druid数据库连接池初始化异常");
             }
        	return druid;
        }
	}

	@Bean
	public  LCNTransactionDataSource lcnDataSourceProxy(){
		LCNTransactionDataSource proxy = new LCNTransactionDataSource();
		proxy.setDataSource(dataSource());
		proxy.setMaxCount(20);
		return proxy;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception{

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(lcnDataSourceProxy());
		sqlSessionFactoryBean.setTypeAliasesPackage("com.zc.main.entity");
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources=resolver.getResources("classpath:com/zc/mybatis/dao/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resources);

		Resource resources2=resolver.getResource("classpath:mybatis-config.xml");
		sqlSessionFactoryBean.setConfigLocation(resources2);
		return sqlSessionFactoryBean;
	}

	/*@Bean
	public MapperScannerConfigurer mapperScannerConfigurer()
	{
		MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
		mapperScannerConfigurer.setAnnotationClass(MyBatisRepository.class);
		mapperScannerConfigurer.setBasePackage("com.zc.mybatis.dao");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}*/

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}



	@Bean
	public PlatformTransactionManager transactionManager(){
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(lcnDataSourceProxy());

		return dataSourceTransactionManager;
	}


}
