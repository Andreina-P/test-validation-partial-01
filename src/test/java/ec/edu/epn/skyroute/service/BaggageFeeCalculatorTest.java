package ec.edu.epn.skyroute.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BaggageFeeCalculatorTest {

    @Mock
    PassengerService passengerService;

    @InjectMocks
    BaggageFeeCalculator baggageFeeCalculator;

    @Test
    @DisplayName("Test para cuando se tiene equipaje estándar de 20kg y es pasajero regular")
    public void test_cuando_equipaje_estandar_pasajero_regular__entonces_cobra_el_costo_base(){
        double weight=20.0; int bagCount=1; Long passengerId = 1L;

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);
        assertEquals(30.0, pago);
    }

    @Test
    @DisplayName("Test que valida el caso exceso de peso: un pasajero regular y una maleta con más de 23kg")
    public void test_cuando_exceso_de_peso_pasajero_regular__entonces_cobra_recargo_50(){
        double weight=25.0; int bagCount=1; Long passengerId = 1L;

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);

        assertEquals(80.0, pago);
    }

    @Test
    @DisplayName("Test que valida justo antes exceso de peso: un pasajero regular y una maleta con más de 23kg")
    public void test_cuando_peso_es_justo_23kg__entonces_no_cobra_recargo_50(){
        double weight=23.0; int bagCount=1; Long passengerId = 1L;

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);

        assertEquals(30.0, pago);
    }

    @Test
    @DisplayName("Test que valida el Beneficio Vip: Pasajero Vip tiene como beneficio no pagar una maleta")
    public void test_cuando__tiene_1_equipaje_estandar_pasajero_vip__entonces_maleta_es_gratis(){
        double weight=15.0; int bagCount=1; Long passengerId = 2L;

        when(passengerService.isVip(passengerId)).thenReturn(true);

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);

        assertEquals(0.00, pago);
        }

    @Test
    @DisplayName("Test que valida Vip(2 maletas): Pasajero vip se cobra de la segunda maleta en adelante, $30")
    public void test_cuando_caso_limite_pasajero_vip__entonces_cobra_recargo_30_sobre_segunda_maleta(){
        double weight=15.0; int bagCount=2; Long passengerId = 2L;

        when(passengerService.isVip(passengerId)).thenReturn(true);

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);
        
        assertEquals(30.00, pago);
    }

    @Test
    @DisplayName("Test que valida Vip(4 maletas): Pasajero vip se cobra de la segunda maleta en adelante, $30")
    public void test_cuando_caso_limite_pasajero_vip__entonces_cobra_recargo_30_sobre_tres_maletas_extras(){
        double weight=15.0; int bagCount=4; Long passengerId = 2L;

        when(passengerService.isVip(passengerId)).thenReturn(true);

        double pago = baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);
        
        assertEquals(90.00, pago);
    }

    @Test
    @DisplayName("Test que valida restricciones: Caso cuando el peso de una maleta es 0 entonces sale una excepción")
    public void test_cuando_valida_excepcion_de_peso_cero(){
        double weight=0; int bagCount=1; Long passengerId=1L;

        assertThrows(IllegalArgumentException.class,
            () -> {
                baggageFeeCalculator.calculateFee(weight, bagCount, passengerId);
            }
        );
    }
}
