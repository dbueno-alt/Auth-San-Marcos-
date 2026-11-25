package cl.duoc.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "SolicitudRegistro", description = "Datos necesarios para crear una nueva cuenta de cliente en Veterinaria San Marcos")
public record RegistroRequest(
  @Schema(
    description = "Nombre y apellido del tutor o cliente",
    example = "Camila Soto",
    minLength = 3,
    maxLength = 100,
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  String nombre,

  @Schema(
    description = "Correo electrónico único que servirá como identificador de acceso",
    example = "camila.soto@correo.cl",
    format = "email",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "El formato del correo electrónico no es válido")
  String correo,

  @Schema(
    description = "Contraseña de acceso (mínimo 6 caracteres)",
    example = "Mascota2026!",
    format = "password",
    minLength = 6,
    maxLength = 64,
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 6, max = 64, message = "La contraseña debe tener al menos 6 caracteres")
  String password,

  @Schema(
    description = "Región de residencia del cliente en Chile",
    example = "Región Metropolitana de Santiago",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "La región es obligatoria")
  String region,

  @Schema(
    description = "Comuna de residencia para atención o despacho",
    example = "Providencia",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  @NotBlank(message = "La comuna es obligatoria")
  String comuna
) {}