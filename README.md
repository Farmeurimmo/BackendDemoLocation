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

Il faut d'abord créer une base de données MySQL. (un docker-compose est fourni plus bas si jamais)

```bash
docker run -d -p 8080:8080 \
    -e DB_URL="jdbc:mysql://localhost:3306/backenddemolocation" \
    -e DB_USERNAME="root" \
    -e DB_PASSWORD="123456789" \
    --name backenddemolocation \
    --restart always \
    farmeurimmo/backend-demo-location:latest
```

Dans mon cas, cloudflare sert de reverse proxy pour rediriger les requêtes vers le serveur donc je n'ouvre pas le port 8080. 
Je dois les mettre dans un même réseau docker.

```docker-compose
networks:
  db:
  # Réseau cloudflare sauf si vous utilisez le port
  cloudflared:
    external: true

services:
  mariadb:
    image: mariadb
    restart: always
    environment:
      MARIADB_ROOT_PASSWORD: 123456789
    networks:
      - db
    volumes:
      - /srv/db/mariadb:/var/lib/mysql
  phpmyadmin:
    image: beeyev/phpmyadmin-lightweight
    restart: always
    environment:
      - 'PMA_HOST=mariadb'
    networks:
      - db
      - cloudflared # spécifique à mon cas

  app:
    image: farmeurimmo/backend-demo-location:latest
    container_name: backenddemolocation_app
    environment:
      DB_URL: jdbc:mysql://db:3306/backenddemolocation
      DB_USERNAME: backenddemolocation
      DB_PASSWORD: 123456789
    networks:
      - db
      - cloudflared
    # A garder sauf si vous utilisez un reverse proxy
    ports:
      - "8080:8080"
    depends_on:
      - db
    restart: always

volumes:
  db_data:
```

## Développement (sous linux)

```bash
# remplacez les valeurs par les vôtres
export DB_URL="jdbc:mysql://localhost:3306/backenddemolocation"
export DB_USERNAME="backenddemolocation"
export DB_PASSWORD="123456789"

./gradlew bootRun
```
