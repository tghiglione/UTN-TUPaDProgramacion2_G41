package integradoR.prog2.service;

import integradoR.prog2.entities.Usuario;
import integradoR.prog2.exception.EntidadNoEncontradaException;
import integradoR.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/*
 * Service de Usuario:  CRUD
 */
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long contador = 0;

    public List<Usuario> listar() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    public void crear(Usuario usuario) throws ValidacionException {
        validarCampos(usuario.getNombre(), usuario.getApellido(), usuario.getMail());
        validarMailUnico(usuario.getMail(), null);
        usuario.setId(++contador);
        usuarios.add(usuario);
    }

    public void editar(Usuario usuario, String nombre, String apellido, String mail, String celular, String contrasena) throws ValidacionException {
        validarCampos(nombre, apellido, mail);
        validarMailUnico(mail, usuario.getId());
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasena(contrasena);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true);
    }

    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getId().equals(id)) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("No existe un usuario con id " + id);
    }

    private void validarCampos(String nombre, String apellido, String mail) throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ValidacionException("El apellido no puede estar vacio");
        }
        if (mail == null || mail.isBlank()) {
            throw new ValidacionException("El mail no puede estar vacio");
        }
    }

    private void validarMailUnico(String mail, Long idExcluir) throws ValidacionException {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail) && !u.getId().equals(idExcluir)) {
                throw new ValidacionException("Ya existe un usuario con el mail " + mail);
            }
        }
    }
}
