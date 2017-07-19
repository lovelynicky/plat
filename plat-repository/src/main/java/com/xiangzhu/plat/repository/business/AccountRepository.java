package com.xiangzhu.plat.repository.business;

import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.domain.business.Account;
import com.xiangzhu.plat.repository.base.BaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liluoqi on 2017/7/15.
 * 账户信息数据库查询
 */
public interface AccountRepository extends BaseRepository<Account>{
    /**
     * 根据用户查询账户信息
     *
     * @param userId 用户id
     * @return Account
     */
    Account queryByUserId(@Param("userId") long userId);

    List<Account> queryAllPagination(@Param("pagination") Pagination pagination);

}
