package integradoR.prog2;

import integradoR.prog2.entities.Categoria;
import integradoR.prog2.entities.Pedido;
import integradoR.prog2.entities.Producto;
import integradoR.prog2.entities.Usuario;
import integradoR.prog2.enums.Estado;
import integradoR.prog2.enums.FormaPago;
import integradoR.prog2.enums.Rol;
import integradoR.prog2.exception.EntidadNoEncontradaException;
import integradoR.prog2.exception.StockInvalidoException;
import integradoR.prog2.exception.ValidacionException;
import integradoR.prog2.service.CategoriaService;
import integradoR.prog2.service.PedidoService;
import integradoR.prog2.service.ProductoService;
import integradoR.prog2.service.UsuarioService;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    // Instancias de los services - manejan la logica de negocio
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final PedidoService pedidoService = new PedidoService();
    private final Scanner scanner = new Scanner(System.in);

    // Punto de entrada del menu, loop principal
    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE - SISTEMA DE PEDIDOS ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Hasta luego!");
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    // ========================
    // METODOS AUXILIARES
    // ========================

    // Lee un entero, si el usuario ingresa letras retorna -1
    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Lee un entero mayor a cero, repite hasta que el valor sea valido
    private int leerEnteroPositivo(String mensaje) {
        int valor = -1;
        while (valor <= 0) {
            System.out.println(mensaje);
            valor = leerEntero();
            if (valor <= 0) {
                System.out.println("Error: el valor debe ser mayor a cero, intente nuevamente.");
            }
        }
        return valor;
    }

    // Lee un entero mayor o igual a cero, repite hasta que el valor sea valido
    private int leerEnteroNoNegativo(String mensaje) {
        int valor = -1;
        while (valor < 0) {
            System.out.println(mensaje);
            valor = leerEntero();
            if (valor < 0) {
                System.out.println("Error: el valor no puede ser negativo, intente nuevamente.");
            }
        }
        return valor;
    }

    // Lee un Double mayor o igual a cero, repite hasta que el valor sea valido
    private Double leerDoubleNoNegativo(String mensaje) {
        Double valor = -1.0;
        while (valor < 0) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor < 0) {
                    System.out.println("Error: el valor no puede ser negativo, intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                valor = -1.0;
            }
        }
        return valor;
    }

    // ========================
    // MENU CATEGORIAS
    // ========================
    private void menuCategorias() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE - SISTEMA DE PEDIDOS ===");
            System.out.println("\n=== CATEGORIAS ===");
            System.out.println("1. Listar categorias cargadas.");
            System.out.println("2. Crear nueva categoria.");
            System.out.println("3. Editar categoria.");
            System.out.println("4. Eliminar categoria.");
            System.out.println("0. Volver al menu anterior");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarCategorias();
                case 2 -> crearCategoria();
                case 3 -> editarCategoria();
                case 4 -> eliminarCategoria();
                case 0 -> System.out.println("Saliendo de menu CATEGORIAS");
                default -> System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }

    private void listarCategorias() {
        System.out.println("Listando categorias: ");
        List<Categoria> categorias = categoriaService.listar();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
        } else {
            for (Categoria cat : categorias) {
                System.out.println(cat);
            }
        }
    }

    private void crearCategoria() {
        try {
            System.out.println("Crear categoria.");
            System.out.println("Ingrese el nombre de la nueva categoria: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la descripcion de la nueva categoria: ");
            String descripcion = scanner.nextLine();
            categoriaService.crear(new Categoria(nombre, descripcion));
            System.out.println("Categoria creada correctamente.");
        } catch (ValidacionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarCategoria() {
        try {
            System.out.println("Editar categoria.");
            System.out.println("¿Desea listar las categorias? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarCategorias();
            }
            System.out.println("Ingrese el ID de la categoria que desea editar: ");
            Long id = (long) leerEntero();
            Categoria categoria = categoriaService.buscarPorId(id);
            System.out.println("Categoria encontrada: " + categoria.getNombre());
            System.out.println("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la nueva descripcion: ");
            String descripcion = scanner.nextLine();
            categoriaService.editar(categoria, nombre, descripcion);
            System.out.println("Categoria editada correctamente.");
        } catch (ValidacionException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCategoria() {
        try {
            System.out.println("Eliminar categoria.");
            System.out.println("¿Desea listar las categorias? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarCategorias();
            }
            System.out.println("Ingrese el ID de la categoria que desea eliminar: ");
            Long id = (long) leerEntero();
            Categoria categoria = categoriaService.buscarPorId(id);
            System.out.println("Categoria encontrada: " + categoria.getNombre());
            System.out.println("¿Esta seguro que desea eliminar la categoria? (S/N): ");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Eliminacion cancelada.");
            } else {
                categoriaService.eliminar(id);
                System.out.println("Categoria eliminada correctamente.");
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========================
    // MENU PRODUCTOS
    // ========================
    private void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE - SISTEMA DE PEDIDOS ===");
            System.out.println("\n=== PRODUCTOS ===");
            System.out.println("1. Listar productos.");
            System.out.println("2. Crear nuevo producto.");
            System.out.println("3. Editar producto.");
            System.out.println("4. Eliminar producto.");
            System.out.println("0. Volver al menu anterior");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarProductos();
                case 2 -> crearProducto();
                case 3 -> editarProducto();
                case 4 -> eliminarProducto();
                case 0 -> System.out.println("Saliendo de menu PRODUCTOS");
                default -> System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }

    private void listarProductos() {
        System.out.println("Listando productos: ");
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
        } else {
            for (Producto prod : productos) {
                System.out.println(prod);
            }
        }
    }

    private void crearProducto() {
        try {
            System.out.println("Crear producto.");
            System.out.println("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la descripcion: ");
            String descripcion = scanner.nextLine();
            // Usa leerDoubleNoNegativo para reintentar si el precio es invalido
            Double precio = leerDoubleNoNegativo("Ingrese el precio: ");
            // Usa leerEnteroNoNegativo para reintentar si el stock es invalido
            int stock = leerEnteroNoNegativo("Ingrese el stock: ");
            System.out.println("Ingrese la imagen (url o nombre): ");
            String imagen = scanner.nextLine();
            System.out.println("¿El producto esta disponible? (S/N): ");
            Boolean disponible = scanner.nextLine().equalsIgnoreCase("s");
            listarCategorias();
            System.out.println("Ingrese el ID de la categoria: ");
            Long idCategoria = (long) leerEntero();
            Categoria categoria = categoriaService.buscarPorId(idCategoria);
            Producto prod = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
            productoService.crear(prod);
            System.out.println("Producto creado correctamente.");
        } catch (ValidacionException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarProducto() {
        try {
            System.out.println("Editar producto.");
            System.out.println("¿Desea listar los productos? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarProductos();
            }
            System.out.println("Ingrese el ID del producto a editar: ");
            Long id = (long) leerEntero();
            Producto producto = productoService.buscarPorId(id);
            System.out.println("Producto encontrado: " + producto.getNombre());
            System.out.println("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese la nueva descripcion: ");
            String descripcion = scanner.nextLine();
            // Usa leerDoubleNoNegativo para reintentar si el precio es invalido
            Double precio = leerDoubleNoNegativo("Ingrese el nuevo precio: ");
            // Usa leerEnteroNoNegativo para reintentar si el stock es invalido
            int stock = leerEnteroNoNegativo("Ingrese el nuevo stock: ");
            System.out.println("Ingrese la nueva imagen: ");
            String imagen = scanner.nextLine();
            System.out.println("¿El producto esta disponible? (S/N): ");
            Boolean disponible = scanner.nextLine().equalsIgnoreCase("s");
            listarCategorias();
            System.out.println("Ingrese el ID de la nueva categoria: ");
            Long idCategoria = (long) leerEntero();
            Categoria categoria = categoriaService.buscarPorId(idCategoria);
            productoService.editar(producto, nombre, precio, descripcion, stock, imagen, disponible, categoria);
            System.out.println("Producto editado correctamente.");
        } catch (ValidacionException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            System.out.println("Eliminar producto.");
            System.out.println("¿Desea listar los productos? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarProductos();
            }
            System.out.println("Ingrese el ID del producto a eliminar: ");
            Long id = (long) leerEntero();
            Producto producto = productoService.buscarPorId(id);
            System.out.println("Producto encontrado: " + producto.getNombre());
            System.out.println("¿Esta seguro que desea eliminar el producto? (S/N): ");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Eliminacion cancelada.");
            } else {
                productoService.eliminar(id);
                System.out.println("Producto eliminado correctamente.");
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========================
    // MENU USUARIOS
    // ========================
    private void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE - SISTEMA DE PEDIDOS ===");
            System.out.println("\n=== USUARIOS ===");
            System.out.println("1. Listar usuarios.");
            System.out.println("2. Crear nuevo usuario.");
            System.out.println("3. Editar usuario.");
            System.out.println("4. Eliminar usuario.");
            System.out.println("0. Volver al menu anterior");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarUsuarios();
                case 2 -> crearUsuario();
                case 3 -> editarUsuario();
                case 4 -> eliminarUsuario();
                case 0 -> System.out.println("Saliendo de menu USUARIOS");
                default -> System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }

    private void listarUsuarios() {
        System.out.println("Listando usuarios: ");
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        }
    }

    private void crearUsuario() {
        try {
            System.out.println("Crear usuario.");
            System.out.println("Ingrese el nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el apellido: ");
            String apellido = scanner.nextLine();
            System.out.println("Ingrese el mail: ");
            String mail = scanner.nextLine();
            System.out.println("Ingrese el celular: ");
            String celular = scanner.nextLine();
            System.out.println("Ingrese la contrasena: ");
            String contrasena = scanner.nextLine();
            // El rol se selecciona con opciones numericas para evitar errores de tipeo
            System.out.println("Seleccione el rol:");
            System.out.println("1. ADMIN");
            System.out.println("2. USUARIO");
            int rolOpcion = leerEntero();
            Rol rol = rolOpcion == 1 ? Rol.ADMIN : Rol.USUARIO;
            usuarioService.crear(new Usuario(nombre, apellido, mail, celular, contrasena, rol));
            System.out.println("Usuario creado correctamente.");
        } catch (ValidacionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        try {
            System.out.println("Editar usuario.");
            System.out.println("¿Desea listar los usuarios? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarUsuarios();
            }
            System.out.println("Ingrese el ID del usuario a editar: ");
            Long id = (long) leerEntero();
            Usuario usuario = usuarioService.buscarPorId(id);
            System.out.println("Usuario encontrado: " + usuario.getNombre());
            System.out.println("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nuevo apellido: ");
            String apellido = scanner.nextLine();
            System.out.println("Ingrese el nuevo mail: ");
            String mail = scanner.nextLine();
            System.out.println("Ingrese el nuevo celular: ");
            String celular = scanner.nextLine();
            System.out.println("Ingrese la nueva contrasena: ");
            String contrasena = scanner.nextLine();
            usuarioService.editar(usuario, nombre, apellido, mail, celular, contrasena);
            System.out.println("Usuario editado correctamente.");
        } catch (ValidacionException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            System.out.println("Eliminar usuario.");
            System.out.println("¿Desea listar los usuarios? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                listarUsuarios();
            }
            System.out.println("Ingrese el ID del usuario a eliminar: ");
            Long id = (long) leerEntero();
            Usuario usuario = usuarioService.buscarPorId(id);
            System.out.println("Usuario encontrado: " + usuario.getNombre());
            System.out.println("¿Esta seguro que desea eliminar el usuario? (S/N): ");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Eliminacion cancelada.");
            } else {
                usuarioService.eliminar(id);
                System.out.println("Usuario eliminado correctamente.");
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========================
    // MENU PEDIDOS
    // ========================
    private void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE - SISTEMA DE PEDIDOS ===");
            System.out.println("\n=== PEDIDOS ===");
            System.out.println("1. Listar pedidos.");
            System.out.println("2. Crear nuevo pedido.");
            System.out.println("3. Actualizar estado/forma de pago.");
            System.out.println("4. Eliminar pedido.");
            System.out.println("0. Volver al menu anterior");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarPedidos();
                case 2 -> crearPedido();
                case 3 -> actualizarPedido();
                case 4 -> eliminarPedido();
                case 0 -> System.out.println("Saliendo de menu PEDIDOS");
                default -> System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }

    private void listarPedidos() {
        System.out.println("Listando pedidos: ");
        List<Pedido> pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
        } else {
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        }
    }

    private void crearPedido() {
        try {
            System.out.println("Crear pedido.");
            // Primero seleccionamos el usuario
            listarUsuarios();
            System.out.println("Ingrese el ID del usuario: ");
            Long idUsuario = (long) leerEntero();
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            // Seleccionamos la forma de pago con opciones numericas
            System.out.println("Seleccione la forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");
            int pagoOpcion = leerEntero();
            FormaPago formaPago = switch (pagoOpcion) {
                case 1 -> FormaPago.TARJETA;
                case 2 -> FormaPago.TRANSFERENCIA;
                default -> FormaPago.EFECTIVO;
            };
            // Creamos el pedido en el service
            Pedido pedido = pedidoService.crear(usuario, formaPago);
            // Agregamos detalles en un loop hasta que el usuario decida salir
            String seguir = "s";
            while (seguir.equalsIgnoreCase("s")) {
                listarProductos();
                System.out.println("Ingrese el ID del producto: ");
                Long idProducto = (long) leerEntero();
                Producto producto = productoService.buscarPorId(idProducto);
                // Usa leerEnteroPositivo para reintentar si la cantidad es invalida
                int cantidad = leerEnteroPositivo("Ingrese la cantidad: ");
                pedidoService.agregarDetalle(pedido, cantidad, producto);
                System.out.println("Detalle agregado. Total parcial: $" + pedido.getTotal());
                System.out.println("¿Desea agregar otro producto? (S/N): ");
                seguir = scanner.nextLine();
            }
            // Calculamos el total final usando la interfaz Calculable
            pedido.calcularTotal();
            System.out.println("Pedido creado correctamente. Total: $" + pedido.getTotal());
        } catch (ValidacionException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (StockInvalidoException e) {
            System.out.println("Error de stock: " + e.getMessage());
        }
    }

    private void actualizarPedido() {
        try {
            System.out.println("Actualizar estado/forma de pago del pedido.");
            listarPedidos();
            System.out.println("Ingrese el ID del pedido a actualizar: ");
            Long id = (long) leerEntero();
            // Seleccionamos el nuevo estado
            System.out.println("Seleccione el nuevo estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. CONFIRMADO");
            System.out.println("3. TERMINADO");
            System.out.println("4. CANCELADO");
            int estadoOpcion = leerEntero();
            Estado estado = switch (estadoOpcion) {
                case 1 -> Estado.PENDIENTE;
                case 2 -> Estado.CONFIRMADO;
                case 3 -> Estado.TERMINADO;
                default -> Estado.CANCELADO;
            };
            // Seleccionamos la nueva forma de pago
            System.out.println("Seleccione la nueva forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");
            int pagoOpcion = leerEntero();
            FormaPago formaPago = switch (pagoOpcion) {
                case 1 -> FormaPago.TARJETA;
                case 2 -> FormaPago.TRANSFERENCIA;
                default -> FormaPago.EFECTIVO;
            };
            pedidoService.actualizarEstado(id, estado, formaPago);
            System.out.println("Pedido actualizado correctamente.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        try {
            System.out.println("Eliminar pedido.");
            listarPedidos();
            System.out.println("Ingrese el ID del pedido a eliminar: ");
            Long id = (long) leerEntero();
            Pedido pedido = pedidoService.buscarPorId(id);
            System.out.println("Pedido encontrado: " + pedido);
            System.out.println("¿Esta seguro que desea eliminar el pedido? (S/N): ");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Eliminacion cancelada.");
            } else {
                pedidoService.eliminar(id);
                System.out.println("Pedido eliminado correctamente.");
            }
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}