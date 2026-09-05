import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Optional;


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
    @Override
    public String toString() {
        return "RegistroProducto{" +
                "nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", linea_produccion='" + linea_produccion + '\'' +
                ", cantidad_producida=" + cantidad_producida +
                ", cantidad_defectuosa=" + cantidad_defectuosa +
                ", costo_unitario=" + costo_unitario +
                ", minutos_utilizado=" + minutos_utilizado +
                ", kilos_materia=" + kilos_materia +
                ", meta_produccion=" + meta_produccion +
                '}';
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


    // cumplimiento de meta, convertir a porcentaje
        Function<RegistroProducto, Double> calcular_cumplimiento = r -> (r.getCantidadProducida() / (double) r.getMetaProduccion())*100;
        System.out.println("Cumplimiento de P001: " + calcular_cumplimiento.apply(registros.get(0)));
    // costo que debe tener por cada cosa
        Function<RegistroProducto, Double> cuanto_fabricacion = p -> (p.getCostoUnitario() * p.getCantidadProducida());
        System.out.println("Lo que debe producir P001 es: "+ cuanto_fabricacion.apply(registros.get(0)));
    // calcular perdidas economicas y cuanto seria con las perdidas
        Function<RegistroProducto, Double> perdidas_fabrucacion = f -> (f.getCantidadDefectuosa()* f.getCostoUnitario());
        System.out.println("el calculo de perdidas se le aplico a P004: "+perdidas_fabrucacion.apply(registros.get(3)));

    // generar nuevo registro
        Supplier<RegistroProducto> generar_registro_prueba = () -> new RegistroProducto(
                "Arandela Test", "P67", "Linea1", 500, 10, 0.05, 100, 8.0, 450
        );

        RegistroProducto registroPrueba = generar_registro_prueba.get();
        System.out.println("Registro de prueba generado: " + registroPrueba);
    // sacar el maz y el min de los registros
        Optional<RegistroProducto> mejor = registros.stream().max(Comparator.comparing(calcular_cumplimiento::apply));

        Optional<RegistroProducto> peor = registros.stream().min(Comparator.comparing(calcular_cumplimiento::apply));

        System.out.println("El mejor desempeño es de: "+mejor.get());
        System.out.println("El peor desempeño es de: "+peor.get());

    // identificar lienas bajo cumplimiento, sumar todo sacar promedio y ver cuales estan de bajo cumplimiento
        Function<List<RegistroProducto>, Double> calcular_promedio_general = lista ->
                lista.stream()
                        .mapToDouble(calcular_cumplimiento::apply)
                        .sum() / lista.size();

        double promedioGeneral = calcular_promedio_general.apply(registros);
        System.out.println("Promedio general de cumplimiento: " + promedioGeneral);
    // metemos en una lista los que pertenecen a promedios bajos

        List<RegistroProducto> promedio_bajos = registros.stream()
                .filter(bajo ->  calcular_cumplimiento.apply(bajo)< promedioGeneral)
                .collect(Collectors.toList());
        System.out.println("los que tienen bajo registro son:");
        promedio_bajos.forEach(mostrar_registro);
    // calcular total producido
    int total_producido = registros.stream()
            .mapToInt(RegistroProducto::getCantidadProducida)
            .sum();

    System.out.println("el total producido fue de: "+total_producido);

    // determinar cuanto dinero se ha invertido en la produccion, multiplucar cada uno por su precio unitario y sumar todos con cada precio
        double invertido = registros.stream()
                .mapToDouble(cuanto_fabricacion::apply)
                .sum();
        System.out.println("lo que se invirtio en total a la empresa fue: "+invertido);



    }
}