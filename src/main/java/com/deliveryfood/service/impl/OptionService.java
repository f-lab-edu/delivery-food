package com.deliveryfood.service.impl;

import com.deliveryfood.dto.OptionDto;
import com.deliveryfood.mapper.OptionMapper;
import com.deliveryfood.controller.model.request.OptionRequest;
import com.deliveryfood.service.IOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OptionService implements IOptionService {

    private final OptionMapper optionMapper;

    @Override
    public void createOption(OptionRequest optionRequest) {
        log.trace("createOption 서비스 호출");
        OptionDto optionDto = OptionDto.builder()
                .optionId(optionRequest.getOptionId())
                .menuId(optionRequest.getMenuId())
                .name(optionRequest.getName())
                .build();
        optionMapper.createOption(optionDto);
    }

    @Override
    public void deleteOptionById(OptionRequest optionRequest) {
        log.trace("deleteOptionById 서비스 호출");
        OptionDto optionDto = OptionDto.builder()
                .optionId(optionRequest.getOptionId())
                .build();
        optionMapper.deleteOptionById(optionDto);
    }

    @Override
    public List<OptionRequest> findOptionById(OptionRequest optionRequest) {
        log.trace("findOptionById 서비스 호출");
        OptionDto optionDto = OptionDto.builder()
                .menuId(optionRequest.getMenuId())
                .build();
        return optionMapper.findOptionById(optionDto);
    }
}
