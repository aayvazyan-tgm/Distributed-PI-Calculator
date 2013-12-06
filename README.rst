#########################
Distributed-PI-Calculator
#########################
================
Aufgabenstellung
================

.. image:: doc/distributedPICalculator.jpeg
    :width: 60%

Als Dienst soll hier die beliebig genaue Bestimmung von pi betrachtet werden.
Der Dienst stellt folgendes Interface bereit:

.. code:: java

    // Calculator.java
    public interface Calculator {
        public BigDecimal pi (int anzahl_nachkommastellen);
    }

Ihre Aufgabe ist es nun, zunächst mittels Java-RMI die direkte Kommunikation
zwischen Klient und Dienst zu ermöglichen und in einem zweiten Schritt den
Balancierer zu implementieren und zwischen Klient(en) und Dienst(e) zu
schalten. Gehen Sie dazu folgendermassen vor:

1. Ändern Sie Calculator und CalculatorImpl so, dass sie über Java-RMI von
   aussen zugreifbar sind. Entwicklen Sie ein Serverprogramm, das eine
   CalculatorImpl-Instanz erzeugt und beim RMI-Namensdienst registriert.
   Entwicklen Sie ein Klientenprogramm, das eine Referenz auf das
   Calculator-Objekt beim Namensdienst erfragt und damit pi bestimmt. Testen
   Sie die neu entwickelten Komponenten.

2. Implementieren Sie nun den Balancierer, indem Sie eine Klasse
   CalculatorBalancer von Calculator ableiten und die Methode pi() entsprechend
   implementieren. Dadurch verhält sich der Balancierer aus Sicht der Klienten
   genauso wie der Server, d.h. das Klientenprogramm muss nicht verändert
   werden. Entwickeln Sie ein Balanciererprogramm, das eine
   CalculatorBalancer-Instanz erzeugt und unter dem vom Klienten erwarteten
   Namen beim Namensdienst registriert. Hier ein paar Details und Hinweise:

   - Da mehrere Serverprogramme gleichzeitig gestartet werden, sollten Sie das
     Serverprogramm so erweitern, dass man beim Start auf der Kommandozeile den
     Namen angeben kann, unter dem das CalculatorImpl-Objekt beim Namensdienst
     registriert wird. dieses nun seine exportierte Instanz an den Balancierer
     übergibt, ohne es in die Registry zu schreiben. Verwenden Sie dabei ein
     eigenes Interface des Balancers, welches in die Registry gebinded wird,
     um den Servern das Anmelden zu ermöglichen.

   - Das Balancierer-Programm sollte nun den Namensdienst in festgelegten
     Abständen abfragen um herauszufinden, ob neue Server Implementierungen zur
     Verfügung stehen.

   - Java-RMI verwendet intern mehrere Threads, um gleichzeitig eintreffende
     Methodenaufrufe parallel abarbeiten zu können. Das ist einerseits von
     Vorteil, da der Balancierer dadurch mehrere eintreffende Aufrufe parallel
     bearbeiten kann, andererseits müssen dadurch im Balancierer änderbare
     Objekte durch Verwendung von synchronized vor dem gleichzeitigen Zugriff
     in mehreren Threads geschützt werden.

   - Beachten Sie, dass nach dem Starten eines Servers eine gewisse Zeit
     vergeht, bis der Server das CalculatorImpl-Objekt erzeugt und beim
     Namensdienst registriert hat sich beim Balancer meldet. D.h. Sie müssen im
     Balancierer zwischen Start eines Servers und Abfragen des Namensdienstes
     einige Sekunden warten.

Testen Sie das entwickelte System, indem Sie den Balancierer mit verschiedenen
Serverpoolgrössen starten und mehrere Klienten gleichzeitig Anfragen stellen
lassen. Wählen Sie die Anzahl der Iterationen bei der Berechung von pi
entsprechend gross, sodass eine Anfrage lang genug dauert um feststellen zu
können, dass der Balancierer tatsächlich mehrere Anfragen parallel bearbeitet.

~~~~~~~~~~~~~
Gruppenarbeit
~~~~~~~~~~~~~
Die Arbeit ist als 2er-Gruppe zu lösen und über das Netzwerk zu testen! Nur
localhost bzw. lokale Testzyklen sind unzulässig und werden mit 6 Minuspunkten
benotet!

