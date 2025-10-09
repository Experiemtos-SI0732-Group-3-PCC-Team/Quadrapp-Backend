# ---------- Build Stage ----------
FROM eclipse-temurin:24-jdk AS builder

# Variables de entorno para Maven
ENV MAVEN_VERSION=3.9.5
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Instalar Maven manualmente
RUN apt-get update && apt-get install -y curl unzip \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip -o /tmp/maven.zip \
    && unzip /tmp/maven.zip -d /opt/ \
    && mv /opt/apache-maven-$MAVEN_VERSION $MAVEN_HOME \
    && rm -rf /tmp/maven.zip

WORKDIR /app

# Copiamos el archivo Maven y descargamos dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos el proyecto
RUN mvn -B clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copiamos el .jar desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto del backend
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]