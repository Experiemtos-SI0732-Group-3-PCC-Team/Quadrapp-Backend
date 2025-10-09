# ---------- Build ----------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copiamos el archivo Maven y descargamos dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos el proyecto
RUN mvn -B clean package -DskipTests

# ---------- Runtime ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos el .jar desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto del backend
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]