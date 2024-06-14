package com.sebn.spring.login.repository;

import com.sebn.spring.login.models.ERole;
import com.sebn.spring.login.models.Role;
import com.sebn.spring.login.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
