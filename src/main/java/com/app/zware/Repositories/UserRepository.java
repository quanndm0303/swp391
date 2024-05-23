package com.app.zware.Repositories;

import com.app.zware.Entities.User;
import com.app.zware.Entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}

