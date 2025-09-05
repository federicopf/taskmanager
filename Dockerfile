# Usa l'immagine OpenJDK 17 come base
FROM openjdk:17-jdk-slim

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file pom.xml
COPY pom.xml .

# Installa Maven e scarica le dipendenze
RUN apt-get update && \
    apt-get install -y maven && \
    mvn dependency:go-offline -B && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia il codice sorgente
COPY src ./src

# Compila l'applicazione
RUN mvn clean package -DskipTests

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'applicazione
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]