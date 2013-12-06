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
import java.util.List;

/**
 * The Class Proxy is a client and a Server at the same time, it receives data from clients and redirects them to a new Server
 */
public class Proxy extends Server {

	/**
	 * Instantiates a new load balancer.
	 *
	 * @param port the port to listen to
	 * @param servers the servers to redirect to
	 */
	public Proxy(int port, List<URI> servers) {
        super(port);

        NetworkedCalculator calculator = new NetworkedCalculator();

        for(URI uri : servers) {
            calculator.addServer(uri);
        }

        super.setCalculator(calculator);
	}
}
