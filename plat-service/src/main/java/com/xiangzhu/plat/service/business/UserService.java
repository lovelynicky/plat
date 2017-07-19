package com.xiangzhu.plat.service.business;

import com.xiangzhu.plat.domain.Page;
import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.domain.UserLoginCheck;
import com.xiangzhu.plat.domain.business.User;
import com.xiangzhu.plat.repository.base.BaseRepository;
import com.xiangzhu.plat.repository.base.PaginationRepository;
import com.xiangzhu.plat.repository.business.UserRepository;
import com.xiangzhu.plat.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by liluoqi on 2017/7/9.
 */
@Service
public class UserService {

    @Resource(name = "userRepository")
    private BaseRepository<User> userRepository;
    @Autowired
    private PaginationRepository<User> paginationRepository;

    @Transactional
    public long add(User user) {
        return userRepository.insert(user);
    }

    @Cacheable("userPagination")
    public Page<User> queryAllPagination(Pagination pagination) {
        return paginationRepository.queryPagination("userRepository","queryAllPagination", pagination);
    }

    public UserLoginCheck validate(String username, String password) {
        User user = ((UserRepository)userRepository).queryByUsername(username);
        if (user == null) {
            return UserLoginCheck.notValid(String.format("用户：%s不存在", username));
        }
        return MD5Utils.string2MD5(password).equals(user.getPassword()) ? UserLoginCheck.valid() :
                UserLoginCheck.notValid(String.format("用户:%s密码:%s不对", username, password));
    }

    @Cacheable("userByOpenId")
    public User queryByOpenId(String openId) {
        return ((UserRepository)userRepository).queryByOpenId(openId);
    }
}
