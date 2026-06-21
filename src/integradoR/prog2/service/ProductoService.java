package integradoR.prog2.service;

import integradoR.prog2.entities.Categoria;
import integradoR.prog2.entities.Producto;
import integradoR.prog2.exception.EntidadNoEncontradaException;
import integradoR.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private long contador = 0;

    public void crear(Producto producto) throws ValidacionException {
        validarCampos(producto.getNombre(), producto.getPrecio(), producto.getStock());
        validarProductoUnico(producto.getNombre(), null);
        producto.setId(++contador);
        productos.add(producto);
        producto.getCategoria().getProductos().add(producto);
    }

    public void editar(Producto producto, String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) throws ValidacionException {
        validarCampos(nombre, precio, stock);
        validarProductoUnico(nombre, producto.getId());
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoria);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
    }

    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto prod : productos) {
            if (!prod.isEliminado()) {
                activos.add(prod);
            }
        }
        return activos;
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto prod : productos) {
            if (!prod.isEliminado() && prod.getId().equals(id)) {
                return prod;
            }
        }
        throw new EntidadNoEncontradaException("No existe un producto con id: " + id + ".");
    }

    private void validarCampos(String nombre, Double precio, int stock) throws ValidacionException {
        // TODO: nombre no vacio, precio >= 0, stock >= 0
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre no puede estar vacio");
        }
        if (precio < 0) {
            throw new ValidacionException("El precio no puede ser un valor negativo.");
        }
        if (stock < 0) {
            throw new ValidacionException("El stock no puede ser un valor negativo.");
        }
    }

    private void validarProductoUnico(String nombre, Long idExcluir) throws ValidacionException {
        for (Producto prod : productos) {
            if (!prod.isEliminado() && prod.getNombre().equalsIgnoreCase(nombre) && !prod.getId().equals(idExcluir)) {
                throw new ValidacionException("Ya existe un producto con el nombre: " + nombre + ".");
            }
        }
    }
}
