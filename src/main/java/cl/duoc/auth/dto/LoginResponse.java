package cl.duoc.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RespuestaAutenticacion", description = "Token JWT y perfil completo del usuario autenticado")
public record LoginResponse(
  @Schema(description = "Token de acceso JWT Bearer (expira en 1 hora)", example = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ2ZXRlcmluYXJpYS1zYW4tbWFyY29zLWF1dGgifQ...")
  String token,

  @Schema(description = "Identificador único del usuario en base de datos", example = "1")
  Long id,

  @Schema(description = "Nombre completo del usuario", example = "Administrador San Marcos")
  String nombre,

  @Schema(description = "Correo electrónico asociado a la cuenta", example = "admin@veterinariasanmarcos.cl")
  String correo,

  @Schema(description = "Rol de autorización asignado (ADMIN o USER)", example = "ADMIN")
  String rol,

  @Schema(description = "Región registrada del usuario", example = "Región Metropolitana de Santiago")
  String region,

  @Schema(description = "Comuna registrada del usuario", example = "Providencia")
  String comuna
) {}