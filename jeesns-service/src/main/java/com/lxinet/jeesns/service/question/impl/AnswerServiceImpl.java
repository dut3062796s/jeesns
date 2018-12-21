package com.lxinet.jeesns.service.question.impl;

import com.lxinet.jeesns.core.conditions.SqlWrapper;
import com.lxinet.jeesns.core.dto.ResultModel;
import com.lxinet.jeesns.core.exception.OpeErrorException;
import com.lxinet.jeesns.core.model.Page;
import com.lxinet.jeesns.core.service.impl.BaseServiceImpl;
import com.lxinet.jeesns.core.utils.HtmlUtil;
import com.lxinet.jeesns.core.utils.PageUtil;
import com.lxinet.jeesns.core.utils.StringUtils;
import com.lxinet.jeesns.core.utils.ValidUtill;
import com.lxinet.jeesns.dao.question.IAnswerDao;
import com.lxinet.jeesns.dao.question.IQuestionDao;
import com.lxinet.jeesns.model.member.Member;
import com.lxinet.jeesns.model.question.Answer;
import com.lxinet.jeesns.model.question.Question;
import com.lxinet.jeesns.service.question.IAnswerService;
import com.lxinet.jeesns.service.question.IQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zchuanzhao on 2018/12/21.
 */
@Service("answerService")
public class AnswerServiceImpl extends BaseServiceImpl<Answer> implements IAnswerService {

    @Resource
    private IAnswerDao answerDao;


    @Override
    public ResultModel<Answer> listByQuestion(Integer questionId) {
        List<Answer> list = answerDao.listByQuestion(PageUtil.getPage(), questionId);
        ResultModel model = new ResultModel(0,PageUtil.getPage());
        model.setData(list);
        return model;
    }

    @Override
    public Answer findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public boolean save(Answer answer) {
        return super.save(answer);
    }

    @Override
    public boolean update(Member loginMember, Answer answer) {

        return super.update(answer);
    }

    @Override
    public boolean delete(Member loginMember, Integer id) {
        Answer findAnswer = findById(id);
        if(loginMember.getId().intValue() == findAnswer.getMember().getId().intValue() || loginMember.getIsAdmin() > 0){
            return super.deleteById(id);
        }
        throw new OpeErrorException("没有权限");
    }

}
