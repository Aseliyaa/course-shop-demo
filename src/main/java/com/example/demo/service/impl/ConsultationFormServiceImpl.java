package com.example.demo.service.impl;

import com.example.demo.exception.CommonServiceException;
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
        return false;
    }

    @Override
    public List<ConsultationForm> findAll() throws CommonServiceException {
        return null;
    }

    @Override
    public ConsultationForm update(ConsultationForm form) throws CommonServiceException {
        return null;
    }
}
