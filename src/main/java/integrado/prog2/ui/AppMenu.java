package integrado.prog2.ui;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exceptions.NegocioException;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    private final InputHelper input;

    public AppMenu(CategoriaService categoriaService, ProductoService productoService,
                   UsuarioService usuarioService, PedidoService pedidoService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
        this.input = new InputHelper(new Scanner(System.in));
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarEncabezado("SISTEMA DE PEDIDOS (FOOD STORE)");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            opcion = input.leerOpcion("Seleccione: ", 0, 4);

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Sistema finalizado correctamente.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void menuCategorias() {
        int opcion;
        do {
            mostrarEncabezado("GESTION DE CATEGORIAS");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            opcion = input.leerOpcion("Seleccione: ", 0, 4);

            switch (opcion) {
                case 1 -> listarCategorias();
                case 2 -> crearCategoria();
                case 3 -> editarCategoria();
                case 4 -> eliminarCategoria();
                case 0 -> { }
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarCategorias() {
        mostrarEncabezado("LISTADO DE CATEGORIAS");
        List<Categoria> categorias = categoriaService.listarActivas();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
        } else {
            categorias.forEach(System.out::println);
        }
        input.pausar();
    }

    private void crearCategoria() {
        mostrarEncabezado("CREAR CATEGORIA");
        try {
            String nombre = input.leerTextoNoVacio("Nombre: ");
            String descripcion = input.leerTextoLibre("Descripcion: ");
            Categoria categoria = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoria creada correctamente. ID generado: " + categoria.getId());
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void editarCategoria() {
        mostrarEncabezado("EDITAR CATEGORIA");
        listarCategoriasSinPausa();
        try {
            Long id = input.leerLong("ID de categoria a editar: ");
            String nombre = input.leerTextoOpcional("Nuevo nombre (ENTER para mantener): ");
            String descripcion = input.leerTextoOpcional("Nueva descripcion (ENTER para mantener): ");
            Categoria categoria = categoriaService.editar(id, nombre, descripcion);
            System.out.println("Categoria actualizada correctamente:");
            System.out.println(categoria);
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void eliminarCategoria() {
        mostrarEncabezado("ELIMINAR CATEGORIA");
        listarCategoriasSinPausa();
        try {
            Long id = input.leerLong("ID de categoria a eliminar: ");
            if (input.leerBoolean("Confirma la baja logica de la categoria")) {
                categoriaService.eliminar(id);
                System.out.println("Categoria eliminada logicamente.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void menuProductos() {
        int opcion;
        do {
            mostrarEncabezado("GESTION DE PRODUCTOS");
            System.out.println("1. Listar todos");
            System.out.println("2. Listar por categoria");
            System.out.println("3. Crear");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = input.leerOpcion("Seleccione: ", 0, 5);

            switch (opcion) {
                case 1 -> listarProductos();
                case 2 -> listarProductosPorCategoria();
                case 3 -> crearProducto();
                case 4 -> editarProducto();
                case 5 -> eliminarProducto();
                case 0 -> { }
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarProductos() {
        mostrarEncabezado("LISTADO DE PRODUCTOS");
        listarProductosSinPausa();
        input.pausar();
    }

    private void listarProductosSinPausa() {
        List<Producto> productos = productoService.listarActivos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private void listarProductosPorCategoria() {
        mostrarEncabezado("PRODUCTOS POR CATEGORIA");
        listarCategoriasSinPausa();
        try {
            Long categoriaId = input.leerLong("ID de categoria: ");
            List<Producto> productos = productoService.listarPorCategoria(categoriaId);
            if (productos.isEmpty()) {
                System.out.println("No hay productos activos para esa categoria.");
            } else {
                productos.forEach(System.out::println);
            }
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void crearProducto() {
        mostrarEncabezado("CREAR PRODUCTO");
        listarCategoriasSinPausa();
        try {
            String nombre = input.leerTextoNoVacio("Nombre: ");
            String descripcion = input.leerTextoLibre("Descripcion: ");
            Double precio = input.leerDouble("Precio: ");
            Integer stock = input.leerEntero("Stock: ");
            String imagen = input.leerTextoLibre("Imagen: ");
            Boolean disponible = input.leerBoolean("Disponible");
            Long categoriaId = input.leerLong("ID de categoria: ");

            Producto producto = productoService.crear(nombre, descripcion, precio, stock, imagen, disponible, categoriaId);
            System.out.println("Producto creado correctamente. ID generado: " + producto.getId());
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void editarProducto() {
        mostrarEncabezado("EDITAR PRODUCTO");
        listarProductosSinPausa();
        try {
            Long id = input.leerLong("ID de producto a editar: ");
            String nombre = input.leerTextoOpcional("Nuevo nombre (ENTER para mantener): ");
            String descripcion = input.leerTextoOpcional("Nueva descripcion (ENTER para mantener): ");
            Double precio = input.leerDoubleOpcional("Nuevo precio (ENTER para mantener): ");
            Integer stock = input.leerEnteroOpcional("Nuevo stock (ENTER para mantener): ");
            String imagen = input.leerTextoOpcional("Nueva imagen (ENTER para mantener): ");
            Boolean disponible = input.leerBooleanOpcional("Disponible");
            listarCategoriasSinPausa();
            Long categoriaId = input.leerLongOpcional("Nueva categoria ID (ENTER para mantener): ");

            Producto producto = productoService.editar(id, nombre, descripcion, precio, stock, imagen, disponible, categoriaId);
            System.out.println("Producto actualizado correctamente:");
            System.out.println(producto);
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void eliminarProducto() {
        mostrarEncabezado("ELIMINAR PRODUCTO");
        listarProductosSinPausa();
        try {
            Long id = input.leerLong("ID de producto a eliminar: ");
            if (input.leerBoolean("Confirma la baja logica del producto")) {
                productoService.eliminar(id);
                System.out.println("Producto eliminado logicamente.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void menuUsuarios() {
        int opcion;
        do {
            mostrarEncabezado("GESTION DE USUARIOS");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            opcion = input.leerOpcion("Seleccione: ", 0, 4);

            switch (opcion) {
                case 1 -> listarUsuarios();
                case 2 -> crearUsuario();
                case 3 -> editarUsuario();
                case 4 -> eliminarUsuario();
                case 0 -> { }
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarUsuarios() {
        mostrarEncabezado("LISTADO DE USUARIOS");
        listarUsuariosSinPausa();
        input.pausar();
    }

    private void listarUsuariosSinPausa() {
        List<Usuario> usuarios = usuarioService.listarActivos();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            usuarios.forEach(System.out::println);
        }
    }

    private void crearUsuario() {
        mostrarEncabezado("CREAR USUARIO");
        try {
            String nombre = input.leerTextoNoVacio("Nombre: ");
            String apellido = input.leerTextoNoVacio("Apellido: ");
            String mail = input.leerTextoNoVacio("Mail: ");
            String celular = input.leerTextoLibre("Celular: ");
            String contrasena = input.leerTextoNoVacio("Contrasena: ");
            Rol rol = input.leerEnum("Rol", Rol.class);

            Usuario usuario = usuarioService.crear(nombre, apellido, mail, celular, contrasena, rol);
            System.out.println("Usuario creado correctamente. ID generado: " + usuario.getId());
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void editarUsuario() {
        mostrarEncabezado("EDITAR USUARIO");
        listarUsuariosSinPausa();
        try {
            Long id = input.leerLong("ID de usuario a editar: ");
            String nombre = input.leerTextoOpcional("Nuevo nombre (ENTER para mantener): ");
            String apellido = input.leerTextoOpcional("Nuevo apellido (ENTER para mantener): ");
            String mail = input.leerTextoOpcional("Nuevo mail (ENTER para mantener): ");
            String celular = input.leerTextoOpcional("Nuevo celular (ENTER para mantener): ");
            String contrasena = input.leerTextoOpcional("Nueva contrasena (ENTER para mantener): ");
            Rol rol = input.leerEnumOpcional("Nuevo rol", Rol.class);

            Usuario usuario = usuarioService.editar(id, nombre, apellido, mail, celular, contrasena, rol);
            System.out.println("Usuario actualizado correctamente:");
            System.out.println(usuario);
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void eliminarUsuario() {
        mostrarEncabezado("ELIMINAR USUARIO");
        listarUsuariosSinPausa();
        try {
            Long id = input.leerLong("ID de usuario a eliminar: ");
            if (input.leerBoolean("Confirma la baja logica del usuario")) {
                usuarioService.eliminar(id);
                System.out.println("Usuario eliminado logicamente.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void menuPedidos() {
        int opcion;
        do {
            mostrarEncabezado("GESTION DE PEDIDOS");
            System.out.println("1. Listar todos");
            System.out.println("2. Listar por usuario");
            System.out.println("3. Crear pedido con detalles");
            System.out.println("4. Actualizar estado / forma de pago");
            System.out.println("5. Eliminar pedido");
            System.out.println("0. Volver");
            opcion = input.leerOpcion("Seleccione: ", 0, 5);

            switch (opcion) {
                case 1 -> listarPedidos();
                case 2 -> listarPedidosPorUsuario();
                case 3 -> crearPedido();
                case 4 -> actualizarPedido();
                case 5 -> eliminarPedido();
                case 0 -> { }
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarPedidos() {
        mostrarEncabezado("LISTADO DE PEDIDOS");
        listarPedidosConDetalle(pedidoService.listarActivos());
        input.pausar();
    }

    private void listarPedidosPorUsuario() {
        mostrarEncabezado("PEDIDOS POR USUARIO");
        listarUsuariosSinPausa();
        try {
            Long usuarioId = input.leerLong("ID de usuario: ");
            listarPedidosConDetalle(pedidoService.listarPorUsuario(usuarioId));
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void crearPedido() {
        mostrarEncabezado("CREAR PEDIDO");
        listarUsuariosSinPausa();
        try {
            Long usuarioId = input.leerLong("ID de usuario: ");
            FormaPago formaPago = input.leerEnum("Forma de pago", FormaPago.class);

            List<PedidoService.ItemPedido> items = new ArrayList<>();
            boolean agregarOtro;
            do {
                System.out.println("\nProductos disponibles:");
                List<Producto> disponibles = productoService.listarDisponibles();
                if (disponibles.isEmpty()) {
                    throw new NegocioException("No hay productos disponibles para crear el pedido.");
                }
                disponibles.forEach(System.out::println);

                Long productoId = input.leerLong("ID de producto: ");
                int cantidad = input.leerEntero("Cantidad: ");
                items.add(new PedidoService.ItemPedido(productoId, cantidad));
                agregarOtro = input.leerBoolean("Agregar otro detalle");
            } while (agregarOtro);

            Pedido pedido = pedidoService.crearPedido(usuarioId, formaPago, items);
            System.out.println("Pedido creado correctamente. ID generado: " + pedido.getId());
            System.out.println(pedido);
            System.out.println(pedido.getComprobante());
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
            System.out.println("La creacion del pedido fue cancelada para evitar datos inconsistentes.");
        }
        input.pausar();
    }

    private void actualizarPedido() {
        mostrarEncabezado("ACTUALIZAR PEDIDO");
        listarPedidosConDetalle(pedidoService.listarActivos());
        try {
            Long id = input.leerLong("ID de pedido a actualizar: ");
            Estado estado = input.leerEnumOpcional("Nuevo estado", Estado.class);
            FormaPago formaPago = input.leerEnumOpcional("Nueva forma de pago", FormaPago.class);

            Pedido pedido = pedidoService.actualizarEstadoFormaPago(id, estado, formaPago);
            System.out.println("Pedido actualizado correctamente:");
            System.out.println(pedido);
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void eliminarPedido() {
        mostrarEncabezado("ELIMINAR PEDIDO");
        listarPedidosConDetalle(pedidoService.listarActivos());
        try {
            Long id = input.leerLong("ID de pedido a eliminar: ");
            if (input.leerBoolean("Confirma la baja logica del pedido")) {
                pedidoService.eliminar(id);
                System.out.println("Pedido eliminado logicamente junto a sus detalles.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
        input.pausar();
    }

    private void listarPedidosConDetalle(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
            for (DetallePedido detalle : pedido.getDetallesActivos()) {
                System.out.println("   - " + detalle);
            }
            if (pedido.getComprobante() != null && pedido.getComprobante().isActivo()) {
                System.out.println("   - " + pedido.getComprobante());
            }
            System.out.println();
        }
    }

    private void listarCategoriasSinPausa() {
        List<Categoria> categorias = categoriaService.listarActivas();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
        } else {
            categorias.forEach(System.out::println);
        }
    }

    private void mostrarEncabezado(String titulo) {
        System.out.println("\n========================================");
        System.out.println(titulo);
        System.out.println("========================================");
    }

    private void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }
}
