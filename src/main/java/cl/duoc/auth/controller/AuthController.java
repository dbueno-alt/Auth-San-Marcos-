package cl.duoc.auth.controller;

import cl.duoc.auth.dto.*;
import cl.duoc.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación y Registro", description = "Endpoints públicos para inicio de sesión y creación de cuentas")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @Operation(summary = "Iniciar sesión", description = "Valida credenciales y retorna un token JWT firmado junto con los datos del usuario.")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @PostMapping("/registro")
  @Operation(summary = "Registrar nuevo cliente", description = "Crea una cuenta nueva con rol USER y retorna su token JWT.")
  public ResponseEntity<LoginResponse> registro(@Valid @RequestBody RegistroRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
  }
}