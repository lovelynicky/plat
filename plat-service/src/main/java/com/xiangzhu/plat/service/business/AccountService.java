package com.xiangzhu.plat.service.business;

import com.xiangzhu.plat.domain.Page;
import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.domain.business.Account;
import com.xiangzhu.plat.domain.business.User;
import com.xiangzhu.plat.repository.base.BaseRepository;
import com.xiangzhu.plat.repository.base.PaginationRepository;
import com.xiangzhu.plat.repository.business.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liluoqi on 2017/7/15.
 * 账户service
 */
@Service
public class AccountService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    /**
     * userService
     */
    @Autowired
    private UserService userService;
    /**
     * accountRepository
     */
    @Resource(name = "accountRepository")
    private BaseRepository<Account> accountRepository;

    @Autowired
    private PaginationRepository<Account> paginationRepository;

    public Account getAccountByOpenId(String openId) {
        try {
            User user = userService.queryByOpenId(openId);
            if (user != null) {
                return ((AccountRepository)accountRepository).queryByUserId(user.getId());
            }
        } catch (Throwable e) {
            logger.error(String.format("获取openId：%sd的余额信息失败", openId));
        }
        return null;
    }

    public Page<Account> queryAll(Pagination pagination){
        return paginationRepository.queryPagination("accountRepository",
                "queryAllPagination",pagination);
    }
}
