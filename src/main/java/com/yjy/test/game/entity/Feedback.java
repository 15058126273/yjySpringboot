package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统公告信息管理
 *
 * @author wdy
 * @version ：2017年5月22日 上午11:21:07
 */
@Entity
@Table(name = "cg_feedback")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = -5112031460082296995L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;//用户id
    private String content;//反馈内容
    private Date addTime;//提交时间

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
