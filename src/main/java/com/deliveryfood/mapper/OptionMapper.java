package com.deliveryfood.mapper;

import com.deliveryfood.dto.OptionDto;
import com.deliveryfood.controller.model.request.OptionRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    void createOption(OptionDto optionDto);
    void deleteOptionById(OptionDto optionDto);
    List<OptionRequest> findOptionById(OptionDto optionDto);
}
