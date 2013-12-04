/*
  Copyright 2013: Ari Ayvazyan & Jakob Klepp

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package rmi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestProxy {
    private Proxy worker;

    @Before
    public void prepare() throws URISyntaxException {
        ArrayList <URI> uris = new ArrayList<URI>();

        uris.add(new URI("localhost:1099"));

        worker = new Proxy(uris);
    }

    @After
    public void after() {

    }

    @Test
    public void testNegative_pi() {
        BigDecimal resultNegativ = null;
        Exception exspected = null;

        try {
            resultNegativ = worker.pi(-1);
        } catch (Exception e) {
            exspected = e;
        }

        assertNull("AlgorithmCalculator#pi(int) sollte bei negativen Parameter kein Ergebnis liefern", resultNegativ);
        assertNotNull("AlgorithmCalculator#pi(int) sollte bei negativen Parameter eine Exception liefern", exspected);
    }

    @Test
    public void testZero_pi() {
        BigDecimal resultZero = null;

        resultZero = worker.pi(0);

        assertTrue("pi ohne Nachkommastellen ist 3", resultZero.doubleValue() == 3.d);
    }

    @Test
    public void testTen_pi() {
        BigDecimal resultTen = null;
        BigDecimal piTen = new BigDecimal(3.1415926535d);

        resultTen = worker.pi(10);

        assertEquals("Die ersten Zehn stellen von PI sollten der erwartung entsprechen", resultTen, piTen);
    }
}
