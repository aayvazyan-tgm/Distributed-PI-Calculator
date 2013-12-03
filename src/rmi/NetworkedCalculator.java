package rmi;

import java.util.List;
import java.net.URI;
import java.math.BigDecimal;

public class NetworkedCalculator implements Calculator {

	private List servers;

	public void addServer(URI uri) {

	}


	/**
	 * @see rmi.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzahlNachkommastellen) {
		return null;
	}

}
