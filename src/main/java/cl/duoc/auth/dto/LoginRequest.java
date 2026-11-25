package cl.duoc.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SolicitudLogin", description = "Credenciales requeridas para iniciar sesión en Veterinaria San Marcos")
public record LoginRequest(
  @Schema(
    description = "Correo electrónico registrado en la plataforma",
    example = "usuario@ejemplo.cl",
    format = "email",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "El formato del correo electrónico no es válido")
  String correo,

  @Schema(
    description = "Contraseña personal del usuario",
    example = "contraseña-de-ejemplo",
    format = "password",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "La contraseña es obligatoria")
  String password
) {}
