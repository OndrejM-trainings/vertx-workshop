# Cvičenie 3 - blocking tasks

Úlohou je opraviť jednoduchú Vert.x aplikáciu DataProducerService, ktorá spúšťa dlhú blokujúcu úlohu v event loope

1. Skompilujte projekt pomocou `mvn install` alebo vo vašom IDE.

2. Spustite aplikáciu pomocou `Launcher.main()` a pozorujte v logu hlásenie o blokujúcom volaní

3. Opravte úlohy v súbore `DataProducerConfigVerticle.java`podľa komentáru, ktorý začína slovom "Úloha"

4. Spustite aplikáciu pomocou `Launcher.main()` a v prehliadači otvorte http://localhost:35000/

5. Po spustení `mvn install` sa vytvorí spustiteľný JAR súbor, ktorý obsahuje všetko potrebné. Zastavte aplikáciu spustenú cez Launcher a spustite z príkazového riadka: `java -jar DataProducerService/target/DataProducerService-1.0-SNAPSHOT-fat.jar`