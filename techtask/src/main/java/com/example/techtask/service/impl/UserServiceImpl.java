package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public User findUser() {

        String sql = "SELECT u.* FROM users u " +
                "JOIN orders o ON o.user_id = u.id " +
                "WHERE o.order_status = 'DELIVERED' " +
                "AND EXTRACT(YEAR FROM o.created_at) = 2003 " +
                "GROUP BY u.id, u.email " +
                "ORDER BY SUM(o.price * o.quantity) DESC " +
                "LIMIT 1";

        return (User) entityManager.createNativeQuery(sql, User.class)
                .getSingleResult();
    }

    @Transactional
    @Override
    public List<User> findUsers() {
        String sql = "SELECT DISTINCT u.* FROM users u " +
                "JOIN orders o ON o.user_id = u.id " +
                "WHERE o.order_status = 'PAID' " +
                "AND EXTRACT(YEAR FROM o.created_at) = 2010";

        return entityManager.createNativeQuery(sql, User.class)
                .getResultList();
    }
}
