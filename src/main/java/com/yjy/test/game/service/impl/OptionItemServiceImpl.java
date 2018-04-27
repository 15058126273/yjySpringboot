package com.yjy.test.game.service.impl;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.OptionItemDao;
import com.yjy.test.game.entity.OptionItem;
import com.yjy.test.game.service.OptionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class OptionItemServiceImpl extends BaseServiceImpl<OptionItem, Integer> implements
        OptionItemService {

    @Autowired
    public void setOptionItemDao(OptionItemDao dao) {
        super.setDao(dao);
    }

    public OptionItemDao getOptionItemDao() {
        return (OptionItemDao) super.getDao();
    }

    @Override
    public List<OptionItem> listByField(String field, Boolean isAll) {
        List<OptionItem> optionItems = new ArrayList<>();
        if (!isBlank(field)) {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<OptionItem> query = criteriaBuilder.createQuery(OptionItem.class);
            Root<OptionItem> root = query.from(OptionItem.class);
            Predicate predicate = criteriaBuilder.equal(root.get("field"), field);
            if (!isAll) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isUse"), 1));
            }
            query.where(predicate);
            optionItems.addAll(em.createQuery(query).getResultList());
        }
        return optionItems;
    }

    public List<String> getAllFieldName() {
        List<String> fieldNames = new LinkedList<>();
        List<OptionItem> optionItems = findAll();
        if (optionItems != null && !optionItems.isEmpty()) {
            for (OptionItem optionItem : optionItems) {
                fieldNames.add(optionItem.getFieldName());
            }
        }
        return fieldNames;
    }



}
