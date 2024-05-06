package com.example.demo.service.impl;

import com.example.demo.exception.CommonServiceException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.CommonService;
import com.example.demo.service.RoleService;

import java.util.List;

public class RoleServiceImpl extends CommonService<Role> implements RoleService {
    @Override
    public Role save(Role role) throws CommonServiceException {
        return null;
    }

    @Override
    public boolean delete(Role role) throws CommonServiceException {
        return false;
    }

    @Override
    public List<Role> findAll() throws CommonServiceException {
        return null;
    }

    @Override
    public Role update(Role role) throws CommonServiceException {
        return null;
    }
}
