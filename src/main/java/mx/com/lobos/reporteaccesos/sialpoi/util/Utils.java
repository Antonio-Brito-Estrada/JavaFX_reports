package mx.com.lobos.reporteaccesos.sialpoi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salvadora
 */
public class Utils {
    public String value(Object parametro, String Default) {
        String result = (Default != null ? Default : "");
        if (parametro != null && !parametro.equals("")) {
            result = parametro.toString();
        }
        return result;
    }

    /**
     * Metodo para convertir una fecha de tipo string a date con un formato 'dd
     * MMM yyyy'
     *
     * @param fecha
     * @param formato
     * @return Fecha es tipo date.
     */
    public Date formateaFecha(String fecha, String formato) {
        Date nuevaFecha = null;
        DateFormat formatoFecha;
        Locale locale = new Locale("us");

        if (formato.isEmpty()) {
            formato = "dd MMM yyyy";
        }
        formatoFecha = new SimpleDateFormat(formato, locale);
        try {
            nuevaFecha = formatoFecha.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevaFecha;
    }

    public String formateaFecha(Date fecha, String formato) {
        DateFormat formatoFecha;
        String strfecha;
        Locale locale = new Locale("us");

        if (formato.isEmpty()) {
            formato = "dd MMM yyyy";
        }
        formatoFecha = new SimpleDateFormat(formato, locale);
        strfecha = formatoFecha.format(fecha);
        return strfecha;
    }
}
