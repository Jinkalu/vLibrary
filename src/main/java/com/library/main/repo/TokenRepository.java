package com.library.main.repo;

import com.library.main.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(" select t from Token t inner join Users u on t.user.id=u.id where u.id=:userId and (t.expired=false or t.revoked=false) ")
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByAccessToken(String accessToken);
}