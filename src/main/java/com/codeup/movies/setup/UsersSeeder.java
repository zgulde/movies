/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.setup;

import com.codeup.auth.infrastructure.jdbc.JdbcUsers;
import com.codeup.auth.domain.identity.User;

import java.sql.Connection;

class UsersSeeder {
    private final Connection connection;

    UsersSeeder(Connection connection) {
        this.connection = connection;
    }

    void seed() {
        JdbcUsers users = new JdbcUsers(connection);
        users.add(User.signUp("admin", "password"));
    }
}
