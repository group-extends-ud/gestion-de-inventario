package app.gui;

import app.DataBase;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.awt.*;

import static lib.sRAD.gui.component.Resource.fontTitle;

public class Controller extends SFrame {

    public static Controller controller;
    //recursos
    private SLabel lCajas;
    //componentes
    private SPanel pLogin;
    private SLabel lBackground;

    private Controller() {
        //cargar recursos
        lCajas = new SLabel(0, 30, new ImageIcon("resources/loginBackground.jpg"));

        //iniciar gui
        lBackground = lCajas;
        pLogin = new PLogin();

        add(pLogin);
        add(lBackground);

        setMainBar("Gestor de inventario");
        setProperties();
    }

    protected static void ingresar(int estado) {
        controller.remove(controller.lBackground);
        controller.remove(controller.pLogin);
        switch (estado) {
            case DataBase.USUARIO:
                View.init();
                break;
            case DataBase.ADMINISTRADOR:
                ViewAdmin.init();
                break;
            default:
                init();
                break;
        }
        controller.repaint();
    }

    public static void init() {
        controller = new Controller();
    }

    public static void agregar(Component component) {
        controller.add(component);
    }
}

/**
 * panel login
 */
class PLogin extends SPanel {

    public PLogin() {
        super(SPanel.EXTERNO, 433, 105, 433, 530);

        SLabel lLogin = new SLabel(160, 52, 150, 50, "LOGIN", fontTitle);
        add(lLogin);

        SLabel lUsuario = new SLabel(90, 160, 250, 28, "Usuario");
        add(lUsuario);

        STextField tfUsuario = new STextField(90, 200, 250, 32);
        add(tfUsuario);

        SLabel lPassword = new SLabel(90, 260, 250, 28, "Contraseña");
        add(lPassword);

        SPasswordField tfPassword = new SPasswordField(90, 300, 250, 32);
        add(tfPassword);

        SButton btIniciar = new SButton(120, 400, 200, 50, "Iniciar sesión");
        btIniciar.addActionListener(e -> {
            int estado = DataBase.validarIngreso(tfUsuario.getText(), tfPassword.getClave());
            if(estado > 0) {
                Controller.ingresar(estado);
            }
            else {
                JOptionPane.showMessageDialog(null, "Datos incorrectos", "Error", 0);
                tfUsuario.setText("");
                tfPassword.setText("");
            }
        });
        add(btIniciar);
    }

}