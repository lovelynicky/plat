package com.xiangzhu.plat.service.business;

import com.xiangzhu.plat.domain.business.MutualAidRule;
import com.xiangzhu.plat.repository.base.BaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lqli on 2017/7/19 9:48.
 * 互助规则业务service
 *
 * @author lqli
 */
@Service
public class MutualAidRuleService {
    /**
     * mutualAidRuleRepository
     */
    @Resource(name = "mutualAidRuleRepository")
    private BaseRepository<MutualAidRule> mutualAidRuleRepository;

    /**
     * id查询
     * @param id id
     * @return MutualAidRule
     */
    public MutualAidRule queryById(long id) {
        return mutualAidRuleRepository.queryById(id);
    }
}
