package com.sebn.spring.login.repository;

import com.sebn.spring.login.models.Photo;
import com.sebn.spring.login.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
