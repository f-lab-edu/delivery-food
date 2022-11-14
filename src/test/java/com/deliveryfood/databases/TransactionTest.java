package com.deliveryfood.databases;

import com.deliveryfood.dto.MenuDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TransactionTest {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @DisplayName("선언적 트랜잭션을 적용 후 스프링 트랜잭션이 활성 상태인지 감지")
    @Transactional
    void txTest() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    @DisplayName("선언적 트랜잭션 미적용 후 스프링 트랜잭션이 비활성 상태인지 감지")
    void txTest2() {
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    @DisplayName("트랜잭션 롤백을 통한 원자성 확인")
    void txRollbackTest() {
        // given
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        MenuDto menuDto = MenuDto.builder()
                .menuId("menuIdToRollbackTest RuntimeException")
                .restaurantId("restaurantIdToRollbackTest_Unchecked")
                .name("test")
                .build();
        sqlSessionTemplate.insert("com.deliveryfood.mapper.MenuMapper.createMenuById", menuDto);

        // when
        transactionManager.rollback(status);

        // then
        menuDto = MenuDto.builder()
                .restaurantId("restaurantIdToRollbackTest_Unchecked")
                .build();
        assertEquals(0, sqlSessionTemplate.selectList("com.deliveryfood.mapper.MenuMapper.findMenuById", menuDto.getRestaurantId()).size());
    }
}