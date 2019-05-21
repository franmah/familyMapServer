package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import models.Event;

import static org.junit.Assert.*;


public class EventDAOTest {

    @Before
    public void setUp() throws Exception {
        Event evt = new Event("birth_francois", "fmahieu", "francois_id",
                (float)45.7640, (float) 4.8357, "France", "Lyon", "birth", 1993);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insertPass() throws Exception {

    }

    @Test
    public void insertFail() throws Exception {

    }

}
