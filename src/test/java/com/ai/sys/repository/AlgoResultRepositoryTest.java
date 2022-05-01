package com.ai.sys.repository;

import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.AlgoResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlgoResultRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private AlgoResultRepository algoResultRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(algoRepository).isNotNull();
        assertThat(algoResultRepository).isNotNull();
    }


    @Test
    void verifyAlgoResultsAreCreated() {
        AlgoResult r1 = AlgoResult.builder().accuracy(3.6f)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        AlgoResult r2 = AlgoResult.builder().accuracy(3.6f)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Algo testalgo = Algo.builder()
                .name("testalgo")
                .algoResults(List.of(r1, r2))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Algo algo = algoRepository.save(testalgo);

        List<Long> allIds = algoResultRepository.findAll().stream()
                .map(AlgoResult::getId)
                .collect(Collectors.toList());

        algo.getAlgoResults().stream()
                .map(AlgoResult::getId)
                .forEach(id -> assertThat(allIds).asList().contains(id));
    }
}