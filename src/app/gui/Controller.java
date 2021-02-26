package app.gui;

import app.controllers.BackController;
import app.inventario.Usuario;
import lib.sRAD.gui.component.MainBar;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;

import static lib.sRAD.gui.component.Resource.LST;

public class Controller extends SFrame {

    public static Controller controller;
    // recursos
    private final SLabel lCajas;
    private final SLabel lLineas;
    // componentes
    private SPanel pLogin;

    private Controller() {
        // añade botones básicos de la ventana
        add(MainBar.getBtExit());
        add(MainBar.getBtMin(this));

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

    protected static void ingresar(Usuario usuario) throws SQLException, ParseException {

        controller.remove(controller.lCajas);
        controller.remove(controller.pLogin);
        if (usuario.isAdmin()) {
            ViewAdmin.init();
        } else {
            View.init();
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
            Usuario estado = null;
            try {
                estado = BackController.validarIngreso(tfUsuario.getText(), tfPassword.getClave());
            } catch (SQLException | ParseException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            if(estado != null) {
                try {
                    Controller.ingresar(estado);
                } catch (SQLException | ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                tfUsuario.setText("");
                tfPassword.setText("");
            }
        });
        add(btIniciar);
    }

}