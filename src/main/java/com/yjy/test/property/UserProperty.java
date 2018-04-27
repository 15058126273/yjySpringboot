package com.yjy.test.property;

import com.yjy.test.game.entity.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-18 14:14
 */
@Component
@ConfigurationProperties(prefix = "user")
@Validated
public class UserProperty {

    @Valid
    private final List<User> list = new ArrayList<>();

    public List<User> getList() {
        return this.list;
    }

}
