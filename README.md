# Backend site Demo location

Technologies utilisées :

- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL (avec HikariCP)
- Lombok
- Github actions
- Docker

## Déploiement Automatique (CI/CD)

À chaque push sur la branche `master`, une nouvelle image Docker est construite et déployée sur le hub Docker.

## Déploiement sur un serveur

```bash
docker run -d -p 8080:8080 \
    -e DB_URL="jdbc:mysql://localhost:3306/backenddemolocation" \
    -e DB_USERNAME="root" \
    -e DB_PASSWORD="123456789" \
    --name backenddemolocation \
    --restart always \
    ghcr.io/abdelalielbahloul/backenddemolocation:latest
```

## Développement (sous linux)

```bash
# remplacez les valeurs par les vôtres
export DB_URL="jdbc:mysql://localhost:3306/backenddemolocation"
export DB_USERNAME="root"
export DB_PASSWORD="123456789"

./gradlew bootRun
```
