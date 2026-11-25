package cl.duoc.auth.service;

import cl.duoc.auth.dto.*;
import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import cl.duoc.auth.security.JwtService;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UsuarioRepository usuarios;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder, JwtService jwt) {
    this.usuarios = usuarios;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  public LoginResponse login(LoginRequest request) {
    Usuario usuario = usuarios.findByCorreoIgnoreCase(request.correo().trim())
      .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas. Verifica tu correo y contraseña."));
    if (!usuario.isActivo()) {
      throw new BadCredentialsException("Tu cuenta se encuentra desactivada. Contacta al administrador.");
    }
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
    nuevo.setActivo(true);
    return construirRespuesta(usuarios.save(nuevo));
  }

  public List<UsuarioAdminResponse> listarTodos() {
    return usuarios.findAll().stream().map(this::mapAdmin).toList();
  }

  public UsuarioAdminResponse crearDesdeAdmin(UsuarioAdminRequest req) {
    String correoLimpio = req.correo().trim().toLowerCase();
    if (usuarios.existsByCorreoIgnoreCase(correoLimpio)) {
      throw new IllegalArgumentException("El correo electrónico ya está registrado.");
    }
    if (req.password() == null || req.password().trim().length() < 6) {
      throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
    }
    Usuario u = new Usuario();
    u.setNombre(req.nombre().trim());
    u.setCorreo(correoLimpio);
    u.setPassword(encoder.encode(req.password()));
    u.setRegion(req.region().trim());
    u.setComuna(req.comuna().trim());
    u.setRol(req.rol());
    u.setActivo(req.activo() != null ? req.activo() : true);
    return mapAdmin(usuarios.save(u));
  }

  public UsuarioAdminResponse actualizarUsuario(Long id, UsuarioAdminRequest req) {
    Usuario u = usuarios.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    String correoLimpio = req.correo().trim().toLowerCase();
    if (!u.getCorreo().equalsIgnoreCase(correoLimpio) && usuarios.existsByCorreoIgnoreCase(correoLimpio)) {
      throw new IllegalArgumentException("Ese correo ya pertenece a otro usuario.");
    }
    u.setNombre(req.nombre().trim());
    u.setCorreo(correoLimpio);
    if (req.password() != null && !req.password().isBlank()) {
      if (req.password().length() < 6) {
        throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres.");
      }
      u.setPassword(encoder.encode(req.password()));
    }
    u.setRegion(req.region().trim());
    u.setComuna(req.comuna().trim());
    u.setRol(req.rol());
    if (req.activo() != null) {
      u.setActivo(req.activo());
    }
    return mapAdmin(usuarios.save(u));
  }

  public UsuarioAdminResponse cambiarEstadoActivo(Long id, boolean activo) {
    Usuario u = usuarios.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    u.setActivo(activo);
    return mapAdmin(usuarios.save(u));
  }

  private UsuarioAdminResponse mapAdmin(Usuario u) {
    return new UsuarioAdminResponse(
      u.getId(), u.getNombre(), u.getCorreo(), u.getRegion(), u.getComuna(), u.getRol(), u.isActivo()
    );
  }

  private LoginResponse construirRespuesta(Usuario u) {
    return new LoginResponse(
      jwt.generar(u), u.getId(), u.getNombre(), u.getCorreo(), u.getRol(), u.getRegion(), u.getComuna()
    );
  }
}