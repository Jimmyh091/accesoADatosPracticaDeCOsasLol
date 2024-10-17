package ejercicio2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FormularioT2_2 extends JFrame {

    private JButton pensiones, cuentas, nueva_sucursal, aumentar, aumentar_cuentas, sumar, nueva_cuenta, mejor, importar;
    private JLabel label_deno, label_director, label_poblacion, label_telefono;
    private JLabel label_prop, label_tipo, label_saldo, label_codigo, label_sucursal;
    private JTextField text_deno, text_director, text_poblacion, text_telefono;
    private JTextField text_prop, text_tipo, text_saldo, text_codigo, text_sucursal;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JFileChooser jf;
    private AccesoDatos ad;

    public FormularioT2_2() {
        super("Banca electronica");

        //Configuracion de la tabla
        tabla = new JTable();
        tabla.setEnabled(false);
        modelo = (DefaultTableModel) tabla.getModel();

        pensiones = new JButton("Ver pensiones");
        cuentas = new JButton("Cuentas sucursal");
        nueva_sucursal = new JButton("Crear sucursal");
        aumentar = new JButton("Aumentar todas");
        aumentar_cuentas = new JButton("Aumentar solo sucursal");
        sumar = new JButton("Sumar cuentas tipo");
        nueva_cuenta = new JButton("Crear cuenta");
        mejor = new JButton("Mejor director");
        importar = new JButton("Importar desde fichero");

        label_deno = new JLabel("Denominacion");
        label_director = new JLabel("Director");
        label_poblacion = new JLabel("Poblacion");
        label_telefono = new JLabel("telefono");

        label_prop = new JLabel("Propietario cuenta");
        label_tipo = new JLabel("Tipo cuenta");
        label_saldo = new JLabel("Cantidad");
        label_codigo = new JLabel("Codigo cuenta corriente");
        label_sucursal = new JLabel("Sucursal de la cuenta");

        text_deno = new JTextField(20);
        text_director = new JTextField(20);
        text_poblacion = new JTextField(20);
        text_telefono = new JTextField(20);

        text_prop = new JTextField(20);
        text_tipo = new JTextField(20);
        text_saldo = new JTextField(20);
        text_codigo = new JTextField(20);
        text_sucursal = new JTextField(20);

        JScrollPane js = new JScrollPane(tabla);

        this.setLayout(new BorderLayout());

        JPanel panel_datos = new JPanel();
        panel_datos.setLayout(new GridLayout(9, 2));

        panel_datos.add(label_deno);
        panel_datos.add(text_deno);
        panel_datos.add(label_director);
        panel_datos.add(text_director);
        panel_datos.add(label_poblacion);
        panel_datos.add(text_poblacion);
        panel_datos.add(label_telefono);
        panel_datos.add(text_telefono);

        panel_datos.add(label_prop);
        panel_datos.add(text_prop);
        panel_datos.add(label_tipo);
        panel_datos.add(text_tipo);
        panel_datos.add(label_saldo);
        panel_datos.add(text_saldo);
        panel_datos.add(label_codigo);
        panel_datos.add(text_codigo);
        

        JPanel panel_botones = new JPanel();
        panel_botones.setLayout(new GridLayout(3, 3));
        panel_botones.add(pensiones);
        panel_botones.add(cuentas);
        panel_botones.add(nueva_sucursal);
        panel_botones.add(aumentar);
        panel_botones.add(mejor);
        
        jf = new JFileChooser();
        ad = new AccesoDatos("localhost", "sucursales", "root", "", "com.mysql.cj.jdbc.Driver");

        this.add(panel_botones, BorderLayout.SOUTH);
        this.add(panel_datos, BorderLayout.NORTH);
        this.add(js, BorderLayout.CENTER);

        
        pensiones.addActionListener((e) -> {
            try {
                ArrayList<String[]> datos = ad.verPensiones();
                Iterator<String[]> it = datos.iterator();
                modelo.setRowCount(0);
                modelo.setColumnIdentifiers(it.next());
                while (it.hasNext()) {
                    modelo.addRow(it.next());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(FormularioT2_2.this, ex.getMessage());
            }

        });

        cuentas.addActionListener((e) -> {
            if (text_deno.getText().equals("")) {
                JOptionPane.showMessageDialog(FormularioT2_2.this, "Faltan datos");
            } else {
                try {
                    ArrayList<String[]> datos = ad.cuentasSucursal(text_deno.getText());
                    Iterator<String[]> it = datos.iterator();
                    modelo.setRowCount(0);
                    modelo.setColumnIdentifiers(it.next());
                    while (it.hasNext()) {
                        modelo.addRow(it.next());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FormularioT2_2.this, ex.getMessage());
                }
            }
        });

        nueva_sucursal.addActionListener((e)-> {
                if (text_deno.getText().equals("") || text_poblacion.getText().equals("")
                        || text_telefono.getText().equals("") || text_director.getText().equals("")) {
                    JOptionPane.showMessageDialog(FormularioT2_2.this, "Faltan datos");
                } else {
                    try {
                        ad.nuevaSucursal(text_deno.getText(), text_director.getText(), text_poblacion.getText(), text_telefono.getText());
                        JOptionPane.showMessageDialog(FormularioT2_2.this, "Sucursal creada con éxito");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(FormularioT2_2.this, ex.getMessage());
                    }
                }
            });

        aumentar.addActionListener((e)-> {
                double dinero;
                if (text_saldo.getText().equals("")) {
                    JOptionPane.showMessageDialog(FormularioT2_2.this, "Faltan datos");
                } else {
                    try {
                        dinero = Double.parseDouble(text_saldo.getText());
                        ad.aumentarCuentas(dinero);
                        JOptionPane.showMessageDialog(FormularioT2_2.this, "Aumentar ejecutado");
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(FormularioT2_2.this, "Hay que poner numeros");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(FormularioT2_2.this, ex.getMessage());
                    }
                }
            });

      

        mejor.addActionListener((e)-> {
                try {
                    JOptionPane.showMessageDialog(FormularioT2_2.this,"Mejor director\n" + ad.mejorDirector());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FormularioT2_2.this, ex.getMessage());
                }
            });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);

        this.setResizable(false);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

}
