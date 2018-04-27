package com.yjy.test.base;

import com.yjy.test.util.hibernate.Finder;
import com.yjy.test.util.hibernate.Pagination;
import org.hibernate.Query;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

// 这里不能加@Service注解, 否则会无法获取泛型T的Class
public class BaseServiceImpl<T extends BaseEntity, L extends Serializable>
        extends BaseClass implements BaseService<T, L> {

    //@Autowired和@PersistenceContext注解任取一
    @PersistenceContext
    protected EntityManager em;

    @Override
    public T save(T entity) {
        return dao.save(entity);
    }

    @Override
    public T update(T entity) {
        return dao.saveAndFlush(entity);
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(L id) {
        dao.delete(id);
    }

    @Override
    public void deleteById(L[] ids) {
        if (ids != null) {
            for (L id : ids) {
                dao.delete(id);
            }
        }
    }

    @Override
    public T saveAndRefresh(T entity) {
        return dao.saveAndFlush(entity);
    }

    @Override
    public T findById(L id) {
        return dao.findOne(id);
    }

    @Override
    public T load(L id) {
        return dao.getOne(id);
    }

    @Override
    public T findByProperty(String property, Object value) {
        List<T> list = findListByProperty(property, value);
        return list != null ? list.get(0) : null;
    }

    @Override
    public List<T> findListByProperty(String property, Object value) {
        return findListByProperty(property, value, false);
    }

    @Override
    public List<T> findListByProperty(String property, Object value, boolean anywhere) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(getPersistentClass());
        Root<T> root = query.from(getPersistentClass());
        Predicate predicate;
        if (anywhere)
            predicate = criteriaBuilder.like(root.get(property), "%" + value.toString() + "%");
        else
            predicate = criteriaBuilder.equal(root.get(property), value);
        query.where(predicate);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public Pagination findAllPage(int pageNo, int pageSize, Sort.Order... orders) {
        Sort sort = orders != null && orders.length > 0 ? new Sort(orders) : null;
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);
        Page<T> page = dao.findAll(pageable);
        Pagination pagination = new Pagination(pageNo, pageSize, (int) page.getTotalElements());
        pagination.setList(page.getContent());
        return pagination;
    }

    @Override
    public List<T> findList(T entity, Sort.Order... orders) {
        Example<T> example = Example.of(entity);
        if (orders != null && orders.length > 0)
            return dao.findAll(example, new Sort(orders));
        else
            return dao.findAll(example);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findList(T entity, int pageNo, int pageSize, Sort.Order... orders) {
        Pagination pagination = findListPage(entity, pageNo, pageSize, orders);
        if (pagination != null) {
            return (List<T>)pagination.getList();
        }
        return new ArrayList<>();
    }

    @Override
    public Pagination findListPage(T entity, int pageNo, int pageSize, Sort.Order... orders) {
        Example<T> example = Example.of(entity);
        Sort sort = orders != null && orders.length > 0 ? new Sort(orders) : null;
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);
        Page<T> page = dao.findAll(example, pageable);
        Pagination pagination = new Pagination(pageNo, pageSize, (int) page.getTotalElements());
        pagination.setList(page.getContent());
        return pagination;
    }

    @Override
    public T findLast() {
        return dao.findTopByOrderByIdDesc();
    }

    @Override
    public T findFirst(T entity, Sort.Order... orders) {
        List<T> list = findList(entity, 1, 1, orders);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public long findAllCount() {
        return dao.count();
    }

    @Override
    public long findCount(T entity) {
        Example<T> example = Example.of(entity);
        return dao.count(example);
    }


    @SuppressWarnings("rawtypes")
    protected Pagination find(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = em.createQuery(finder.getOrigHql()).unwrap(Query.class);
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        List list = query.list();
        p.setList(list);
        return p;
    }

    /**
     * 通过count查询获得本次查询所能获得的对象总数.
     *
     * @param finder
     * @return
     */
    protected int countQueryResult(Finder finder) {
        Query query = em.createQuery(finder.getRowCountHql()).unwrap(Query.class);
        finder.setParamsToQuery(query);
        return ((Number) query.iterate().next()).intValue();
    }

    /*************************************************************************/
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.persistentClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    private BaseJpaRepository<T, L> dao;

    public void setDao(BaseJpaRepository<T, L> dao) {
        this.dao = dao;
    }

    protected BaseJpaRepository<T, L> getDao() {
        return this.dao;
    }

    private Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
