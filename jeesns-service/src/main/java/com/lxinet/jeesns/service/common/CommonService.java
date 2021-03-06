package com.lxinet.jeesns.service.common;

import com.lxinet.jeesns.dao.common.ICommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zchuanzhao on 2017/2/6.
 */
@Service("commonService")
public class CommonService {
    @Resource
    private ICommonDao commonDao;

    public String getMysqlVsesion() {
        return commonDao.getMysqlVsesion();
    }
}
