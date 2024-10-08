/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mcv;

import mcv.controlador.Controlador;
import mcv.vista.Vista;
import mvc.modelo.IDAO;
import mvc.modelo.Jugador;
import mvc.modelo.JugadorDAO;

/**
 *
 * @author Alejandra
 */
public class run {
    public static void main(String[] args) {
        Vista vista = new Vista();
        vista.mostrarSubmenuConfiguracion();
        int tipoAlmacenamiento = vista.leerEntero();
        IDAO<Jugador> jugadorDAO = new JugadorDAO(tipoAlmacenamiento);
        Controlador controlador = new Controlador(vista, jugadorDAO);
        controlador.iniciar();
    }
}
