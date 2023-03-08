
package com.artmart.interfaces;
import com.artmart.models.Apply;
import java.sql.SQLException;
import java.util.List;
public interface IApplyDao {
    
     int createApply(Apply apply) throws SQLException;

    boolean updateApply(int id, Apply apply) throws SQLException;

    int deleteApply(int id) throws SQLException;

    Apply getApplyById(int applyId) throws SQLException;

    List<Apply> getAllApplies() throws SQLException;

    List<Apply> getAppliesByCustomProductId(int customProductId) throws SQLException;
}
