package com.lxinet.jeesns.web.front;

import com.lxinet.jeesns.core.controller.BaseController;
import com.lxinet.jeesns.service.member.ScoreDetailService;
import com.lxinet.jeesns.utils.MemberUtil;
import com.lxinet.jeesns.interceptor.UserLoginInterceptor;
import com.lxinet.jeesns.core.annotation.Before;
import com.lxinet.jeesns.core.dto.Result;
import com.lxinet.jeesns.core.model.Page;
import com.lxinet.jeesns.model.member.Member;
import com.lxinet.jeesns.model.member.ScoreDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by zchuanzhao on 2017/4/7.
 */
@Controller("scoreDetailFrontController")
@RequestMapping("/member/scoreDetail")
@Before(UserLoginInterceptor.class)
public class ScoreDetailController extends BaseController {
    private static final String INDEX_FTL_PATH = "/member/scoreDetail/";
    @Resource
    private ScoreDetailService scoreDetailService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        Page page = new Page(request);
        Result<ScoreDetail> result = scoreDetailService.list(page,loginMember.getId());
        model.addAttribute("model", result);
        return INDEX_FTL_PATH + "list";
    }
}
