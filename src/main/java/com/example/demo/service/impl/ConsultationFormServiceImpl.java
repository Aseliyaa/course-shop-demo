package com.example.demo.service.impl;

import com.example.demo.exception.CommonServiceException;
import com.example.demo.exception.ConsultationFormServiceException;
import com.example.demo.model.ConsultationForm;
import com.example.demo.repository.ConsultationFormRepository;
import com.example.demo.service.CommonService;
import com.example.demo.service.ConsultationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationFormServiceImpl extends CommonService<ConsultationForm> implements ConsultationFormService {
    private final ConsultationFormRepository consultationFormRepository;

    @Autowired
    public ConsultationFormServiceImpl(ConsultationFormRepository consultationFormRepository) {
        this.consultationFormRepository = consultationFormRepository;
    }

    @Override
    public ConsultationForm save(ConsultationForm form) throws CommonServiceException {
        return consultationFormRepository.save(form);
    }

    @Override
    public boolean delete(ConsultationForm form) throws CommonServiceException {
        consultationFormRepository.delete(form);
        return false;
    }

    @Override
    public List<ConsultationForm> findAll() throws CommonServiceException {
        return consultationFormRepository.findAll();
    }

    @Override
    public ConsultationForm update(ConsultationForm form) throws CommonServiceException {
        return null;
    }

    @Override
    public List<ConsultationForm> findAllForms() throws ConsultationFormServiceException {
        try {
            return findAll();
        } catch (CommonServiceException e) {
            throw new ConsultationFormServiceException(e);
        }
    }

    @Override
    public ConsultationForm findFormById(long formId) throws ConsultationFormServiceException {
        return consultationFormRepository.findById(formId).get();
    }

    @Override
    public void deleteConsultationForm(ConsultationForm form) throws ConsultationFormServiceException {
        try {
            delete(form);
        } catch (CommonServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
