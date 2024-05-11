package com.example.demo.service;

import com.example.demo.exception.ConsultationFormServiceException;
import com.example.demo.model.ConsultationForm;

import java.util.List;


public interface ConsultationFormService {

    List<ConsultationForm> findAllForms() throws ConsultationFormServiceException;

    ConsultationForm findFormById(long formId) throws ConsultationFormServiceException;

    void deleteConsultationForm(ConsultationForm form) throws ConsultationFormServiceException;
}
