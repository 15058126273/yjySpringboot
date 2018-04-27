package com.yjy.test.game.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.LogDao;
import com.yjy.test.game.entity.Log;
import com.yjy.test.game.service.LogService;
import com.yjy.test.util.hibernate.Pagination;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Service
@Transactional
public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements LogService {

    @Autowired
    public void setLogDao(LogDao dao) {
        super.setDao(dao);
    }

    public LogDao getLogDao() {
        return (LogDao) super.getDao();
    }

    @Override
    public Pagination findList(Date startDate, Date endDate, String itemTitle, String operator,
                               Integer pageNo, Integer pageSize, Integer type, Integer itemType) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Log> criteriaQuery = criteriaBuilder.createQuery(Log.class);
        Root<Log> root = criteriaQuery.from(Log.class);
        List<Predicate> predicates = new LinkedList<>();
        if (notBlank(operator))
            predicates.add(criteriaBuilder.like(root.get("operator"), "%" + operator + "%"));
        if (notBlank(itemTitle))
            predicates.add(criteriaBuilder.like(root.get("itemName"), "%" + operator + "%"));
        if (startDate != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("operateDate"), startDate));
        if (endDate != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("operateDate"), endDate));
        if (type != null)
            predicates.add(criteriaBuilder.equal(root.get("operateType"), type));
        if (itemType != null)
            predicates.add(criteriaBuilder.equal(root.get("itemType"), itemType));
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("operateDate")));
        int totalCount = em.createQuery(criteriaQuery).getResultList().size();
        List<Log> list = em.createQuery(criteriaQuery)
                .setFirstResult((pageNo-1)*pageSize)
                .setMaxResults(pageSize).getResultList();
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount);
        pagination.setList(list);
        return pagination;
    }
}
