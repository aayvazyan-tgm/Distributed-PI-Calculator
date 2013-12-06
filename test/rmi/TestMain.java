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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestMain {

    public boolean test_server_main() {
        new Thread() {
            @Override
            public void run() {
                Main.main("-s", "8888");
            }
        }.start();
        return true;
    }

    public boolean test_proxy_main() {
        new Thread() {
            @Override
            public void run() {
                Main.main("-p", "4444", "localhost:8888");
            }
        }.start();
        return true;
    }

    public boolean test_client_main() {
        new Thread() {
            @Override
            public void run() {
                Main.main("-c", "localhost:4444", "50");
            }
        }.start();
        return true;
    }

    @Test
    public void test_main() {
        assertTrue(test_server_main());
        assertTrue(test_proxy_main());
        assertTrue(test_client_main());
    }
}
