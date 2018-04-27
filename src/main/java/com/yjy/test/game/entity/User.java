package com.yjy.test.game.entity;

import com.yjy.test.base.BaseEntity;
import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户详细信息表
 *
 * @author wdy
 * @version ：2016年8月25日 上午9:31:47
 */
@Entity
@Table(name = "cg_user_detail")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1063923761052068119L;

    public static final int STATUS_OFFLINE = 0;
    public static final int STATUS_ONLINE = 1;

    public static final int SEX_MAN = 1; // 男
    public static final int SEX_WOMAN = 2; // 女
    public static final int SEX_NONE = 0; // 无

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // 与user的id一样
    private String nickName;
    private String headImg; //头像
    private Integer sex; // 性别
    private String openId;
    private String code; // 用户的编号
    private Date addTime; // 注册或首次登陆时间
    private Date updateTime;//

    public User() {}

    public User(String nickName, String code) {
        this.nickName = nickName;
        this.code = code;
        this.addTime = new Date();
        this.updateTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getOpenId() {
        return openId;
    }

    public String getCode() {
        return code;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", headImg='" + headImg + '\'' +
                ", sex=" + sex +
                ", openId='" + openId + '\'' +
                ", code='" + code + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
