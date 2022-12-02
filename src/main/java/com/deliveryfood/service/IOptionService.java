package com.deliveryfood.service;

import com.deliveryfood.service.model.OptionVO;

import java.util.List;

public interface IOptionService {

    void createOption(OptionVO optionVO);
    void deleteOptionById(OptionVO optionVO);
    List<OptionVO> findOptionById(OptionVO optionVO);
}
