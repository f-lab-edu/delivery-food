package com.deliveryfood.service.impl;

import com.deliveryfood.dao.SubOptionDao;
import com.deliveryfood.dto.SubOptionDto;
import com.deliveryfood.service.ISubOptionService;
import com.deliveryfood.service.model.SubOptionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubOptionService implements ISubOptionService {

    private final SubOptionDao subOptionDao;

    @Override
    public void createSubOption(SubOptionVO subOptionVO) {
        log.trace("createSubOption 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .optionId(subOptionVO.getOptionId())
                .menuId(subOptionVO.getMenuId())
                .subOptionId(subOptionVO.getSubOptionId())
                .build();
        subOptionDao.createSubOption(subOptionDto);
    }

    @Override
    public void deleteSubOptionById(SubOptionVO subOptionVO) {
        log.trace("deleteSubOptionById 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .subOptionId(subOptionVO.getSubOptionId())
                .build();
        subOptionDao.deleteSubOptionById(subOptionDto);
    }

    @Override
    public List<SubOptionVO> findSubOptionById(SubOptionVO subOptionVO) {
        log.trace("findSubOptionById 서비스 호출");
        SubOptionDto subOptionDto = SubOptionDto.builder()
                .optionId(subOptionVO.getOptionId())
                .menuId(subOptionVO.getMenuId())
                .build();
        List<SubOptionDto> subOptionDtoOutputList = subOptionDao.findSubOptionById(subOptionDto);

        List<SubOptionVO> subOptionOutputList = new ArrayList<>();
        for (SubOptionDto subOptionDtoOutput : subOptionDtoOutputList) {
            subOptionOutputList.add(SubOptionVO.builder()
                    .menuId(subOptionDtoOutput.getMenuId())
                    .optionId(subOptionDtoOutput.getOptionId())
                    .subOptionId(subOptionDtoOutput.getSubOptionId())
                    .name(subOptionDtoOutput.getName())
                    .price(subOptionDtoOutput.getPrice())
                    .build());
        }

        return subOptionOutputList;
    }

}
