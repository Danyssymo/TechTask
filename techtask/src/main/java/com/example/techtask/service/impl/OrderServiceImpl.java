package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Order findOrder() {
        String sql = "SELECT * FROM orders " +
                "WHERE quantity > 1 " +
                "ORDER BY created_at DESC " +
                "LIMIT 1";

        return (Order) entityManager.createNativeQuery(sql, Order.class)
                .getSingleResult();
    }

    @Transactional
    @Override
    public List<Order> findOrders() {
        String sql = "SELECT o.* FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE u.user_status = 'ACTIVE' " +
                "ORDER BY o.created_at ASC";

        return entityManager.createNativeQuery(sql, Order.class)
                .getResultList();
    }
}
