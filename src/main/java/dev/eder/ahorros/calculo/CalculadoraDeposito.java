package dev.eder.ahorros.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CalculadoraDeposito {

    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal TASA_CORTO = new BigDecimal("0.40");
    private static final BigDecimal TASA_MEDIO = new BigDecimal("0.60");
    private static final BigDecimal TASA_LARGO = new BigDecimal("0.80");
    private static final int ESCALA_TASA = 10;
    private static final int ESCALA_MONTO = 2;

    private CalculadoraDeposito() {
    }

    public static BigDecimal obtenerTasaPorPlazo(int plazoMeses) {
        if (plazoMeses < 12) {
            return TASA_CORTO;
        } else if (plazoMeses < 24) {
            return TASA_MEDIO;
        } else {
            return TASA_LARGO;
        }
    }

    public static BigDecimal convertirTasa(BigDecimal tasaPorcentaje) {
        return tasaPorcentaje.divide(CIEN, ESCALA_TASA, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcularMontoFinal(BigDecimal capital, BigDecimal tasaPorcentaje, int plazoMeses) {
        BigDecimal i = convertirTasa(tasaPorcentaje);
        BigDecimal factor = BigDecimal.ONE.add(i).pow(plazoMeses);
        return capital.multiply(factor).setScale(ESCALA_MONTO, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcularInteres(BigDecimal montoFinal, BigDecimal capital) {
        return montoFinal.subtract(capital);
    }

    public static BigDecimal calcularRendimiento(BigDecimal interes, BigDecimal capital) {
        return interes.divide(capital, ESCALA_TASA, RoundingMode.HALF_UP)
                .multiply(CIEN)
                .setScale(ESCALA_MONTO, RoundingMode.HALF_UP);
    }

    public static boolean esMultiploDe100(BigDecimal capital) {
        return capital.remainder(CIEN).compareTo(BigDecimal.ZERO) == 0;
    }
}
