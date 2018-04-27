package com.yjy.test.game.controller.front;

import com.yjy.test.game.entity.User;
import com.yjy.test.game.entity.club.ClubMessage;
import com.yjy.test.game.redis.RedisConfig;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.service.club.ClubMessageService;
import com.yjy.test.util.hibernate.Pagination;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 首页
 *
 * @Author yjy
 * @Date 2018-04-24 14:58
 */
@Controller
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    @Resource
    private UserService userService;
    @Resource
    private RoomUserService roomUserService;

    @RequestMapping(value = "/")
    public String welcome() {
//        User user = new User("test save", "test save");
//        user = userService.save(user);
//        System.out.println(user);
//
//        Long id = user.getId();
//
//        user.setHeadImg("test update");
//        user = userService.update(user);
//        System.out.println(user);
//
//        List<User> allList = userService.findAll();
//        System.out.println(allList);
//
//        User user1 = userService.findById(id);
//        System.out.println(user1);
//
//        User user2 = userService.load(id);
//        System.out.println(user2);
//
//        user2.setOpenId("test saveAndRefresh success");
//        user2 = userService.saveAndRefresh(user2);
//        System.out.println(user2);
//
//        long allCount = userService.findAllCount();
//        System.out.println(allCount);
//
//        User user3 = new User();
//        user3.setNickName("test1");
//        long count = userService.findCount(user3);
//        System.out.println(count);
//
//        User user4 = userService.findByProperty("nickName", "test save");
//        System.out.println(user4);

//        List<User> list = userService.findListByProperty("nickName", "test", true);
//        System.out.println(list);
//
//        User user5 = new User();
//        user5.setNickName("test1");
//        List<User> list1 = userService.findList(user5);
//        System.out.println(list1);
//
//        Page<User> page = userService.findAllPage(2, 4, new Sort.Order(Sort.Direction.DESC, "id"));
//        System.out.println(page);
//        log.info("totalPage: {}, totalCount: {}, content: {}, iterator: {}",
//                page.getTotalPages(), page.getTotalElements(), page.getContent(), page.iterator());
//
//
//        User user6 = new User();
//        user6.setNickName("test1");
//        Page<User> page1 = userService.findListPage(user6, 2, 2, new Sort.Order(Sort.Direction.DESC, "id"));
//        System.out.println(page1);

//        userService.deleteById(26L);
//        User user7 = new User();
//        user7.setId(1L);
//        userService.delete(user7);
//        System.out.println("deleted...");


//        Pagination p = userService.findAllPage(1, 10);
//        Pagination p = roomUserService.findAllPage(1, 10);
//        System.out.println(p);

        List<ClubMessage> list = clubMessageService.findMyList(222L, null, 1, 100);
        System.out.println(list);

        return "front/index";
    }

    @Resource
    private ClubMessageService clubMessageService;

}