~~~~~~~~~~~~~~~~~~
Benotungskriterien
~~~~~~~~~~~~~~~~~~
- 12 Punkte: Java RMI Implementierung (siehe Punkt 1)
- 12 Punkte: Implementierung des Balancers (siehe Punkt 2)
- davon 6 Punkte: Balancer
- davon 2 Punkte: Parameter - Name des Objekts
- davon 2 Punkte: Listing der Server (dyn. Hinzufügen und Entfernen)
- davon 2 Punkte: Testprotokoll mit sinnvollen Werten für Serverpoolgröße und
  Iterationen

=======
Planung
=======
~~~~~~~~~~~~~~~~~
Aufwandsschätzung
~~~~~~~~~~~~~~~~~
+-----------------------------------+---------------+-------------+-----------+
| Task                              | Planned Time  | Effort      | Who       |
|                                   +---------------+-------------+-----------+
|                                   |     [H:MM]    |             | [name]    |
+===================================+===============+=============+===========+
| RMI einlesen                      |      1:30     |    Hoch     | aayvazyan |
|                                   |               |             | jklepp    |
+-----------------------------------+---------------+-------------+-----------+
| UML                               |      1:00     |   Gering    | jklepp    |
+-----------------------------------+---------------+-------------+-----------+
| Ant build file                    |      0:30     |   Gering    | jklepp    |
+-----------------------------------+---------------+-------------+-----------+
| JUnit tests                       |      1:30     |   Gering    | jklepp    |
+-----------------------------------+---------------+-------------+-----------+
| Programmierung d Behaviours       |      2:00     |    Hoch     | aayvazyan |
+-----------------------------------+---------------+-------------+-----------+
| Sonstiger Code                    |      1:00     |   Mittel    | aayvazyan |
+-----------------------------------+---------------+-------------+-----------+
| manuelle Tests                    |      0:20     |   Gering    | aayvazyan |
|                                   |               |             | jklepp    |
+-----------------------------------+---------------+-------------+-----------+
| Dokumentation                     |      1:20     |   Mittel    | jklepp    |
|                                   |               |             | ayvazyan  |
+-----------------------------------+---------------+-------------+-----------+

~~~~~~~~~~~~~~~
Klassendiagramm
~~~~~~~~~~~~~~~

.. image:: doc/classDiagram.png
    :width: 100%

=========
Umsetzung
=========
Das Strategy Pattern kommt zum Einsatz um den Code wie pi verwendet vom Code zu
trennen wie das Ergebniss weiterverwendet wird. Das Interface ``Calculator``
ist sowohl Remote als auch das Verhalten im Strategy Pattern.

``Calculator`` wird auf zwei Arten implementiert,
als ``NetworkedCalculator`` welcher pi durch anfrage an ei RemoteObject
berechnen lässt und
als ``AlgorithmCalculator`` welcher pi mithilfe von Machin's Formel berechnet.

.. image:: doc/798px-RMI-Stubs-Skeletons.svg.png
    :width: 100%

*Grundsätzlicher aufbau eines RMI Systems* [2]_

Während im Tutorial [1]_ ein System umgesetzt wurde das über Client und Server
verfügt, enthält Distributed Pi Calculator zusätzlich einen Proxy zur
Lastenverteilung. (Theoretisch ist auch eine tiefere Verschachtelung möglich)

.. image:: doc/RMI-Stubs-Skeletons.proxy.png
    :width: 100%

*RMI System mit Proxy zur Lastenverteilung*

~~~~~~
Client
~~~~~~
Der ``Client`` hat einen ``NetworkedCalculator`` welcher auf die Funktionalität
eines über RMI bereitgestellten ``Calculator`` angewießen ist. Vom User
bekommt der ``Client`` eine Anweisung wie viele Stellen von pi er Anzeigen soll
holt sich diesen Wert über den ``NetworkedCalculator`` und gibt das Ergebniss
aus.

~~~~~~
Server
~~~~~~
Der ``Server`` hat einen ``AlgorithmCalculator`` welcher pi mithilfe eines
Algortihmus berechnen kann. Der ``Server`` stellt diesen über RMI zur
verfügung.

~~~~~
Proxy
~~~~~
Der ``Proxy`` vereinfach Eigenschaften von ``Client`` und ``Server``. Er
verfügt über einen ``NetworkedCalculator`` und stellt desen Funktionalität über
RMI zur verfügung. Dem ``Client`` gegenüber erscheint er daher als ``Server``,
dem ``Server`` gegenüber als ``Client``.

=====
Tests
=====
Zur Testdurchführung werden auf dem Windows PC 3 Server gestartet.

Auf dem Linux PC wird ein Proxy gestartet dem diese 3 Server bekannt sind.

