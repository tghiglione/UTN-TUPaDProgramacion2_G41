package integradoR.prog2.service;

import integradoR.prog2.entities.Categoria;
import integradoR.prog2.exception.EntidadNoEncontradaException;
import integradoR.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private long contador = 0;

    public void crear(Categoria categoria) throws ValidacionException {
        validarCampos(categoria.getNombre(), categoria.getDescripcion());
        validarCategoriaUnica(categoria.getNombre(), null);
        categoria.setId(++contador);
        categorias.add(categoria);
    }

    private void validarCampos(String nombre, String descripcion) throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre no puede estar vacio");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new ValidacionException("La descripcion no puede estar vacia");
        }
    }

    private void validarCategoriaUnica(String nombre, Long idExcluir ) throws ValidacionException {
        for (Categoria cat : categorias) {
            if (!cat.isEliminado() && cat.getNombre().equalsIgnoreCase(nombre) && !cat.getId().equals(idExcluir)) {
                throw new ValidacionException("Ya existe una categoría llamada " + nombre + ".");
            }
        }
    }

    public List<Categoria> listar() {
        List<Categoria> activos = new ArrayList<>();
        for (Categoria cat : categorias) {
            if (!cat.isEliminado()) {
                activos.add(cat);
            }
        }
        return activos;
    }

    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria cat : categorias) {
            if (!cat.isEliminado() && cat.getId().equals(id)) {
                return cat;
            }
        }
        throw new EntidadNoEncontradaException("No existe una categoria con id: " + id + ".");
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true);
    }
    
    public void editar(Categoria categoria, String nombre, String descripcion) throws ValidacionException {
        validarCampos(nombre, descripcion);
        validarCategoriaUnica(nombre, categoria.getId());
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
    }

}
