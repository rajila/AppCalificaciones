package uah.rdajila.appcalificaciones.util;

import java.util.regex.Pattern;

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    /**
     * Verifica que el nombre sea valido
     * @param name
     * @return
     */
    public static boolean isNameOK(String name)
    {
        return Pattern.compile("^[a-zA-ZÑáéíóúñ ]+$").matcher(name).matches() && name.length() <= Constant._MAX_CHARACTER;
    }

    /**
     * Valida que el texto sea un numero mayor a CERO
     * @param nota
     * @return
     */
    public static boolean isNotaOK(String nota)
    {
        try {
            double _nota = Double.parseDouble(nota);
            return _nota > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
