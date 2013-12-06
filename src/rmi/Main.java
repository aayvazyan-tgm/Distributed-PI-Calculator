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

import java.awt.Toolkit;
import java.io.ObjectInputStream.GetField;
import java.net.URISyntaxException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import javax.annotation.Resource;

/**
 * The Main Class.
 */
public class Main {
	
	/**
	 * Checks the user input and starts the selected service: Server/Client/Proxy
	 *
	 * @param args the args provided by the user
	 */
	public static void main(String... args){
        /* SecurityManager, inserts the policy if none is given*/
        if (System.getSecurityManager() == null) {
        	try{
        	System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
        	}catch(Exception e){
        		System.err.println("policy file: java.policy was not found or could not be set as property");
        	}
            System.setSecurityManager(new SecurityManager());
        }

        MyCommandLineParser parser = null;
        parser = new MyCommandLineParser(args);

        switch (parser.getProgramType()) {
            case CLIENT:
                Client client = new Client(parser.clientURI);
			try {
				System.out.println(client.pi(parser.piDigits));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
                break;
            case SERVER:
                Server server = new Server(parser.port);
                try {
                    try {
						server.serve();
					} catch (AlreadyBoundException e) {
						e.printStackTrace();
					}
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case PROXY:
                Proxy proxy = new Proxy(parser.port, parser.proxyURIs);
                try {
                    proxy.serve();
                } catch (RemoteException | AlreadyBoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("No ProgramType specified");
                break;
        }
    }
}
