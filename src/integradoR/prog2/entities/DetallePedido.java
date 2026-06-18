package integradoR.prog2.entities;

/*
 * DetallePedido hereda de Base. Representa una linea de un pedido.
 * Relacion N:1 con Producto: guarda una referencia al Producto pedido.
 */
public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Double subtotal, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetallePedido(id=" + id + ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", producto=" + (producto != null ? producto.getNombre() : "sin producto") + ")";
    }
}
