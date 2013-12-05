package rmi;


import java.net.URI;
import java.util.List;

import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.*;
import org.apache.commons.cli2.commandline.Parser;



/**
 * Verwaltet die Optionen und deren Argumente.
 * @author Ari Ayvazyan
 * @version 20.11.2013
 */
public class MyCommandLineParser {

	
	public URI clientURI;
	public int piDigits;
	public URI[] proxyURIs;
	public int port;
	private boolean isServer=false;
	private boolean isProxy=false;
	private boolean isClient=false;
	
	/**
	 * Verarbeitet die argumente.
	 *
	 * @param args - Die zu parsende Argumente.
	 * @throws OptionException the option exception
	 */
	public static void main(String args[]){
		try {
			MyCommandLineParser myp= new MyCommandLineParser(args);
		} catch (OptionException e) {
			e.printStackTrace();
		}
	}
	public MyCommandLineParser(String[] args) throws OptionException{

		DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
		ArgumentBuilder abuilder = new ArgumentBuilder();
		GroupBuilder gbuilder = new GroupBuilder();
		
		
		
		
		/*
		 * Hier werden die zu verarbeitenden parameter angegeben.
		 */
		
		Option clientkOption = obuilder.withLongName("client").withShortName("c").withRequired(false).withDescription("ist ein Client")
				.withArgument(abuilder.withName("uri , piDigits").withMinimum(2).withMaximum(2).create())
				.create();
		
		
		Option serverOption = obuilder.withLongName("server").withShortName("s").withRequired(false).withDescription("ist ein Server")
				.withArgument(abuilder.withName("port").withMinimum(1).withMaximum(1).create()).create();
		
		
		Option proxyOption = obuilder.withLongName("proxy").withShortName("p").withRequired(false).withDescription("ist ein Proxy")
				.withArgument(abuilder.withName("port, URI1<, URI2,...URI100>").withMinimum(1).withMaximum(101).create())
				.create();

		
		/*
		 * erstellen einer Optionsgruppe welche an den parser weitergegeben wird.
		 */
		Group options = gbuilder.withName("options")
				.withOption(clientkOption)
				.withOption(proxyOption)
				.withOption(serverOption)
				.create();
		
		Parser parser = new Parser();
		parser.setGroup(options);
		
		
		//verarbeiten der argumente
		CommandLine cl = parser.parse(args);
		
		//auslesen der argumente
		try{
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
					this.proxyURIs=new URI[l.size()-1];
					for(int i=1;i<l.size();i++){
						URI u=new URI(l.get(i));
						proxyURIs[i-1]=u;
					}
				}catch(Exception e){
					e.printStackTrace();
					System.err.println("There was a parsing error, check your input!");
					System.exit(-1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Check your arguments!");
			System.exit(-1);
		}
	}
	public boolean isClient(){
		return isClient;
	}
	public boolean isProxy(){
		return isProxy;
	}
	public boolean isServer(){
		return isServer;
	}
}