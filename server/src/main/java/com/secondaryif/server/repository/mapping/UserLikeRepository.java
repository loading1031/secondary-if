package com.secondaryif.server.repository.mapping;

import com.secondaryif.server.domain.Upload;
import com.secondaryif.server.domain.mapping.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike,Long> {
    Integer countByUpload(Upload upload);
}
