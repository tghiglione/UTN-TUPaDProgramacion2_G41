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
        // TODO: validar campos
        // TODO: asignar id
        // TODO: agregar a la lista
    }

    public void editar(Producto producto, String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) throws ValidacionException {
        // TODO: validar campos
        // TODO: actualizar atributos
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        // TODO: buscar por id y hacer soft delete
    }

    public List<Producto> listar() {
        // TODO: retornar solo los no eliminados
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        // TODO: buscar en la lista, lanzar excepcion si no existe
    }

    private void validarCampos(String nombre, Double precio, int stock) throws ValidacionException {
        // TODO: nombre no vacio, precio >= 0, stock >= 0
    }
}