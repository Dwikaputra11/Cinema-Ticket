package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = """
      select t from Token t inner join User u
      on t.user.id = u.id
      where u.id = :id and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    @Modifying
    @Query(value = "delete from Token t where (t.expired = true or t.revoked = true)")
    void deleteTokenByExpiredIsTrue();

    @Transactional
    @Modifying
    @Query(value = "select t from Token t where (t.expired = true or t.revoked = true)")
    List<Token> findAllByTokenExpiredIsTrue();
}
