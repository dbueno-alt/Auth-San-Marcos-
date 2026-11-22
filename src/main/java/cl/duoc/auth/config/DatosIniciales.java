package cl.duoc.auth.config;

import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!prod")
public class DatosIniciales {
  @Bean CommandLineRunner usuarioInicial(UsuarioRepository repo, PasswordEncoder encoder) {
    return args -> {
      if (repo.findByCorreoIgnoreCase("admin@veterinariasanmarcos.cl").isEmpty()) {
        Usuario admin = new Usuario();
        admin.setNombre("Administrador San Marcos");
        admin.setCorreo("admin@veterinariasanmarcos.cl");
        admin.setPassword(encoder.encode("Cambiar123!"));
        admin.setRegion("Región Metropolitana de Santiago");
        admin.setComuna("Providencia");
        admin.setRol("ADMIN");
        repo.save(admin);
      }
      if (repo.findByCorreoIgnoreCase("cliente@veterinariasanmarcos.cl").isEmpty()) {
        Usuario user = new Usuario();
        user.setNombre("Cliente Demo");
        user.setCorreo("cliente@veterinariasanmarcos.cl");
        user.setPassword(encoder.encode("Cliente123!"));
        user.setRegion("Valparaíso");
        user.setComuna("Viña del Mar");
        user.setRol("USER");
        repo.save(user);
      }
    };
  }
}
