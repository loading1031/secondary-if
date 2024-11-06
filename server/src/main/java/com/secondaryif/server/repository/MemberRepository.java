package com.secondaryif.server.repository;

import com.secondaryif.server.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);
    Optional<Member> getMemberByName(String name);
}
