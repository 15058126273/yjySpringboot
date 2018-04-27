package com.yjy.test.base;

import com.yjy.test.util.hibernate.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity, L extends Serializable> {

    /**
     * 保存对象
     *
     * @param entity 实体对象
     * @return 操作信息
     */
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    /**
     * 根据ID删除记录
     *
     * @param id 记录ID
     */
    void deleteById(L id);

    /**
     * 根据ID数组删除记录，当发生异常时，操作终止并回滚
     *
     * @param ids 记录ID数组
     * @return 删除的对象
     */
    void deleteById(L[] ids);

    /**
     * 保存并刷新对象，避免many-to-one属性不完整
     *
     * @param entity
     */
    T saveAndRefresh(T entity);

    /**
     * 通过ID查找对象
     *
     * @param id 记录的ID
     * @return 实体对象
     */
    T findById(L id);

    T load(L id);

    T findByProperty(String property, Object value);

    List<T> findListByProperty(String property, Object value);

    /**
     * 根据属性查找
     * @param propertyName 属性
     * @param value 值
     * @param anywhere 是否模糊匹配
     * @return
     */
    List<T> findListByProperty(String propertyName, Object value, boolean anywhere);

    /**
     * 查找所有对象
     *
     * @return 对象列表
     */
    List<T> findAll();

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 条数
     * @param orders 排序规则
     * @return 分页列表
     */
    Pagination findAllPage(int pageNo, int pageSize, Sort.Order... orders);

    List<T> findList(T entity, Sort.Order... orders);

    List<T> findList(T entity, int pageNo, int pageSize, Sort.Order... orders);

    Pagination findListPage(T entity, int pageNo, int pageSize, Sort.Order... orders);

    T findLast();

    T findFirst(T entity, Sort.Order... orders);

    long findAllCount();

    long findCount(T entity);

}
