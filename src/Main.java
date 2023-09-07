import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    // Materias que recibe cada estudiante
    static String[] materias = {"Calculo diferencial", "Fisica I", "Tecnicas de Comunicacion Oral y Escrita", "Etica y Responsabilidad Social", "Derecho Constitucional", "Herramientas Digitales y Colaborativas"};

    public static void main(String[] args) {
        // Crear la interfaz grafica
        crearInterfaz();
    }

    static ArrayList<Integer> crearParalelos(int estudiantes) {
        ArrayList<Integer> estudiantesPorParalelo = new ArrayList<>();

        // Se ejecuta mientras que estudiantes no se igual a 0
        while (estudiantes != 0) {

            if (estudiantes < 30) {
                // Si es menor que 30, entonces añadir el restante que queda
                estudiantesPorParalelo.add(estudiantes);

                // Importante! Finalizar la ejecucion del while
                break;
            }

            // Substraer 30 del total de estudiantes nuevos
            estudiantes -= 30;

            // Añadir 30 al array de estudiantesPorParalelo
            estudiantesPorParalelo.add(30);
        }

        return estudiantesPorParalelo;
    }

    static void crearInterfaz() {
        JFrame frame = new JFrame("Sistema de asignacion de estudiantes");
        frame.setSize(1366, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizada la ventana

        JPanel panel = new JPanel();

        // Añadir un text field para poder ingresar el numero de estudiantes que se desea
        // Por ahora, algo simple sin mucha estetica
        JLabel label = new JLabel("Ingrese cantidad de estudiantes:");
        JTextField textField = new JTextField(30);
        JButton button = new JButton("Crear");

        // Obtener el valor ingresado, convertirlo a integer y generar el archivo de texto cuando de click en 'Crear'
        button.addActionListener(e -> {
            int cantidadEstudiantes = 0;

            try {
                cantidadEstudiantes = Integer.parseInt(textField.getText());
            } catch (NumberFormatException err) {
                // Puede dar error si inserta un valor que es string y no se puede convertir a integer, como "hola"
                // Por lo tanto, mostrar mensaje de error en caso de que eso suceda
                JOptionPane.showMessageDialog(frame,
                        "Error, solo se aceptan numeros",
                        "Hubo un Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // Solo si la cantidad de estudiantes ingresados es mayor a 0
            if (cantidadEstudiantes > 0) {
                ArrayList<Integer> paralelos = crearParalelos(cantidadEstudiantes);
                crearArchivoDeTexto(paralelos);

                // Mostrar ventana de dialogo indicando que se ha creado el archivo de texto.
                JOptionPane.showMessageDialog(frame,
                        "Archivo creado con exito!",
                        "Exito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Error, solo se aceptan numeros mayores a 0",
                        "Hubo un Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.add(panel);
        frame.setVisible(true);
    }

    static void crearArchivoDeTexto(ArrayList<Integer> paralelos) {

        // Aqui uso mucho el metodo `newLine`, se puede reemplazar con un `\n` en los String format pero prefiero ser mas explicito

        try {
            String headings = String.format("%-50s %-25s %-25s", "Materia", "Paralelo", "#Estudiantes");

            FileWriter fileWriter = new FileWriter("estudiantes.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(headings);
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------------------------------------------------------------------------------");
            bufferedWriter.newLine();

            for (int i = 0; i < paralelos.size(); i++) {
                // Para cada paralelo, agregar cada materia
                for (String materia : materias) {
                    bufferedWriter.write(String.format("%-50s %-25s %-25d", materia, "P-" + (i + 1), paralelos.get(i)));
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("------------------------------------------------------------------------------------------------");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException err) {
            // TODO Deal with this later
            System.out.println(err);
        }

    }
}
