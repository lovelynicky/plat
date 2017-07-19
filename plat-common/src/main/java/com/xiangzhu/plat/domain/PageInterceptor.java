package com.xiangzhu.plat.domain;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liluoqi on 2017/7/9.
 * 分页拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Interceptor.class);

    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";

    protected String databaseType;// 数据库类型，不同的数据库有不同的分页方法
    /**
     * 线程持有对象
     */
    protected ThreadLocal<Page> pageThreadLocal = new ThreadLocal<Page>();


    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = statementHandler.getBoundSql();
        prepareAndCheckDatabaseType((Connection) invocation.getArgs()[0]);
        Object parameterObject = boundSql.getParameterObject();
        Pagination pagination = null;
        if (parameterObject instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) parameterObject;
            if (params.containsKey("pagination")) {
                pagination = (Pagination) params.get("pagination");
            }
        } else if (parameterObject instanceof ParamMap) {
            if (((ParamMap) parameterObject).containsKey("pagination")) {
                pagination = (Pagination) ((ParamMap) parameterObject).get("pagination");
            }
        } else if (parameterObject instanceof Pagination) {
            pagination = (Pagination) parameterObject;
        } else {
            logger.warn("不是查询语句，不走分页");
        }
        /**
         * 只要repository方法里面包含分页参数Pagination即进行分页查询
         */
        if (pagination != null) {
            Connection connection = (Connection) invocation.getArgs()[0];
            pagination.withTotalCount(getTotalCount(boundSql, parameterObject, mappedStatement, connection));
            String pageSql = buildSql(pagination, boundSql.getSql());
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

    protected void prepareAndCheckDatabaseType(Connection connection) throws SQLException {
        if (databaseType == null) {
            String productName = connection.getMetaData().getDatabaseProductName();
            if (logger.isTraceEnabled()) {
                logger.trace("Database productName: " + productName);
            }
            productName = productName.toLowerCase();
            if (productName.contains(MYSQL)) {
                databaseType = MYSQL;
            } else if (productName.contains(ORACLE)) {
                databaseType = ORACLE;
            } else {
                throw new PageNotSupportException("Page not support for the type of database, database product name [" + productName + "]");
            }
            if (logger.isInfoEnabled()) {
                logger.info("自动检测到的数据库类型为: " + databaseType);
            }
        }
    }

    private Page getPageObject(Object parameterObj, Pagination pagination) {
        if (parameterObj instanceof Pagination || parameterObj instanceof Map) {
            return new Page(pagination);
        }
        return null;
    }

    private String buildSql(Pagination pagination, String sql) {
        if (MYSQL.equals(databaseType)) {
            return sql + " limit " + pagination.getStartIndex() + "," + pagination.getTotalSelect();
        } else if (ORACLE.equals(databaseType)) {
            // TODO: 2017/7/9 oracle 暂未支持
            logger.warn("oracle分页暂未完成");
            return sql;
        } else {
            logger.error("除了mysql和oracle之外其他数据库类型未支持");
            return sql;
        }
    }

    private long getTotalCount(BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement, Connection connection) throws SQLException {
        String countSql = String.format("select count(1) from (%s) t", boundSql.getSql());
        logger.debug("分页时, 生成countSql: " + countSql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(countSql);
            parameterHandler.setParameters(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (Exception e) {
                    logger.warn("关闭ResultSet时异常.", e);
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    logger.warn("关闭PreparedStatement时异常.", e);
                }
        }
        return 0;
    }

    public static class PageNotSupportException extends RuntimeException {

        public PageNotSupportException() {
            super();
        }

        public PageNotSupportException(String message, Throwable cause) {
            super(message, cause);
        }

        public PageNotSupportException(String message) {
            super(message);
        }

        public PageNotSupportException(Throwable cause) {
            super(cause);
        }
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
}
