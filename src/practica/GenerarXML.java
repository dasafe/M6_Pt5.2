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

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			
			File file = new File(archivo);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(file);
			
			System.out.println("1. Crear curso\n2. Modificar curso\n3. Salir");
			opcion = validacion();
			switch (opcion) {
			case 1:
				crearXML(doc);
				break;
			case 2:
				try {
					modificarXML(doc);
				} catch (TransformerException e) {
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

	private static void modificarXML(Document doc) throws TransformerException {
		// TODO Auto-generated method stub

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
			NodeList nList = doc.getElementsByTagName("alumnes");
			for (int i = 0; i < nList.getLength(); i++) {
				Element alumne = doc.createElement("alumne");
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					eElement.appendChild(alumne);
					alumne.appendChild(doc.createTextNode(nombreAlumno));
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(archivo));
			transformer.transform(source, result);
			System.out.println("Alumno añadido");
			break;
		case 2:
			NodeList nList2 = doc.getElementsByTagName("alumne");
			for (int i = 0; i < nList2.getLength(); i++) {
				Node nNode2 = nList2.item(i);
				if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode2;
					String pName = eElement.getTextContent();
					if (pName.contains(nombreAlumno)) {
						nNode2.getParentNode().removeChild(nNode2);
						i = i - 1;
					}
				}
			}
			TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
			Transformer transformer2 = transformerFactory2.newTransformer();
			DOMSource source2 = new DOMSource(doc);
			StreamResult result2 = new StreamResult(new File(archivo));
			transformer2.transform(source2, result2);
			System.out.println("Alumno eliminado");
			break;
		case 3:
			break;

		default:
			System.out.println("\nOpcion invalida");
			break;
		}

	}

	private static void crearXML(Document doc2) {
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
