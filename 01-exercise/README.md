# Cvičenie 1 - prvý Vert.x projekt

Úlohou je doplniť a spustiť jednoduchú Vert.x aplikáciu DataProducerService.

1. Skompilujte projekt pomocou `mvn install` alebo vo vašom IDE. Kompilátor zahlási chyby.

2. Opravte chyby kompilátoru - súbory `Launcher.java` a `HellowWorldVerticle.java` podľa komentáru, ktorý začína slovom "Úloha"

3. Spustite aplikáciu pomocou `Launcher.main()` a v prehliadači otvorte http://localhost:35000/

4. Po spustení `mvn install` sa vytvorí spustiteľný JAR súbor, ktorý obsahuje všetko potrebné. Zastavte aplikáciu spustenú cez Launcher a spustite z príkazového riadka: `java -jar DataProducerService/target/DataProducerService-1.0-SNAPSHOT-fat.jar`