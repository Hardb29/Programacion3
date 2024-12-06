package utils;


import Model.Finanzas;
import Model.Gasto;
import Model.Ingreso;

import java.io.*;
import java.util.List;

public class ArchivoUtil {

    public static void guardarFinanzas(Finanzas finanzas, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(finanzas);
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Finanzas cargarFinanzas(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Finanzas) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Finanzas();
        }
    }
}
