import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.function.Consumer;

class RegistroProducto{
    private String nombre;
    private String codigo;
    private String linea_produccion;
    private int cantidad_producida;
    private int cantidad_defectuosa;
    private double costo_unitario;
    private int minutos_utilizado;
    private double kilos_materia;
    private int meta_produccion;


    public RegistroProducto(String nombre,String codigo,String linea_produccion, int cantidad_producida, int cantidad_defectuosa,double costo_unitario, int minutos_utilizado,double kilos_materia, int meta_produccion ){
        this.nombre = nombre;
        this. codigo = codigo;
        this.linea_produccion = linea_produccion;
        this.cantidad_producida = cantidad_producida;
        this.cantidad_defectuosa = cantidad_defectuosa;
        this.costo_unitario = costo_unitario;
        this.minutos_utilizado = minutos_utilizado;
        this.kilos_materia = kilos_materia;
        this.meta_produccion = meta_produccion;

    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getLineaProduccion() {
        return linea_produccion;
    }

    public int getCantidadProducida() {
        return cantidad_producida;
    }

    public int getCantidadDefectuosa() {
        return cantidad_defectuosa;
    }

    public double getCostoUnitario() {
        return costo_unitario;
    }

    public int getMinutosUtilizado() {
        return minutos_utilizado;
    }

    public double getKilosMateria() {
        return kilos_materia;
    }

    public int getMetaProduccion() {
        return meta_produccion;
    }


}

class Produccion{
    public static void main(String[] args){
        List<RegistroProducto> registros = new ArrayList<>(Arrays.asList(
                // nombre, codigo, linea, cantidadProducida, cantidadDefectuosa, costoUnitario, minutosUtilizados, kgMateriaPrima, metaProduccion
                new RegistroProducto("Tornillo M8", "P001", "Linea1", 1200, 40,  0.15, 300, 25.0, 1000),
                new RegistroProducto("Buje Metálico", "P002", "Linea1",  800, 150, 0.80, 280, 60.0, 1000),
                new RegistroProducto("Soporte Chasis", "P003", "Linea2", 1500, 30,  2.50, 450, 120.0, 1400),
                new RegistroProducto("Eje de Transmisión", "P004", "Linea2", 600, 90,  5.20, 400, 200.0, 700),
                new RegistroProducto("Tuerca Hexagonal", "P005", "Linea3", 2000, 60,  0.10, 200, 15.0, 1800)
        ));

    Consumer<RegistroProducto> mostrar_registro = reg -> System.out.println("los resgistros son"+reg);


    registros.forEach(mostrar_registro);

    Predicate<RegistroProducto> si_es_alto = alt -> alt.getCantidadDefectuosa() >50;

    System.out.println("Los productos con registro alto son: ");
    registros.stream()
            .filter(si_es_alto)
            .forEach(mostrar_registro);



    }
}