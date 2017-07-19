package com.xiangzhu.plat.service.business;

import com.xiangzhu.plat.domain.business.MutualAidPlan;
import com.xiangzhu.plat.repository.base.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lqli on 2017/7/19 9:08.
 * 互助计划业务service
 *
 * @author lqli
 */
@Service
public class MutualAidPlanService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MutualAidPlanService.class);

    @Resource(name = "mutualAidPlanRepository")
    private BaseRepository<MutualAidPlan> mutualAidPlanRepository;

    /**
     * 查询全部互助计划
     * 不分页
     *
     * @return List<MutualAidPlan>
     */
    public List<MutualAidPlan> queryAll() {
        try {
            return mutualAidPlanRepository.queryAll();
        } catch (Throwable e) {
            logger.error("查询全部互助计划异常", e);
            return new ArrayList<MutualAidPlan>();
        }
    }
}
