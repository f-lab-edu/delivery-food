package com.deliveryfood.service;

import com.deliveryfood.controller.model.request.OptionRequest;

import java.util.List;

public interface IOptionService {

    void createOption(OptionRequest optionRequest);
    void deleteOptionById(OptionRequest optionRequest);
    List<OptionRequest> findOptionById(OptionRequest optionRequest);
}
