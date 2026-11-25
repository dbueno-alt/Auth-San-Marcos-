package cl.duoc.auth.dto;

public record UsuarioAdminResponse(
  Long id,
  String nombre,
  String correo,
  String region,
  String comuna,
  String rol,
  boolean activo
) {}