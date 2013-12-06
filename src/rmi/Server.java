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

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class Server provides functionality to calculate pi
 */
public class Server extends AbstractWorker {
    
    /** The port. */
    private int port;
    
    /** The stub. */
    private Calculator stub;
	
	/**
	 * Creates a new server
	 *
	 * @param port the port to listen to
	 */
	public Server(int port) {
        AlgorithmCalculator calculator = new AlgorithmCalculator();
        setCalculator(calculator);

        this.port = port;
	}

    /**
     * Serve starts the RMI server
     *
     * @throws RemoteException the remote exception
     * @throws AlreadyBoundException the already bound exception
     */
    public void serve() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(port);

        String name = "Calculator";
        Calculator stub;
        stub = (Calculator) UnicastRemoteObject.exportObject(this.getCalculator(), port);
        registry.rebind(name, stub);
        this.stub = stub;
    }
}
