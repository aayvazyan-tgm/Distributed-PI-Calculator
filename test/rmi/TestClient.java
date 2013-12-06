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
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class TestClient {
    private Client worker;
    private static boolean runonce = false;

    @Before
    public void prepare()
            throws URISyntaxException, AlreadyBoundException, RemoteException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        if(!runonce) {
            Server server = new Server(1099);
            server.serve();
            runonce = true;
        }

        URI serverURI = new URI("localhost:1099");
        worker = new Client(serverURI);
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
            resultNegativ = worker.pi(-1);
        } catch (Exception e) {
            exspected = e;
        }

        assertNull("Client#pi(int) sollte bei negativen Parameter kein Ergebniss liefern", resultNegativ);
        assertNotNull("Client#pi(int) sollte bei negativen Parameter eine Exception werfen", exspected);
    }

    @Test
    public void testZero_pi() throws RemoteException {
        BigDecimal resultZero = null;

        resultZero = worker.pi(0);

        assertEquals("pi ohne Nachkommastellen ist 3", new BigDecimal(3), resultZero);
    }

    @Test
    public void testSixteen_pi() throws RemoteException {
        BigDecimal resultTen = null;
        BigDecimal piTen = new BigDecimal("3.1415926535897932");

        resultTen = worker.pi(16);

        assertEquals("Die ersten 16 Stellen von PI sollten der erwartung entsprechen", piTen, resultTen);
    }
}
