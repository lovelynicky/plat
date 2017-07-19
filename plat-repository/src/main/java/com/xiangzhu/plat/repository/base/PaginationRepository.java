package com.xiangzhu.plat.repository.base;

import com.xiangzhu.plat.domain.BaseModel;
import com.xiangzhu.plat.domain.Page;
import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liluoqi on 2017/7/10.
 * 分页查询类
 */
@Component
public class PaginationRepository<T extends BaseModel> implements ApplicationContextAware {

    /**
     * 上下文信息
     */
    protected ApplicationContext applicationContext;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PaginationRepository.class);

    /**
     * 泛型repository对象，实际上mybatis采用了JDKd的动态代理机制，这里的repository实际类型是MapperProxy
     */
    private Object repository;

    /**
     * 分页查询数据库操作专用类
     *
     * @param queryMethod 实际<K>repository接口的查询方法
     * @param pagination  分页参数
     * @param objects     查询参数
     * @return Page<T>分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Page<T> queryPagination(String repositoryName,String queryMethod, Pagination pagination, Object... objects) {
        Page<T> page = new Page<T>(pagination);
        repository = applicationContext.getBean(repositoryName);
        try {
            Object object = ReflectUtils.executeMethod(queryMethod, repository, addPaginationParam(objects, pagination));
            if (object instanceof List) {
                page.withTotalCount(pagination.getTotalCount()).withData((List) object);
            }
        } catch (Throwable throwable) {
            logger.error(String.format("查询分页方法:%s时报错", throwable));
        }
        return page;
    }

    private Object[] addPaginationParam(Object[] objects, Pagination pagination) {
        Object[] params = new Object[objects.length + 1];
        params[0] = pagination;
        System.arraycopy(objects, 0, params, 1, params.length - 1);
        return params;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
