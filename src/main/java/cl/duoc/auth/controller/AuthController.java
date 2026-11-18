package cl.duoc.auth.controller;

import cl.duoc.auth.dto.ErrorResponse;
import cl.duoc.auth.dto.LoginRequest;
import cl.duoc.auth.dto.LoginResponse;
import cl.duoc.auth.dto.RegistroRequest;
import cl.duoc.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
  name = "1. Autenticación y Registro",
  description = "Operaciones públicas para acceso de clientes/administradores y creación de cuentas en Veterinaria San Marcos."
)
public class AuthController {

  private final AuthService auth;

  public AuthController(AuthService auth) {
    this.auth = auth;
  }

  @Operation(
    summary = "Iniciar sesión en el sistema",
    description = "Verifica el correo y contraseña contra la base de datos. Si son válidos, genera un token JWT firmado con el rol del usuario (`ADMIN` o `USER`)."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Autenticación exitosa. Devuelve el token JWT y los datos del perfil.",
      content = @Content(schema = @Schema(implementation = LoginResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Error de validación en los parámetros enviados (correo mal formado o campos vacíos).",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        examples = @ExampleObject(value = "{\"mensaje\": \"El formato del correo electrónico no es válido\"}")
      )
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Credenciales incorrectas (correo no existe o contraseña no coincide).",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        examples = @ExampleObject(value = "{\"mensaje\": \"Credenciales inválidas. Verifica tu correo y contraseña.\"}")
      )
    )
  })
  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return auth.login(request);
  }

  @Operation(
    summary = "Registrar un nuevo cliente",
    description = "Crea una nueva cuenta con rol `USER` por defecto, cifra la contraseña con BCrypt y retorna automáticamente su sesión activa con JWT."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "201",
      description = "Cliente registrado exitosamente.",
      content = @Content(schema = @Schema(implementation = LoginResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Datos inválidos o faltantes en el formulario de registro.",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        examples = @ExampleObject(value = "{\"mensaje\": \"La contraseña debe tener al menos 6 caracteres\"}")
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "Conflicto: el correo electrónico ya se encuentra registrado.",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class),
        examples = @ExampleObject(value = "{\"mensaje\": \"El correo electrónico ya está registrado.\"}")
      )
    )
  })
  @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(auth.registrar(request));
  }

  @Tag(name = "2. Monitoreo", description = "Endpoints de estado y disponibilidad del microservicio.")
  @Operation(
    summary = "Verificar salud del servicio (Healthcheck)",
    description = "Permite comprobar rápidamente si el proceso Spring Boot está levantado detrás de Nginx o del túnel SSH."
  )
  @ApiResponse(
    responseCode = "200",
    description = "Servicio operativo.",
    content = @Content(examples = @ExampleObject(value = "{\"status\": \"UP\", \"service\": \"veterinaria-san-marcos-auth\"}"))
  )
  @GetMapping("/health")
  public ResponseEntity<?> health() {
    return ResponseEntity.ok(Map.of(
      "status", "UP",
      "service", "veterinaria-san-marcos-auth"
    ));
  }
}