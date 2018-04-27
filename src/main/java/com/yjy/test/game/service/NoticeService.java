package com.yjy.test.game.service;


import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.Notice;
import com.yjy.test.game.web.WebException;

/**
 * 系统公告的service层
 *
 * @author wdy
 * @version ：2017年5月22日 上午11:38:12
 */
public interface NoticeService extends BaseService<Notice, Integer> {

    /**
     * 获取最新的
     *
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年5月27日 上午10:38:57
     */
    public Notice findLastOne(String type) throws WebException;

    /**
     * 保存新的记录
     *
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年6月19日 下午4:38:10
     */
    public Notice saveOne(Notice notice) throws WebException;

    /**
     * 更新公告信息
     *
     * @param notice
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年6月19日 下午5:46:49
     */
    public Notice updateOne(Notice notice) throws WebException;
}
