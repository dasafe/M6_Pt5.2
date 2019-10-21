package practica;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GenerarXML {

	private static final String archivo = "cursosexemple.xml";
	static Scanner reader = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("1. Crear curso\n2. Modificar curso\n3. Salir");
			opcion = validacion();
			switch (opcion) {
			case 1:
				crearXML();
				break;
			case 2:
				try {
					modificarXML();
				} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("\nBye!");
				break;
			default:
				System.out.println("\nOpcion invalida");
				break;
			}
			System.out.println();
		} while (opcion != 3);

	}

	private static void modificarXML()
			throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// TODO Auto-generated method stub
		File file = new File(archivo);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(file);

		System.out.println("\n1. Añadir alumno\n2. Eliminar alumno\n3. Salir");
		String nombreAlumno = null;
		int opcion = validacion();
		reader.nextLine();
		if (opcion == 1 || opcion == 2) {
			System.out.println("Nombre del alumno:");
			nombreAlumno = reader.nextLine();
		}
		switch (opcion) {
		case 1:
			System.out.println("En proceso");
			break;
		case 2:
			NodeList nList = doc.getElementsByTagName("alumne");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String pName = eElement.getTextContent();
					if (pName.contains(nombreAlumno)) {
						nNode.getParentNode().removeChild(nNode);
						i = i - 1;
					}
				}
			}
			System.out.println("Alumno eliminado");
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(archivo));
			transformer.transform(source, result);
			break;
		case 3:
			break;

		default:
			System.out.println("\nOpcion invalida");
			break;
		}

	}

	private static void crearXML() {
		// TODO Auto-generated method stub
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();

			Element cursos = doc.createElement("cursos");
			doc.appendChild(cursos);
			System.out.println("Cuantos cursos vas a introducir?");
			int nCursos = validacion();
			reader.nextLine();

			for (int i = 0; i < nCursos; i++) {
				System.out.println("\n- Curs " + (i + 1) + " -");

				Element curs = doc.createElement("curs");
				Attr codiC = doc.createAttribute("codi");
				Element tutor = doc.createElement("tutor");
				Element alumnes = doc.createElement("alumnes");
				Element moduls = doc.createElement("moduls");

				cursos.appendChild(curs);
				curs.appendChild(tutor);
				curs.setAttributeNode(codiC);
				curs.appendChild(alumnes);
				curs.appendChild(moduls);

				System.out.println("Nom del curs:");
				codiC.setValue(reader.nextLine());
				System.out.println("Nom del tutor:");
				tutor.appendChild(doc.createTextNode(reader.nextLine()));
				System.out.println("Nombre d'alumnes del curs:");
				int nAlumnos = validacion();
				reader.nextLine();
				System.out.println("Nom dels alumnes:");
				for (int j = 0; j < nAlumnos; j++) {
					Element alumne = doc.createElement("alumne");
					alumnes.appendChild(alumne);
					alumne.appendChild(doc.createTextNode(reader.nextLine()));
				}
				System.out.println("Nombre de moduls:");
				int nModuls = validacion();
				reader.nextLine();
				for (int j = 0; j < nModuls; j++) {

					Element modul = doc.createElement("modul");
					Element titol = doc.createElement("titol");
					Attr codiM = doc.createAttribute("codi");
					Attr ufs = doc.createAttribute("ufs");
					Element profs = doc.createElement("professors");

					moduls.appendChild(modul);
					modul.setAttributeNode(codiM);
					modul.setAttributeNode(ufs);
					modul.appendChild(titol);
					modul.appendChild(profs);

					System.out.println("\n- Modul " + (j + 1) + " -");
					System.out.println("Codi del modul:");
					codiM.setValue(reader.nextLine());
					System.out.println("Nom del modul:");
					titol.appendChild(doc.createTextNode(reader.nextLine()));
					System.out.println("Nombre de UFs:");
					ufs.setValue(reader.nextLine());
					System.out.println("Nombre de professors:");
					int nProf = validacion();
					reader.nextLine();
					System.out.println("Nom dels professors:");
					for (int k = 0; k < nProf; k++) {
						Element prof = doc.createElement("professor");
						profs.appendChild(prof);
						prof.appendChild(doc.createTextNode(reader.nextLine()));
					}
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Creamos el XML
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(archivo));
			transformer.transform(source, result);
			System.out.println("\nXML creado con exito");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int validacion() {
		while (!reader.hasNextInt()) {
			reader.next();
			System.out.println("No valido");
		}
		int numero = reader.nextInt();
		return numero;
	}

}
