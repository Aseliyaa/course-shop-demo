package com.example.demo.service;

import com.example.demo.exception.CommonServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.exception.UserServiceException;
import com.example.demo.model.AbstractModel;
import com.example.demo.model.User;

import java.util.List;

public abstract class CommonService<T extends AbstractModel> {
    public abstract T save(T t) throws CommonServiceException;
    public abstract boolean delete(T t) throws CommonServiceException;

    public abstract List<T> findAll() throws CommonServiceException;
    public abstract T update(T t) throws CommonServiceException;
}
