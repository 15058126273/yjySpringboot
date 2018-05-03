package com.yjy.test.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * BaseJpaRepository
 *
 * @Author yjy
 * @Date 2018-04-25 15:55
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseEntity, L extends Serializable> extends JpaRepository<T, L> {

    public T findTopByOrderByIdDesc();

}
