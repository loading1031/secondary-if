package com.secondaryif.demo.repository;

import com.secondaryif.demo.domain.Upload;
import com.secondaryif.demo.domain.mapping.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike,Long> {
    Integer countByUpload(Upload upload);
}
