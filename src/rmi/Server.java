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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class Server.
 */
public class Server extends AbstractWorker {
    private int port;
	/**
	 * Erstellt einen neuen Server an einen spezifizierten Port
	 *
	 * @param port der Port an dem der Server lauschen soll
	 */
	public Server(int port) {
        AlgorithmCalculator calculator = new AlgorithmCalculator();
        setCalculator(calculator);

        this.port = port;
	}

    public void serve() throws RemoteException {
        String name = "Calculator";
        Calculator stub;
        stub = (Calculator) UnicastRemoteObject.exportObject(this.getCalculator(), 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(name, stub);
    }
}
