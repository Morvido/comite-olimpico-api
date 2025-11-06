package gt.comiteolimpico.comiteolimpicoapi.repository;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}