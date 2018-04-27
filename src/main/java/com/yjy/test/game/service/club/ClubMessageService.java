package com.yjy.test.game.service.club;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.club.ClubMessage;

import java.util.List;


/**
 * 俱乐部消息管理
 *
 * @author yjy
 * Created on 2017年12月6日 上午10:43:15
 */
public interface ClubMessageService extends BaseService<ClubMessage, Long> {

    /**
     * 新建一个消息
     *
     * @param sendId     发送人id
     * @param receiveId  接收人id
     * @param clubId     俱乐部id
     * @param clubUserId 成员id
     * @param type       消息类型
     * @param result     审核结果
     * @param remark     备注
     * @author yjy
     * Created on 2017年12月6日 上午11:16:57
     */
    void addMessage(Long sendId, Long receiveId, Long clubId, Long clubUserId, Integer type, Integer result, String remark);

    /**
     * 获取我的消息列表
     *
     * @param userId   用户id
     * @param status   消息状态
     * @param pageNo   页号
     * @param pageSize 条数
     * @return 消息列表
     * @author yjy
     * Created on 2017年12月6日 上午11:50:40
     */
    List<ClubMessage> findMyList(Long userId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 群主审核消息
     *
     * @param clubMessage 申请消息
     * @param result      结果
     * @author yjy
     * Created on 2017年12月6日 下午1:47:42
     */
    ClubMessage check(ClubMessage clubMessage, Integer result);
}
