package app.gui;

import app.DataBase;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.awt.*;

import static lib.sRAD.gui.component.Resource.LST;
import static lib.sRAD.gui.component.Resource.fontTitle;

public class Controller extends SFrame {

    public static Controller controller;
    // recursos
    private SLabel lCajas;
    private SLabel lLineas;
    // componentes
    private SPanel pLogin;
    private SLabel lBackground;

    private static DataBase data;

    private Controller() {
        // cargar recursos y ajustes
        lCajas = new SLabel(0, 0, new ImageIcon("resources/loginBackground.jpg"));
        lLineas = new SLabel(0, 0, new ImageIcon("resources/appWallpaper.png"));

        setProperties();
        reset();
    }

    public void reset() {
        // borra
        remove(lLineas);
        if (View.view != null) {
            remove(View.view.tpTabs);
            remove(View.view.btAdd);
            remove(View.view.btClose);
            if (View.view.btRemove != null) {
                remove(View.view.btRemove);
                remove(View.view.btSetting);
            }
        }

        // dibuja
        pLogin = new PLogin();

        add(pLogin);
        add(lCajas);

        repaint();
    }

    protected static void ingresar(int estado) {

        try {
            DataBase.query("SELECT * FROM producto;");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        controller.remove(controller.lCajas);
        controller.remove(controller.pLogin);
        switch (estado) {
            case 2:
                View.init();
                break;
            default:
                ViewAdmin.init();
                break;
        }
        controller.add(controller.lLineas);
        controller.repaint();
    }

    public static void init() {
        if(controller == null) {
            controller = new Controller();
        }
        else {
            controller.reset();
        }
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

        SLabel lLogin = new SLabel(160, 52, 150, 50, "LOGIN", LST);
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