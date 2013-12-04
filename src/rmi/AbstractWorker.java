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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractWorker.
 */
public abstract class AbstractWorker {

	/** The calculator. */
	private Calculator calculator;

	/**
	 * Pi.
	 *
	 * @param anzahlNachkommastellen the anzahl nachkommastellen
	 * @return the big decimal
	 */
	public BigDecimal pi(int anzahlNachkommastellen){
		return calculator.pi(anzahlNachkommastellen);
	}

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public Calculator getCalculator() {
        return calculator;
    }
}
