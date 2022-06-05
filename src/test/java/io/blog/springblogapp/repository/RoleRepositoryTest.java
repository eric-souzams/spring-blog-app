package io.blog.springblogapp.repository;

import io.blog.springblogapp.model.entity.AuthorityEntity;
import io.blog.springblogapp.model.entity.RoleEntity;
import io.blog.springblogapp.model.enums.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TestEntityManager entityManager;

    RoleEntity role;
    AuthorityEntity authority;

    @BeforeEach
    void setUp() {
        authority = AuthorityEntity.builder().name("READ_AUTHORITY").build();
        role = RoleEntity.builder().name(Roles.ROLE_USER.name()).authorities(Collections.singletonList(authority)).build();
    }

    @Test
    void test_find_by_name() {
        //given
        entityManager.persist(role);

        //act
        Optional<RoleEntity> result = roleRepository.findByName(Roles.ROLE_USER.name());

        //assert
        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isTrue();
    }
}
