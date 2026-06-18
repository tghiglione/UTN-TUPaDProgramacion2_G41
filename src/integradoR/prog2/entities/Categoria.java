package integradoR.prog2.entities;

import java.util.ArrayList;
import java.util.List;

/*
 * Categoria hereda de Base
 * Relacion 1:N con Producto: una categoria agrupa muchos productos.
 */
public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

   
    @Override
    public String toString() {
        return "Categoria(id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", productos=" + productos.size() + ")";
    }
}
