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

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.math.BigDecimal;

/**
 * The Class NetworkedCalculator connects to the given URIs using the round robin method.
 */
public class NetworkedCalculator implements Calculator {

    /** The servers. */
    private List<URI> servers;

    /** The position for roundrobin load balancing */
    private int position;

    /**
     * creates an emty list
     */
    public NetworkedCalculator(){
        servers=new ArrayList<URI>();
    }

    /**
     * Adds the server to the load balancer.
     *
     * @param uri the uri to the RMI server
     */
    public void addServer(URI uri) {
        servers.add(uri);
    }


    /**
     * Accesses one of the listed server to ask it for pi
     *
     * @param anzahlNachkommastellen decimal places
     * @return Pi with a given number of decimal places as BigDecimal
     * @see rmi.Calculator#pi(int)
     */
    public BigDecimal pi(int anzahlNachkommastellen) {
        BigDecimal pi = null;

        //increment position for load balancing
        position = (position+1) % servers.size();
        //next server to access
        URI server = servers.get(position);

        String name = "Compute";
        try {
            Registry registry = LocateRegistry.getRegistry(server.getHost(), server.getPort());
            Calculator calc = (Calculator) registry.lookup(name);
            pi = calc.pi(anzahlNachkommastellen);
        } catch (Exception e) {
            e.printStackTrace();
            //Todo do
        }

        return pi;
    }
}
