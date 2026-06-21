package integradoR.prog2.service;

import integradoR.prog2.entities.Pedido;
import integradoR.prog2.entities.Producto;
import integradoR.prog2.entities.Usuario;
import integradoR.prog2.enums.Estado;
import integradoR.prog2.enums.FormaPago;
import integradoR.prog2.exception.EntidadNoEncontradaException;
import integradoR.prog2.exception.StockInvalidoException;
import integradoR.prog2.exception.ValidacionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();
    private long contador = 0;

    public Pedido crear(Usuario usuario, FormaPago formaPago) throws ValidacionException {
        if (usuario == null || usuario.isEliminado()) {
            throw new ValidacionException("El usuario no es valido.");
        }
        Pedido pedido = new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
        pedido.setId(++contador);
        pedidos.add(pedido);
        return pedido;
    }

    public void agregarDetalle(Pedido pedido, int cantidad, Producto producto) throws StockInvalidoException {
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
        pedido.calcularTotal();
    }

    public void actualizarEstado(Long id, Estado estado, FormaPago formaPago) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true);
    }

    public List<Pedido> listar() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getId().equals(id)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No existe un pedido con id: " + id + ".");
    }
}