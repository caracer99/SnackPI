package com.snack.api.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value="com.snack.api", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatabaseConfig {

	@Value("${db.driver}")		private String driver;
	@Value("${db.url}")			private String url;
	@Value("${db.username}")	private String username;
	@Value("${db.password}")	private String password;
	
	@Primary
	@Bean(name="dataSource", destroyMethod="close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
//		ds.setInitialSize(2);	// 초기 풀 갯수(기본:10)
//		ds.setMaxActive(10);	// 최대 연결 갯수(기본:100)
//		ds.setTestWhileIdle(true); 						// 풀에 유휴 상태 검사 여부(기본:False).
//		ds.setMinEvictableIdleTimeMillis(60000 * 3);	// 풀에 유휴 유지 시간(기본:60초)
//		ds.setTimeBetweenEvictionRunsMillis(1000 * 10);	// 풀에 유휴 검사 주기(기본:5초)
		return ds;
	}
		
	@Primary
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(datasource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.snack.api");
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/com/snack/api/**/mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Primary
	@Bean(name="sqlSession")
	public SqlSession sqlSession(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
