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


import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



/**
 * Verwaltet die Argumente und deren Argumente.
 */
public class MyCommandLineParser {
	
	/**
	 * The Enum ProgramType.
	 */
	public enum ProgramType{
		
		/** The server. */
		SERVER,
		/** The client. */
		CLIENT,
		/** The proxy. */
		PROXY,
		/** The none. */
		NONE
	}
	
	/** The URI if the user selected client. */
	public URI clientURI;
	
	/** The pi digits if the user selected client. */
	public int piDigits;
	
	/** The URIs given by the user, if proxy was selected. */
	public ArrayList<URI> proxyURIs;
	
	/** The port, if given by the user. */
	public int port;
	
	/** The is server. */
	private boolean isServer=false;
	
	/** The is proxy. */
	private boolean isProxy=false;
	
	/** The is client. */
	private boolean isClient=false;
	
	/** The Constant synopsis. */
	private final static String synopsis="Aviable options:\n" +
			"-c --client <URI> <piDigits>\n" +
			"-s --server <port>\n" +
			"-p --proxy <port> <URIs...>\n"+
			"-h --hilfe \n";

    /**
     * Verarbeitet die argumente.
     *
     * @param args - Die zu parsenden Argumente.
     */
	public MyCommandLineParser(String[] args) {

		DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
		ArgumentBuilder abuilder = new ArgumentBuilder();
		GroupBuilder gbuilder = new GroupBuilder();

		/*
		 * Hier werden die zu verarbeitenden parameter angegeben.
		 */
		Option clientkOption = obuilder.withLongName("client").withShortName("c").withRequired(false).withDescription("is a Client")
				.withArgument(abuilder.withName("uri , piDigits").withMinimum(2).withMaximum(2).create())
				.create();

		Option serverOption = obuilder.withLongName("server").withShortName("s").withRequired(false).withDescription("is a Server")
				.withArgument(abuilder.withName("port").withMinimum(1).withMaximum(1).create()).create();
		
		Option proxyOption = obuilder.withLongName("proxy").withShortName("p").withRequired(false).withDescription("is a Proxy")
				.withArgument(abuilder.withName("port, URI").withMinimum(1).withMaximum(101).create())
				.create();
		Option helpOption = obuilder.withLongName("help").withShortName("h").withRequired(false).withDescription("help")
				.withArgument(abuilder.withName("help").create())
				.create();
		/*
		 * erstellen einer Optionsgruppe welche an den parser weitergegeben wird.
		 */
		Group options = gbuilder.withName("options")
				.withOption(clientkOption)
				.withOption(proxyOption)
				.withOption(serverOption)
				.withOption(helpOption)
				.create();
		
		Parser p = new Parser();
		p.setGroup(options);
		
		Parser parser = new Parser();
		parser.setGroup(options);

		//verarbeiten der argumente
		try{
		CommandLine cl = parser.parse(args);
		if(cl==null){
			System.err.println("parsing error");
			System.out.println(synopsis);
			System.exit(-1);//parsing error
		}
		//auslesen der argumente
			/*if(cl.hasOption(helpOption)){
				System.out.println(synopsis);
			}*/
			if(cl.hasOption(clientkOption)) {
				isClient=true;
				String rawURI= (String) cl.getValues(clientkOption).get(0);
				String rawDigits= (String) cl.getValues(clientkOption).get(1);
				try{
					//parsen
					this.clientURI=new URI(rawURI);
					this.piDigits=Integer.parseInt(rawDigits);
				}catch(Exception e){
					e.printStackTrace();
					System.err.println("There was a parsing error, check your input!");
					System.exit(-1);
				}
			}
			if(cl.hasOption(serverOption)) {
				isServer=true;
				String rawPort= (String) cl.getValue(serverOption);
				try{
					//parsen
					this.port=Integer.parseInt(rawPort);
				}catch(Exception e){
					e.printStackTrace();
					System.err.println("There was a parsing error, check your input!");
					System.exit(-1);
				}
			}
			if(cl.hasOption(proxyOption)) {
				isProxy=true;
				String rawPort= (String) cl.getValues(proxyOption).get(0);
				List<String> l=cl.getValues(proxyOption);
				
				try{
					//parsen
					this.port=Integer.parseInt(rawPort);
					this.proxyURIs = new ArrayList<URI>();
					for(int i=1;i<l.size();i++){
						URI u=new URI(l.get(i));
						proxyURIs.add(u);
			        }
				} catch(Exception e) {
					e.printStackTrace();
					System.err.println("There was a parsing error, check your input!");
					System.out.println(synopsis);
					System.exit(-1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Check your arguments!");
			System.out.println(synopsis);
			System.exit(-1);
		}
		//the user may only input a server, proxy or client at once.
		boolean isSet=false;
		if(isClient){
			//if(isSet)System.out.println(synopsis); will never be the case
			isSet=true;
		}
		if(isProxy){
			if(isSet)System.out.println(synopsis);
			isSet=true;
		}
		if(isServer){
			if(isSet)System.out.println(synopsis);
			isSet=true;
		}
		//if the user did not select a server/proxy/client print the synopsis
		if(!isSet) {
			System.out.println(synopsis);
			System.exit(-1);
		}
	}

	/**
	 * Gets the program type.
	 *
	 * @return returns the selected ProgrammType, ProgrammType.NONE if none was selected
	 */
	public ProgramType getProgramType(){
		if(isServer)return ProgramType.SERVER;
		if(isProxy)return ProgramType.PROXY;
		if(isClient)return ProgramType.CLIENT;
		return ProgramType.NONE;//Should never happen
	}
}