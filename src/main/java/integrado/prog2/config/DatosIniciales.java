package integrado.prog2.config;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exceptions.NegocioException;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;

public class DatosIniciales {

    private DatosIniciales() {
    }

    public static void cargar(CategoriaService categoriaService,
                              ProductoService productoService,
                              UsuarioService usuarioService,
                              PedidoService pedidoService) {
        try {
            Categoria hamburguesas = categoriaService.crear("Hamburguesas", "Comidas rapidas con pan y carne.");
            Categoria pizzas = categoriaService.crear("Pizzas", "Pizzas clasicas y especiales.");
            Categoria bebidas = categoriaService.crear("Bebidas", "Gaseosas, aguas y jugos.");

            Producto clasica = productoService.crear("Hamburguesa Clasica", "Medallon, queso y salsa", 6500.0,
                    20, "hamburguesa_clasica.jpg", true, hamburguesas.getId());
            Producto doble = productoService.crear("Hamburguesa Doble", "Doble medallon y cheddar", 8200.0,
                    15, "hamburguesa_doble.jpg", true, hamburguesas.getId());
            productoService.crear("Pizza Muzza", "Pizza de muzzarella", 9000.0,
                    12, "pizza_muzza.jpg", true, pizzas.getId());
            productoService.crear("Pizza Napolitana", "Pizza con tomate y ajo", 9800.0,
                    10, "pizza_napolitana.jpg", true, pizzas.getId());
            productoService.crear("Gaseosa 500ml", "Bebida individual", 1800.0,
                    40, "gaseosa_500.jpg", true, bebidas.getId());

            Usuario admin = usuarioService.crear("Santino", "Arias", "santino@foodstore.com", "2990000000",
                    "1234", Rol.ADMIN);
            usuarioService.crear("Juan", "Perez", "juan.perez@mail.com", "2991111111",
                    "1234", Rol.USUARIO);

            List<PedidoService.ItemPedido> items = new ArrayList<>();
            items.add(new PedidoService.ItemPedido(clasica.getId(), 1));
            items.add(new PedidoService.ItemPedido(doble.getId(), 1));
            pedidoService.crearPedido(admin.getId(), FormaPago.EFECTIVO, items);

        } catch (NegocioException ex) {
            System.out.println("No se pudieron cargar los datos iniciales: " + ex.getMessage());
        }
    }
}
