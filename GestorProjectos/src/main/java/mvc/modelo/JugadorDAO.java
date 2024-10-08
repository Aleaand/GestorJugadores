/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Alejandra
 */

public class JugadorDAO implements IDAO<Jugador> {
    private List<Jugador> jugadores;
    private int tipoAlmacenamiento; // 1 = texto, 2 = binario, 3 = objetos, 4 = aleatorio, 5 = XML
    private String fileName = "jugadores.dat"; // Nombre del archivo

    public JugadorDAO(int tipoAlmacenamiento) {
        this.tipoAlmacenamiento = tipoAlmacenamiento;
        jugadores = new ArrayList<>();
        cargarDatos();
    }

    @Override
    public void create(Jugador jugador) {
        jugador.setId(generarIdUnico());
        jugadores.add(jugador);
        guardarDatos();
    }

    @Override
    public Jugador read(int id) {
        for (Jugador j : jugadores) {
            if (j.getId() == id) {
                return j;
            }
        }
        return null;
    }

    @Override
    public void update(Jugador jugadorModificado) {
        for (Jugador j : jugadores) {
            if (j.getId() == jugadorModificado.getId()) {
                j.setNick(jugadorModificado.getNick());
                j.setExperience(jugadorModificado.getExperience());
                j.setLifeLevel(jugadorModificado.getLifeLevel());
                j.setCoins(jugadorModificado.getCoins());
                guardarDatos();
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        jugadores.removeIf(j -> j.getId() == id);
        guardarDatos();
    }

    @Override
    public List<Jugador> getAll() {
        return jugadores;
    }

    private int generarIdUnico() {
        if (jugadores.isEmpty()) {
            return 0;
        } else {
            return jugadores.stream().mapToInt(Jugador::getId).max().orElse(0) + 1;
        }
    }

    private void guardarDatos() {
        switch (tipoAlmacenamiento) {
            case 1:
                guardarTexto();
                break;
            case 2:
                guardarBinario();
                break;
            case 3:
                guardarObjetos();
                break;
            case 4:
                guardarAleatorio();
                break;
            case 5:
                guardarXML();
                break;
            default:
                System.out.println("Tipo de almacenamiento no soportado.");
        }
    }

    private void cargarDatos() {
        switch (tipoAlmacenamiento) {
            case 1:
                cargarTexto();
                break;
            case 2:
                cargarBinario();
                break;
            case 3:
                cargarObjetos();
                break;
            case 4:
                cargarAleatorio();
                break;
            case 5:
                cargarXML();
                break;
            default:
                System.out.println("Tipo de almacenamiento no soportado.");
        }
    }

    // Métodos para almacenamiento en texto
    private void guardarTexto() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Jugador j : jugadores) {
                writer.write(j.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarTexto() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.replaceAll("[\\[\\]]", "").split(", ");
                int id = Integer.parseInt(parts[0].split("=")[1].trim());
                String nick = parts[1].split("=")[1].trim();
                int experiencia = Integer.parseInt(parts[2].split("=")[1].trim());
                int vida = Integer.parseInt(parts[3].split("=")[1].trim());
                int monedas = Integer.parseInt(parts[4].split("=")[1].trim());
                jugadores.add(new Jugador(id, nick, experiencia, vida, monedas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos para almacenamiento binario
    private void guardarBinario() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            for (Jugador j : jugadores) {
                dos.writeInt(j.getId());
                dos.writeUTF(j.getNick());
                dos.writeInt(j.getExperience());
                dos.writeInt(j.getLifeLevel());
                dos.writeInt(j.getCoins());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarBinario() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                String nick = dis.readUTF();
                int experiencia = dis.readInt();
                int vida = dis.readInt();
                int monedas = dis.readInt();
                jugadores.add(new Jugador(id, nick, experiencia, vida, monedas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos para almacenamiento de objetos binarios
    private void guardarObjetos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(jugadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarObjetos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            jugadores = (List<Jugador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Métodos para almacenamiento de acceso aleatorio
    private void guardarAleatorio() {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            for (Jugador j : jugadores) {
                raf.writeInt(j.getId());
                raf.writeUTF(j.getNick());
                raf.writeInt(j.getExperience());
                raf.writeInt(j.getLifeLevel());
                raf.writeInt(j.getCoins());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarAleatorio() {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                String nick = raf.readUTF();
                int experiencia = raf.readInt();
                int vida = raf.readInt();
                int monedas = raf.readInt();
                jugadores.add(new Jugador(id, nick, experiencia, vida, monedas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos para almacenamiento XML
    private void guardarXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            Document document = docFactory.newDocumentBuilder().newDocument();
            Element rootElement = document.createElement("jugadores");
            document.appendChild(rootElement);

            for (Jugador j : jugadores) {
                Element jugadorElement = document.createElement("jugador");
                rootElement.appendChild(jugadorElement);

                Element idElement = document.createElement("id");
                idElement.appendChild(document.createTextNode(String.valueOf(j.getId())));
                jugadorElement.appendChild(idElement);

                Element nickElement = document.createElement("nick");
                nickElement.appendChild(document.createTextNode(j.getNick()));
                jugadorElement.appendChild(nickElement);

                Element experienceElement = document.createElement("experience");
                experienceElement.appendChild(document.createTextNode(String.valueOf(j.getExperience())));
                jugadorElement.appendChild(experienceElement);

                Element lifeLevelElement = document.createElement("lifeLevel");
                lifeLevelElement.appendChild(document.createTextNode(String.valueOf(j.getLifeLevel())));
                jugadorElement.appendChild(lifeLevelElement);

                Element coinsElement = document.createElement("coins");
                coinsElement.appendChild(document.createTextNode(String.valueOf(j.getCoins())));
                jugadorElement.appendChild(coinsElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void cargarXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            Document doc = docFactory.newDocumentBuilder().parse(fileName);
            doc.getDocumentElement().normalize();

            var nodeList = doc.getElementsByTagName("jugador");
            for (int i = 0; i < nodeList.getLength(); i++) {
                var node = nodeList.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    Element jugadorElement = (Element) node;

                    int id = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());
                    String nick = jugadorElement.getElementsByTagName("nick").item(0).getTextContent();
                    int experiencia = Integer.parseInt(jugadorElement.getElementsByTagName("experience").item(0).getTextContent());
                    int vida = Integer.parseInt(jugadorElement.getElementsByTagName("lifeLevel").item(0).getTextContent());
                    int monedas = Integer.parseInt(jugadorElement.getElementsByTagName("coins").item(0).getTextContent());

                    jugadores.add(new Jugador(id, nick, experiencia, vida, monedas));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}