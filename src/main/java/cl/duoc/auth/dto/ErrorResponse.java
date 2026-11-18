package cl.duoc.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RespuestaError", description = "Estructura estándar de error devuelta por la API")
public record ErrorResponse(
  @Schema(description = "Detalle legible del error ocurrido", example = "Credenciales inválidas. Verifica tu correo y contraseña.")
  String mensaje
) {}