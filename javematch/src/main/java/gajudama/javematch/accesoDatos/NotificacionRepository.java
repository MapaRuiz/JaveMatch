package gajudama.javematch.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("SELECT n FROM Notificacion n WHERE n.usuarioNotificado.id = :userId")
    List<Notificacion> findByUsuarioNotificado(@Param("userId") Long userId);

}
