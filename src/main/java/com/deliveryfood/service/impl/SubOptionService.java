package com.deliveryfood.service.impl;

import com.deliveryfood.dto.SubOptionDto;
import com.deliveryfood.mapper.SubOptionMapper;
import com.deliveryfood.controller.model.request.SubOptionRequest;
import com.deliveryfood.service.ISubOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubOptionService implements ISubOptionService {

    private final SubOptionMapper subOptionMapper;

    @Override
    public void createSubOption(SubOptionRequest subOptionRequest) {
        log.trace("createSubOption 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .optionId(subOptionRequest.getOptionId())
                .menuId(subOptionRequest.getMenuId())
                .subOptionId(subOptionRequest.getSubOptionId())
                .build();
        subOptionMapper.createSubOption(subOptionDto);
    }

    @Override
    public void deleteSubOptionById(SubOptionRequest subOptionRequest) {
        log.trace("deleteSubOptionById 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .subOptionId(subOptionRequest.getSubOptionId())
                .build();
        subOptionMapper.deleteSubOptionById(subOptionDto);
    }

    @Override
    public List<SubOptionRequest> findSubOptionById(SubOptionRequest subOptionRequest) {
        log.debug("findSubOptionById 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .optionId(subOptionRequest.getOptionId())
                .menuId(subOptionRequest.getMenuId())
                .build();
        return subOptionMapper.findSubOptionById(subOptionDto);
    }

}
