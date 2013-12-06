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

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface Calculator.
 */
public interface Calculator extends Remote {

	/**
	 * Generates Pi.
	 *
	 * @param digits the amount of digits to calculate
	 * @return pi with the given amount of digits
	 * @throws RemoteException if a digits number < 0 is provided
	 */
	public BigDecimal pi(int digits) throws RemoteException;
}
