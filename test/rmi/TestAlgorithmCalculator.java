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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TestAlgorithmCalculator {
    private AlgorithmCalculator calculator;

    @Before
    public void prepare() {
        calculator = new AlgorithmCalculator();
    }

    @After
    public void after() {
        //nothing
    }

    @Test
    public void testNegative_pi() {
        BigDecimal resultNegativ = null;
        Exception exspected = null;

        try {
            resultNegativ = calculator.pi(-1);
        } catch (Exception e) {
            exspected = e;
        }

        assertNull("AlgorithmCalculator#pi(int) sollte bei negativen Parameter kein Ergebnis liefern", resultNegativ);
        assertNotNull("AlgorithmCalculator#pi(int) sollte bei negativen Parameter eine Exception liefern", exspected);
    }

    @Test
    public void testZero_pi() {
        BigDecimal resultZero = null;

        resultZero = calculator.pi(0);

        assertTrue("pi ohne Nachkommastellen ist 3", resultZero.doubleValue() == 3.d);
    }

    @Test
    public void testTen_pi() {
        BigDecimal resultTen = null;
        BigDecimal piTen = new BigDecimal(3.1415926535d);

        resultTen = calculator.pi(10);

        assertEquals("Die ersten Zehn stellen von PI sollten der erwartung entsprechen", resultTen, piTen);
    }
}
