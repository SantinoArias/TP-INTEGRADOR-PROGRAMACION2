package integrado.prog2;

import integrado.prog2.config.DatosIniciales;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;
import integrado.prog2.ui.AppMenu;

public class Main {

    public static void main(String[] args) {
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService(categoriaService);
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService(usuarioService, productoService);

        DatosIniciales.cargar(categoriaService, productoService, usuarioService, pedidoService);

        AppMenu menu = new AppMenu(categoriaService, productoService, usuarioService, pedidoService);
        menu.iniciar();
    }
}
