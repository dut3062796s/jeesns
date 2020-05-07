package com.lxinet.jeesns.service.picture;

import com.lxinet.jeesns.core.consts.AppTag;
import com.lxinet.jeesns.core.dto.Result;
import com.lxinet.jeesns.core.enums.MessageType;
import com.lxinet.jeesns.core.model.Page;
import com.lxinet.jeesns.dao.picture.IPictureDao;
import com.lxinet.jeesns.model.member.Member;
import com.lxinet.jeesns.model.picture.Picture;
import com.lxinet.jeesns.service.member.MessageService;
import com.lxinet.jeesns.utils.PictureUtil;
import com.lxinet.jeesns.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author zchuanzhao
 * @date 2017/3/7
 */
@Service
public class PictureService {
    @Resource
    private IPictureDao pictureDao;
    @Resource
    private PictureFavorService pictureFavorService;
    @Resource
    private MessageService messageService;

    public List<Picture> find(Integer foreignId) {
        return pictureDao.find(foreignId);
    }

    public Picture findById(Integer pictureId,int loginMemberId) {
        return pictureDao.findById(pictureId,loginMemberId);
    }

    public Result<Picture> listByPage(Page page, int loginMemberId) {
        List<Picture> list = pictureDao.list(page,loginMemberId);
        Result model = new Result(0, page);
        model.setData(list);
        return model;
    }

    public Result<Picture> listByAlbum(Page page, Integer pictureAlbumId, int loginMemberId) {
        List<Picture> list = pictureDao.listByAlbum(page,pictureAlbumId,loginMemberId);
        Result model = new Result(0, page);
        model.setData(list);
        return model;
    }

    public int deleteByForeignId(HttpServletRequest request, Integer foreignId) {
        List<Picture> pictures = this.find(foreignId);
        PictureUtil.delete(request,pictures);
        return pictureDao.deleteByForeignId(foreignId);
    }

    public boolean delete(HttpServletRequest request, Integer pictureId) {
        Picture picture = this.findById(pictureId,0);
        PictureUtil.delete(request,picture);
        return pictureDao.deleteById(pictureId, Picture.class) == 1;
    }

    public int save(Picture picture) {
        return pictureDao.saveObj(picture);
    }

    public int update(Integer foreignId, String ids,String description) {
        if(StringUtils.isNotEmpty(ids)){
            String[] idsArr = ids.split(",");
            return pictureDao.update(foreignId, idsArr,description);
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result favor(Member loginMember, int pictureId) {
        String message;
        Result<Integer> result;
        Picture picture = this.findById(pictureId,loginMember.getId());
        if(pictureFavorService.find(pictureId,loginMember.getId()) == null){
            //增加
            pictureDao.favor(pictureId,1);
            picture.setFavorCount(picture.getFavorCount() + 1);
            pictureFavorService.save(pictureId,loginMember.getId());
            message = "点赞成功";
            result = new Result(0,message);
            //点赞之后发送系统信息
            messageService.diggDeal(loginMember.getId(),picture.getMemberId(), AppTag.PICTURE, MessageType.PICTURE_ZAN,pictureId);
        }else {
            //减少
            pictureDao.favor(pictureId,-1);
            picture.setFavorCount(picture.getFavorCount() - 1);
            pictureFavorService.delete(pictureId,loginMember.getId());
            message = "取消赞成功";
            result = new Result(1,message);
        }
        result.setData(picture.getFavorCount());
        return result;
    }
}