Von beiden Maschinen werden Clients gestartet welche dem Proxy um
unteschiedlich viele Stellen von pi fragen.

Der Proxy teilt diese Anfragen auf die Server auf.

~~~~~~
Server
~~~~~~
--------
Server 1
--------

.. image:: doc/testServer1.png
    :width: 100%

--------
Server 2
--------

.. image:: doc/testServer2.png
    :width: 100%

--------
Server 3
--------

.. image:: doc/testServer3.png
    :width: 100%

~~~~~
Proxy
~~~~~

.. image:: doc/testProxy.png
    :width: 100%

~~~~~~
Client
~~~~~~
--------
Client 1
--------

.. image:: doc/testClient1.png
    :width: 100%

--------
Client 2
--------

.. image:: doc/testClient2.png
    :width: 100%

~~~~~~~~~~~
JUnit tests
~~~~~~~~~~~

.. image:: doc/junitTestResults.png
    :width: 100%

================
Zeitaufzeichnung
================
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Task                        | Who?      | Date         | From    | To      | Duration  |
|                             +-----------+--------------+---------+---------+-----------+
|                             | [name]    | [YYYY-MM-DD] | [HH:MM] | [HH:MM] |    [H:MM] |
+=============================+===========+==============+=========+=========+===========+
| RMI Tutorial lesen          | jklepp    |  2013-11-29  |  16:20  |  17:00  |     0:40  |
|                             | aayvazyan |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| UML Session 1               | jklepp    |  2013-11-29  |  17:00  |  17:30  |     0:30  |
|                             | aayvazyan |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Vorbereitung Dokument       | jklepp    |  2013-12-03  |  20:00  |  20:35  |     0:35  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| UML                         | jklepp    |  2013-12-03  |  21:15  |  22:00  |     0:45  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| JUnit Test stubs            | jklepp    |  2013-12-03  |  22:10  |  22:30  |     0:20  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Ant build file anpassen     | jklepp    |  2013-12-04  |  15:30  |  16:00  |     0:30  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| JUnit Tests                 | jklepp    |  2013-12-04  |  16:25  |  16:40  |     0:30  |
| AlgorithmCalculator         |           |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| JUnit Tests                 | jklepp    |  2013-12-04  |  16:25  |  16:40  |     1:00  |
| vorläufig fertiggestellt    |           |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Server/Proxy Code           | jklepp    |  2013-12-04  |  19:00  |  19:25  |     0:25  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| NetworkCalculator Code      | jklepp    |  2013-12-04  |  19:25  |  19:50  |     0:25  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Client Code                 | aayvazyan |  2013-12-04  |  18:00  |  18:15  |     0:15  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Algorithm Calculator        | aayvazyan |  2013-12-04  |  18:20  |  18:40  |     0:20  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| MyCommandlineParser         | aayvazyan |  2013-12-04  |  18:45  |  20:00  |     1:15  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Main Methode                | jklepp    |  2013-12-05  |  08:20  |  09:40  |     1:20  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| verzweifelte Fehler suche   | jklepp    |  2013-12-05  |  18:45  |  21:00  |     2:15  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| erfreuhlich einfaches       | jklepp    |  2013-12-05  |  23:10  |  23:50  |     0:40  |
| Fehler lösen                |           |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| JUnit test fixen, erweitern | jklepp    |  2013-12-06  |  08:40  |  09:30  |     0:50  |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Manuelle Tests              | aayvazyan |  2013-12-06  |  11:30  |  12:10  |     0:40  |
|                             | jklepp    |              |         |         |           |
+-----------------------------+-----------+--------------+---------+---------+-----------+
| Dokument abschließen        | jklepp    |  2013-12-06  |  12:20  |  14:00  |     1:40  |
+-----------------------------+-----------+--------------+---------+---------+-----------+

=======
Quellen
=======
.. _1:

[1] An Overview of RMI Applications, Oracle Online Resource,

    http://docs.oracle.com/javase/tutorial/rmi/overview.html
    (last viewed 06.12.2013)

.. _2:

[2] RMI-Stubs-Skeletons.svg, Wikipedia,

    https://en.wikipedia.org/wiki/File:RMI-Stubs-Skeletons.svg
    (last viewed 06.12.2013)


.. header::

    +-------------+---------------+------------+
    | Titel       | Autor         | Datum      |
    +=============+===============+============+
    | ###Title### | Ari Ayvazyan  | 06.12.2013 |
    |             | & Jakob Klepp |            |
    +-------------+---------------+------------+

.. footer::

    ###Page### / ###Total###
