package cl.duoc.auth.service;

import cl.duoc.auth.dto.*;
import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import cl.duoc.auth.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UsuarioRepository usuarios;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UsuarioRepository usuarios,
      PasswordEncoder encoder, JwtService jwt) {
    this.usuarios = usuarios;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  public LoginResponse login(LoginRequest request) {
    Usuario usuario = usuarios.findByCorreoIgnoreCase(request.correo().trim())
      .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas. Verifica tu correo y contraseña."));
    if (!encoder.matches(request.password(), usuario.getPassword())) {
      throw new BadCredentialsException("Credenciales inválidas. Verifica tu correo y contraseña.");
    }
    return construirRespuesta(usuario);
  }

  public LoginResponse registrar(RegistroRequest request) {
    String correoLimpio = request.correo().trim().toLowerCase();
    if (usuarios.existsByCorreoIgnoreCase(correoLimpio)) {
      throw new IllegalArgumentException("El correo electrónico ya está registrado.");
    }
    Usuario nuevo = new Usuario();
    nuevo.setNombre(request.nombre().trim());
    nuevo.setCorreo(correoLimpio);
    nuevo.setPassword(encoder.encode(request.password()));
    nuevo.setRegion(request.region().trim());
    nuevo.setComuna(request.comuna().trim());
    nuevo.setRol("USER");

    Usuario guardado = usuarios.save(nuevo);
    return construirRespuesta(guardado);
  }

  private LoginResponse construirRespuesta(Usuario u) {
    return new LoginResponse(
      jwt.generar(u),
      u.getId(),
      u.getNombre(),
      u.getCorreo(),
      u.getRol(),
      u.getRegion(),
      u.getComuna()
    );
  }
}