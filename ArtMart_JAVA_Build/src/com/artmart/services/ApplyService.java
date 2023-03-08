package com.artmart.services;

import com.artmart.interfaces.IApplyDao;
import com.artmart.interfaces.IApplyService;
import com.artmart.models.Apply;

import java.sql.SQLException;
import java.util.List;

public class ApplyService implements IApplyService {

    private final IApplyDao applyDao;

    public ApplyService(IApplyDao applyDao) {
        this.applyDao = applyDao;
    }

    public ApplyService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createApply(Apply apply) throws SQLException {
        return applyDao.createApply(apply);
    }

    @Override
    public boolean updateApply(int id, Apply apply) throws SQLException {
        return applyDao.updateApply(id, apply);
    }

    @Override
    public int deleteApply(int id) throws SQLException {
        return applyDao.deleteApply(id);
    }

    @Override
    public Apply getApplyById(int applyId) throws SQLException {
        return applyDao.getApplyById(applyId);
    }

    @Override
    public List<Apply> getAllApplies() throws SQLException {
        return applyDao.getAllApplies();
    }

    @Override
    public List<Apply> getAppliesByCustomProductId(int customProductId) throws SQLException {
        return applyDao.getAppliesByCustomProductId(customProductId);
    }
}
