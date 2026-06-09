package integrado.prog2.services;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exceptions.EntidadNoEncontradaException;
import integrado.prog2.exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UsuarioService {

    private final List<Usuario> usuarios;
    private long siguienteId;
    private static final Pattern MAIL_BASICO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol)
            throws ValidacionException {

        validarUsuario(nombre, apellido, mail, contrasena, rol);
        validarMailUnico(mail, null);

        Usuario usuario = new Usuario(siguienteId++, nombre.trim(), apellido.trim(), mail.trim().toLowerCase(),
                celular.trim(), contrasena.trim(), rol);
        usuarios.add(usuario);
        return usuario;
    }

    public List<Usuario> listarActivos() {
        return usuarios.stream()
                .filter(Usuario::isActivo)
                .toList();
    }

    public Usuario buscarActivoPorId(Long id) throws EntidadNoEncontradaException {
        return usuarios.stream()
                .filter(Usuario::isActivo)
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe un usuario activo con ID " + id + "."));
    }

    public Usuario editar(Long id, String nombre, String apellido, String mail, String celular,
                          String contrasena, Rol rol) throws ValidacionException, EntidadNoEncontradaException {

        Usuario usuario = buscarActivoPorId(id);

        if (nombre != null && !nombre.isBlank()) {
            usuario.setNombre(nombre.trim());
        }
        if (apellido != null && !apellido.isBlank()) {
            usuario.setApellido(apellido.trim());
        }
        if (mail != null && !mail.isBlank()) {
            validarMail(mail);
            validarMailUnico(mail, id);
            usuario.setMail(mail.trim().toLowerCase());
        }
        if (celular != null) {
            usuario.setCelular(celular.trim());
        }
        if (contrasena != null && !contrasena.isBlank()) {
            usuario.setContrasena(contrasena.trim());
        }
        if (rol != null) {
            usuario.setRol(rol);
        }
        return usuario;
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarActivoPorId(id);
        usuario.eliminar();
    }

    private void validarUsuario(String nombre, String apellido, String mail, String contrasena, Rol rol)
            throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre del usuario es obligatorio.");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ValidacionException("El apellido del usuario es obligatorio.");
        }
        validarMail(mail);
        if (contrasena == null || contrasena.isBlank()) {
            throw new ValidacionException("La contrasena del usuario es obligatoria.");
        }
        if (rol == null) {
            throw new ValidacionException("El rol del usuario es obligatorio.");
        }
    }

    private void validarMail(String mail) throws ValidacionException {
        if (mail == null || mail.isBlank()) {
            throw new ValidacionException("El mail del usuario es obligatorio.");
        }
        if (!MAIL_BASICO.matcher(mail.trim()).matches()) {
            throw new ValidacionException("El formato del mail no es valido.");
        }
    }

    private void validarMailUnico(String mail, Long idActual) throws ValidacionException {
        boolean existe = usuarios.stream()
                .filter(Usuario::isActivo)
                .anyMatch(usuario -> usuario.getMail().equalsIgnoreCase(mail.trim())
                        && (idActual == null || !usuario.getId().equals(idActual)));

        if (existe) {
            throw new ValidacionException("Ya existe un usuario activo con ese mail.");
        }
    }
}
