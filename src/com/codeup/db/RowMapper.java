/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T> {
    T mapRow(ResultSet resultSet) throws SQLException;
    T newEntity(int id, Object[] parameters);
}
