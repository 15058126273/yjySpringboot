package com.yjy.test.util.hibernate;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Sort;

@SuppressWarnings("serial")
public class OrderBy extends Condition {
    private Sort.Direction orderType;

    protected OrderBy(String field, Sort.Direction orderType) {
        this.field = field;
        this.orderType = orderType;
    }

    public static OrderBy asc(String field) {
        return new OrderBy(field, Sort.Direction.ASC);
    }

    public static OrderBy desc(String field) {
        return new OrderBy(field, Sort.Direction.DESC);
    }

    public Order getOrder() {
        Order order = null;
        if (Sort.Direction.ASC == orderType) {
            order = Order.asc(getField());
        } else if (Sort.Direction.DESC == orderType) {
            order = Order.desc(getField());
        }
        return order;
    }

    public static Order[] asOrders(OrderBy[] orderBys) {
        if (orderBys != null) {
            Order[] orders = new Order[orderBys.length];
            for (int i = 0; i < orderBys.length; i++) {
                orders[i] = orderBys[i].getOrder();
            }
            return orders;
        } else {
            return null;
        }

    }

    public Sort.Direction getOrderType() {
        return orderType;
    }


    public static class OrderType {
        public static final Sort.Direction ASC = Sort.Direction.ASC;
        public static final Sort.Direction DESC = Sort.Direction.DESC;
    }

}
