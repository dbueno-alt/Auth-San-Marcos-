# Auth San Marcos

Servicio Spring Boot independiente para registrar clientes, verificar credenciales y emitir JWT con el rol del usuario.

## Requisitos

- Java 17 o superior.
- MySQL.

## Configuración local

El servicio usa el puerto `8081` y la base `conecta_auth` por defecto. Configura estas variables antes de iniciarlo:

- `AUTH_DB_URL`
- `AUTH_DB_USER`
- `AUTH_DB_PASSWORD`
- `JWT_SECRET` (Base64 y compartido con Back)
- `APP_CORS_ALLOWED_ORIGIN` (por defecto `http://localhost:5173`)

No guardar contraseñas ni el secreto JWT en archivos versionados. Los usuarios de demostración se crean solo cuando el perfil `prod` no está activo.

## Ejecución en Windows

Desde esta carpeta, en PowerShell:

```text
.\mvnw.cmd spring-boot:run
```

## Endpoints

- `POST /api/auth/registro`: crea una cuenta con rol `USER`.
- `POST /api/auth/login`: valida credenciales y devuelve un JWT.
- `GET /api/auth/health`: informa el estado del servicio.

Swagger UI está disponible en `http://localhost:8081/swagger-ui/index.html`.

El perfil `prod` lee las variables de conexión, JWT y CORS desde el entorno y limita el servicio a `127.0.0.1`.
