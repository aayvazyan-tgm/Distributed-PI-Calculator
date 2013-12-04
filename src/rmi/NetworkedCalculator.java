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

import java.util.LinkedList;
import java.util.List;
import java.net.URI;
import java.math.BigDecimal;

/**
 * The Class NetworkedCalculator connects to the given URIs using the round robin method.
 */
public class NetworkedCalculator implements Calculator {

	/** The servers. */
	private List<URI> servers;

	/**
	 * creates a emty linkedlist.
	 */
	public NetworkedCalculator(){
		servers=new LinkedList<URI>();
	}
	/**
	 * Adds the server.
	 *
	 * @param uri the uri to the RMI server
	 */
	public void addServer(URI uri) {
		servers.add(uri);
	}


	/**
	 * Pi.
	 *
	 * @param anzahlNachkommastellen the anzahl nachkommastellen
	 * @return the big decimal
	 * @see rmi.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzahlNachkommastellen) {
		return null;
	}

}
