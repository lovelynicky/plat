package com.xiangzhu.plat.repository.base;

import com.xiangzhu.plat.domain.BaseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liluoqi on 2017/7/9.
 * 数据库操作基础通用接口
 */
public interface BaseRepository<T extends BaseModel> {
    /**
     * 插入数据
     * @param model model
     * @return 主键id
     */
    long insert(T model);

    /**
     * 根据主键id查询
     * @param id id
     * @return T
     */
    T queryById(@Param("id") long id);

    /**
     * 全部数据不分页
     * @return List T
     */
    List<T> queryAll();
}
