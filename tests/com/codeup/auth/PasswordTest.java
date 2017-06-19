/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class PasswordTest {
    @Test
    public void it_hashes_a_plain_text_password()
    {
        assertThat(password.toString(), is(not(plainTextPassword)));
        assertThat(password.toString().length(), is(60));
    }

    @Test
    public void it_verifies_it_matches_the_correct_plain_text_password()
    {
        assertThat(password.verify(plainTextPassword), is(true));
    }

    @Test
    public void it_fails_to_verify_a_non_matching_plain_text_password()
    {
        assertThat(password.verify("i do not match"), is(false));
    }

    @Test
    public void it_creates_a_password_from_an_existing_hash()
    {
        Password anotherPassword = Password.fromHash(this.password.toString());

        assertThat(password.equals(anotherPassword), is(true)); // hashes should be the same
    }


    private final String plainTextPassword = "iL0veMyJob";
    private final Password password = Password.fromPlainText(plainTextPassword);
}