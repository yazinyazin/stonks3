package net.yazin.stonks.User.data.repository;

import net.yazin.stonks.User.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
