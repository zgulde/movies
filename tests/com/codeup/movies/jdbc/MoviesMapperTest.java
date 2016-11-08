package com.codeup.movies.jdbc;
/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */

import com.codeup.movies.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MoviesMapperTest {

    private MoviesMapper mapper;

    @Before
    public void initMapper() {
        mapper = new MoviesMapper();
    }

    @Test
    public void it_maps_correctly_a_movie() {
        List<Object> values = new ArrayList();
        values.addAll(Arrays.asList(5L, "Shrek", 3, "shrek.png"));

        Movie movie = mapper.mapRow(values);

        assertEquals(5L, movie.id());
        assertEquals("Shrek", movie.title());
        assertEquals(3, movie.rating());
        assertEquals("shrek.png", movie.thumbnail());
    }

    @Test
    public void it_maps_correctly_a_movie_withaout_a_thumbnail() {
        List<Object> values = new ArrayList();
        values.addAll(Arrays.asList(5L, "Shrek", 3, null));

        Movie movie = mapper.mapRow(values);

        assertEquals(5L, movie.id());
        assertEquals("Shrek", movie.title());
        assertEquals(3, movie.rating());
        assertNull(movie.thumbnail());
    }
}
