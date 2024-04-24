package com.TFG.TFG.Respository;

import com.TFG.TFG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
