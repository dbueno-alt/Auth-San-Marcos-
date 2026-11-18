package cl.duoc.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Veterinaria San Marcos - API de Autenticación y Seguridad",
    version = "1.0.0",
    description = """
      Microservicio encargado de la gestión de identidad, registro de clientes/personal

      ### Características principales:
      * **Autenticación Stateless:** Emisión de tokens firmados con roles (`ADMIN` y `USER`).
      * **Seguridad de Credenciales:** Contraseñas cifradas.
      * **Validación de Datos:** Verificación estricta de formato de correo, longitud de contraseña y ubicación geográfica (Región/Comuna).
      """
  
  ),
  servers = {
    @Server(url = "/", description = "Entorno Actual (Local / Túnel SSH / Nginx AWS)"),
    @Server(url = "http://localhost:8081", description = "Desarrollo Local Directo (Puerto 8081)")
  }
)
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT",
  description = "Token JWT emitido por el endpoint `/api/auth/login` o `/api/auth/registro`."
)
public class OpenApiConfig {}