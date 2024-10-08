/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mcv.controlador;

import mcv.vista.Vista;
import mvc.modelo.IDAO;
import mvc.modelo.Jugador;

/**
 *
 * @author Alejandra
 */
public class Controlador {
    private Vista vista;
    private IDAO<Jugador> jugadorDAO;

    public Controlador(Vista vista, IDAO<Jugador> jugadorDAO) {
        this.vista = vista;
        this.jugadorDAO = jugadorDAO;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.leerEntero();
            switch (opcion) {
                case 1:
                    Jugador nuevoJugador = vista.leerDatosJugadorSinId();
                    jugadorDAO.create(nuevoJugador);
                    break;
                case 2:
                    int idBaja = vista.solicitarIdJugador();
                    jugadorDAO.delete(idBaja);
                    break;
                case 3: // Modificación de jugadores
                    int idMod = vista.solicitarIdJugador();
                    Jugador jugadorModificado = jugadorDAO.read(idMod);
                    if (jugadorModificado != null) {
                        vista.mostrarMensaje("Modificando datos de: " + jugadorModificado.getNick());

                        // Modificar cada atributo por separado
                        if (vista.preguntarModificar("nick")) {
                            String nuevoNick = vista.leerNuevoNick();
                            jugadorModificado.setNick(nuevoNick);
                        }
                        if (vista.preguntarModificar("experiencia")) {
                            int nuevaExperiencia = vista.leerNuevaExperiencia();
                            jugadorModificado.setExperience(nuevaExperiencia);
                        }
                        if (vista.preguntarModificar("nivel de vida")) {
                            int nuevoNivelVida = vista.leerNuevoNivelVida();
                            jugadorModificado.setLifeLevel(nuevoNivelVida);
                        }
                        if (vista.preguntarModificar("monedas")) {
                            int nuevasMonedas = vista.leerNuevasMonedas();
                            jugadorModificado.setCoins(nuevasMonedas);
                        }

                        jugadorDAO.update(jugadorModificado);
                    } else {
                        vista.mostrarMensaje("Jugador no encontrado");
                    }
                    break;
                case 4:
                    int idConsulta = vista.solicitarIdJugador();
                    Jugador jugadorConsultado = jugadorDAO.read(idConsulta);
                    if (jugadorConsultado != null) {
                        vista.mostrarJugador(jugadorConsultado);
                    } else {
                        vista.mostrarMensaje("Jugador no encontrado");
                    }
                    break;
                case 5:
                    for (Jugador j : jugadorDAO.getAll()) {
                        vista.mostrarJugador(j);
                    }
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida");
            }
        }
    }
}
