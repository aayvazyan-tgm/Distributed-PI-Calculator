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

import static org.junit.Assert.*;

public class TestNetworkedCalculator {
    private NetworkedCalculator calculator;

    @Before
    public void prepare()
            throws URISyntaxException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Server server = new Server(1099);

        calculator  = new NetworkedCalculator();

        URI uri = new URI("localhost:1099");
        calculator.addServer(uri);
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

        assertNull("NetworkedCalculator#pi(int) sollte bei negativen Parameter kein Ergebnis liefern", resultNegativ);
        assertNotNull("NetworkedCalculator#pi(int) sollte bei negativen Parameter eine Exception liefern", exspected);
    }

    @Test
    public void testZero_pi() {
        BigDecimal resultZero = null;

        resultZero = calculator.pi(0);

        assertEquals("pi ohne Nachkommastellen ist 3", new BigDecimal(3), resultZero);
    }

    @Test
    public void testSixteen_pi() {
        BigDecimal resultTen = null;
        BigDecimal piTen = new BigDecimal("3.1415926535897932");

        resultTen = calculator.pi(16);

        assertEquals("Die ersten 16 Stellen von PI sollten der erwartung entsprechen", piTen, resultTen);
    }
}
