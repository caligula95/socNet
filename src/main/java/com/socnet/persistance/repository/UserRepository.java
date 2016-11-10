package com.socnet.persistance.repository;

import com.socnet.persistance.entities.User;
import org.springframework.stereotype.Component;

/**
 * @author Ruslan Lazin
 */
public interface UserRepository {
    User findByUsername(String login);

    User findById(Long userId);

    User update(User user);
}