package com.cursos.organizador.cursosservice.services;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class UrlsPUCP {

    public static Map<String, String> URLs = new HashMap<String,String>(){
        {
            put("Ingenieria Informatica", "https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123410/informatica.pdf");
//            put("Ingenieria Mecatronica", "https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123413/mecatronica.pdf");
//            put("Ingenieria Mecanica","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123412/mecanica.pdf");
//            put("Matematicas","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123411/matematica.pdf");
//            put("Quimica","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123415/quimica.pdf"); //Quiza haya errores
//            put("Ingenieria Electronica", "https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123403/electronica.pdf"); // No lee los primeros  de cada lista
//            put("Estadistica", "https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123405/estadistica.pdf"); //ARREGLAR
//            put("Fisica","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123406/fisica.pdf"); // Arreglar
//            put("Ingenieria de Minas","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123414/minas.pdf");
//            put("Ingenieria Civil","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123402/civil.pdf"); // Revisar Mas
//            put("Ingenieria Biomedica","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123401/biomedica.pdf"); // Arreglar Antropologia
//            put("Ingenieria de las Telecomunicaciones","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123417/telecomunicaciones.pdf"); // Arreglar TEL272
//            put("Ingenieria Industrial","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123408/industrial.pdf"); // No lee der348
//            put("Ingenieria Geologica","https://files.pucp.education/facultad/ingenieria/wp-content/uploads/2020/02/25123407/geologica.pdf"); // No lee los primeros de cada hoja
        }};

}
