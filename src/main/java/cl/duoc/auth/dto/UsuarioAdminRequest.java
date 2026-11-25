package cl.duoc.auth.dto;

import jakarta.validation.constraints.*;

public record UsuarioAdminRequest(
  @NotBlank(message = "El nombre es obligatorio") String nombre,
  @NotBlank(message = "El correo es obligatorio") @Email(message = "Formato de correo inválido") String correo,
  String password,
  @NotBlank(message = "La región es obligatoria") String region,
  @NotBlank(message = "La comuna es obligatoria") String comuna,
  @NotBlank(message = "El rol es obligatorio") @Pattern(regexp = "ADMIN|RECEPCIONISTA|VETERINARIO|USER") String rol,
  Boolean activo
) {}