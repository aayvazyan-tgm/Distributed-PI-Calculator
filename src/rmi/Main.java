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

/**
 * The Class Main.
 */
public class Main {

	/**
	 * Main.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
        /* SecurityManager */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        MyCommandLineParser parser = null;
        parser = new MyCommandLineParser(args);

        switch (parser.getProgramType()) {
            case CLIENT:
                Client client = new Client(parser.clientURI);
                break;
            case SERVER:
                Server server = new Server(parser.port);
                try {
                    server.serve();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case PROXY:
                Proxy proxy = new Proxy(parser.port, parser.proxyURIs);
                try {
                    proxy.serve();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("No ProgramType specified");
                break;
        }
    }
}
