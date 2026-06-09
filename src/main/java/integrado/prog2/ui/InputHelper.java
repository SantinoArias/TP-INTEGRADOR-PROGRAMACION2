package integrado.prog2.ui;

import java.util.Scanner;

public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            int opcion = leerEntero(mensaje);
            if (opcion >= minimo && opcion <= maximo) {
                return opcion;
            }
            System.out.println("Opcion fuera de rango. Intente nuevamente.");
        }
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero entero valido.");
            }
        }
    }

    public Integer leerEnteroOpcional(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                if (entrada.isBlank()) {
                    return null;
                }
                return Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero entero valido o dejar vacio.");
            }
        }
    }

    public Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un ID numerico valido.");
            }
        }
    }

    public Long leerLongOpcional(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                if (entrada.isBlank()) {
                    return null;
                }
                return Long.parseLong(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un ID numerico valido o dejar vacio.");
            }
        }
    }

    public Double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero decimal valido.");
            }
        }
    }

    public Double leerDoubleOpcional(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim().replace(",", ".");
                if (entrada.isBlank()) {
                    return null;
                }
                return Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero decimal valido o dejar vacio.");
            }
        }
    }

    public String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isBlank()) {
                return texto;
            }
            System.out.println("Este campo no puede quedar vacio.");
        }
    }

    public String leerTextoLibre(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();
        return texto.isBlank() ? null : texto;
    }

    public boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String entrada = scanner.nextLine().trim().toUpperCase();
            if (entrada.equals("S")) {
                return true;
            }
            if (entrada.equals("N")) {
                return false;
            }
            System.out.println("Ingrese S para si o N para no.");
        }
    }

    public Boolean leerBooleanOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N o ENTER para mantener): ");
            String entrada = scanner.nextLine().trim().toUpperCase();
            if (entrada.isBlank()) {
                return null;
            }
            if (entrada.equals("S")) {
                return true;
            }
            if (entrada.equals("N")) {
                return false;
            }
            System.out.println("Ingrese S, N o deje vacio.");
        }
    }

    public <E extends Enum<E>> E leerEnum(String titulo, Class<E> enumClass) {
        E[] valores = enumClass.getEnumConstants();
        System.out.println(titulo);
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        int opcion = leerOpcion("Seleccione: ", 1, valores.length);
        return valores[opcion - 1];
    }

    public <E extends Enum<E>> E leerEnumOpcional(String titulo, Class<E> enumClass) {
        E[] valores = enumClass.getEnumConstants();
        System.out.println(titulo + " (ENTER para mantener)");
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        while (true) {
            System.out.print("Seleccione: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                return null;
            }
            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion >= 1 && opcion <= valores.length) {
                    return valores[opcion - 1];
                }
                System.out.println("Opcion fuera de rango.");
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero valido o dejar vacio.");
            }
        }
    }

    public void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
