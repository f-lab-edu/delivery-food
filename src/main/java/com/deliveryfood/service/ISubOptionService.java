package com.deliveryfood.service;

import com.deliveryfood.controller.model.request.SubOptionRequest;

import java.util.List;

public interface ISubOptionService {

    void createSubOption(SubOptionRequest subOptionRequest);
    void deleteSubOptionById(SubOptionRequest subOptionRequest);
    List<SubOptionRequest> findSubOptionById(SubOptionRequest subOptionRequest);
}
