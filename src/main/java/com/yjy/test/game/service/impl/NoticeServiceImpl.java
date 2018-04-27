package com.yjy.test.game.service.impl;


import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.base.Constants;
import com.yjy.test.game.dao.NoticeDao;
import com.yjy.test.game.entity.Notice;
import com.yjy.test.game.service.NoticeService;
import com.yjy.test.game.util.APICodeUtils;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.WebException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeServiceImpl extends BaseServiceImpl<Notice, Integer> implements
        NoticeService {

    @Autowired
    public void setNoticeDao(NoticeDao dao) {
        super.setDao(dao);
    }

    public NoticeDao getNoticeDao() {
        return (NoticeDao) super.getDao();
    }

    @Override
    public Notice findLastOne(String type) throws WebException {
        Notice notice = new Notice();
        if (StringUtils.isBlank(Notice.TYPE_NOTICE))
            type = Notice.TYPE_NOTICE;
        notice.setType(type);
        return findFirst(notice, new Sort.Order(Sort.Direction.DESC, "id"));
    }

    @Override
    public Notice saveOne(Notice notice) throws WebException {
        notice = save(notice);
        //需要广播的处理下
        if (Notice.TYPE_BROADCAST.equals(notice.getType())) {
            String url = FrontUtils.configParame(Constants.CONFIG_BROADCAST_URL);
            boolean succ = APICodeUtils.sendBroadcast(url, notice.getContent());
            if (!succ) {
                throw new WebException("发送广播发生错误");
            }
        }
        return notice;
    }

    @Override
    public Notice updateOne(Notice notice) throws WebException {
        notice = (Notice) update(notice);
        //需要广播的处理下
        if (Notice.TYPE_BROADCAST.equals(notice.getType())) {
            String url = FrontUtils.configParame(Constants.CONFIG_BROADCAST_URL);
            boolean succ = APICodeUtils.sendBroadcast(url, notice.getContent());
            if (!succ) {
                throw new WebException("发送广播发生错误");
            }
        }
        return notice;
    }
}
