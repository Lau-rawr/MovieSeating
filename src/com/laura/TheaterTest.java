package com.laura;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TheaterTest {
 //  Theater testTheater = new Theater();

    @Test
    void fillReservation() throws Exception {

        Theater test1 = new Theater();
        try {
           assertEquals(-2, test1.fillReservation("R001 0"));
           assertEquals(-1, test1.fillReservation("R001 500"));
           assertEquals(0,test1.fillReservation("R001 7"));
        }
        catch(Exception e){
            fail("unexpected IOException");
        }

    }


}




