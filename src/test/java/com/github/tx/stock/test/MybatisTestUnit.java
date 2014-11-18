package com.github.tx.stock.test;

import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tangx
 * @since 2014年10月22日
 */

public abstract class MybatisTestUnit {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws Exception {
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	@After
	public void tearDown() throws Exception {
	}

}
