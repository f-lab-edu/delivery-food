package com.deliveryfood.mapper;

import com.deliveryfood.dto.SubOptionDto;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubOptionMapper {

    void createSubOption(SubOptionDto subOptionDto);
    void deleteSubOptionById(SubOptionDto subOptionDto);
    List<SubOptionRequest> findSubOptionById(SubOptionDto subOptionDto);
}
