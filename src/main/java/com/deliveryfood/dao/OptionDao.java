package com.deliveryfood.dao;

import com.deliveryfood.dto.OptionDto;
import com.deliveryfood.controller.model.request.OptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OptionDao {

    private final SqlSessionTemplate sqlSessionTemplate;

    public void createOption(OptionDto optionDto) {
        log.trace("createOption DAO 호출");
        sqlSessionTemplate.insert("com.deliveryfood.mapper.OptionMapper.createOption", optionDto);
    }
    public void deleteOptionById(OptionDto optionDto) {
        log.trace("deleteOptionById DAO 호출");
        sqlSessionTemplate.delete("com.deliveryfood.mapper.OptionMapper.deleteOptionById", optionDto);
    }

    public List<OptionRequest> findOptionById(OptionDto optionDto) {
        log.trace("findOptionById DAO 호출");
        return sqlSessionTemplate.selectList("com.deliveryfood.mapper.OptionMapper.findOptionById", optionDto);
    }
}
