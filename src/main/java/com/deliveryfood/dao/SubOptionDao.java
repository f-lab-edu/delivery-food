package com.deliveryfood.dao;

import com.deliveryfood.dto.SubOptionDto;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SubOptionDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public void createSubOption(SubOptionDto subOptionDto) {
        log.trace("createSubOption DAO 호출");
        sqlSessionTemplate.insert("com.deliveryfood.mapper.SubOptionMapper.createSubOption", subOptionDto);
    }
    public void deleteSubOptionById(SubOptionDto subOptionDto) {
        log.trace("deleteSubOptionById DAO 호출");
        sqlSessionTemplate.delete("com.deliveryfood.mapper.SubOptionMapper.deleteSubOptionById", subOptionDto);
    }

    public List<SubOptionRequest> findSubOptionById(SubOptionDto subOptionDto) {
        log.trace("findSubOptionById DAO 호출");
        return sqlSessionTemplate.selectList("com.deliveryfood.mapper.SubOptionMapper.findSubOptionById", subOptionDto);
    }
}
