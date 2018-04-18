# Cvičenie 2 - konfigurácia a event bus

Úlohou je doplniť a spustiť jednoduchú Vert.x aplikáciu DataProducerService, ktorá generuje quotes a zobrazí ich pomocou REST endpointu.

1. Skompilujte projekt pomocou `mvn install` alebo vo vašom IDE. Kompilátor zahlási chyby.

2. Opravte chyby kompilátoru - súbory `DataProducerConfigVerticle.java` a `RestQuoteAPIVerticle.java` podľa komentáru, ktorý začína slovom "Úloha"

3. Pozrite konfiguráciu uloženú v súbore src/conf/config.json. Launcher načíta tento súbor a použije pre Verticles

4. Spustite aplikáciu pomocou `Launcher.main()` a v prehliadači otvorte http://localhost:35000/

5. Po spustení `mvn install` sa vytvorí spustiteľný JAR súbor, ktorý obsahuje všetko potrebné. Zastavte aplikáciu spustenú cez Launcher a spustite z príkazového riadka: `java -jar DataProducerService/target/DataProducerService-1.0-SNAPSHOT-fat.jar`