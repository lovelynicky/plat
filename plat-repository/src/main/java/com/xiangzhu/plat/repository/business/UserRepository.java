package com.xiangzhu.plat.repository.business;

import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.domain.business.User;
import com.xiangzhu.plat.repository.base.BaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liluoqi on 2017/7/9.
 * 用户数据操作
 */

public interface UserRepository extends BaseRepository<User>{

    List<User> queryAllPagination(@Param("pagination") Pagination pagination);

    User queryByUsername(@Param("username") String username);

    User queryByOpenId(@Param("openId") String openId);
}
