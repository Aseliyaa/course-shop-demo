package com.example.demo.repository;

import com.example.demo.model.ConsultationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationFormRepository extends JpaRepository<ConsultationForm, Long> {
}
