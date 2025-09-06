# Usa l'immagine OpenJDK 17 come base
FROM openjdk:17-jdk-slim

# Imposta la directory di lavoro
WORKDIR /app

# Installa Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia il pom.xml per le dipendenze
COPY pom.xml .

# Scarica le dipendenze (cache layer)
RUN mvn dependency:go-offline -B

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'applicazione con Maven e DevTools
# Il codice sorgente sarà montato come volume
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=dev"]