package cl.duoc.auth.controller;

import cl.duoc.auth.dto.*;
import cl.duoc.auth.service.AuthService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuariosController {
  private final AuthService authService;

  public AdminUsuariosController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping
  public List<UsuarioAdminResponse> listar() {
    return authService.listarTodos();
  }

  @PostMapping
  public ResponseEntity<UsuarioAdminResponse> crear(@Valid @RequestBody UsuarioAdminRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.crearDesdeAdmin(req));
  }

  @PutMapping("/{id}")
  public UsuarioAdminResponse editar(@PathVariable Long id, @Valid @RequestBody UsuarioAdminRequest req) {
    return authService.actualizarUsuario(id, req);
  }

  @PatchMapping("/{id}/estado")
  public UsuarioAdminResponse cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
    boolean activo = Boolean.TRUE.equals(body.get("activo"));
    return authService.cambiarEstadoActivo(id, activo);
  }
}