/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IDatabaseUtilsDao {

    void printTableNames() throws SQLException;

    List<String> getTableColumnNames(String tableName) throws SQLException;
    
    List<String> getTableNames() throws SQLException;
    
    List<List<Object>> getTableData(String tableName) throws SQLException;
}
