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

import java.net.URI;

/**
 * Instantiates a new client with a NetworkedCalculator.
 */
public class Client extends AbstractWorker {

    /**
     * Instantiates a new client with a NetworkedCalculator with the given URI.
     *
     * @param serverUri is the URI that directs to the server that provides the pi calculation function.
     */
    public Client(URI serverUri) {
        NetworkedCalculator nw= new NetworkedCalculator();
        nw.addServer(serverUri);
        this.setCalculator(nw);
    }
}
