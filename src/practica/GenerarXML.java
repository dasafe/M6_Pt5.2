package practica;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerarXML {

	static Scanner reader = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();

			Element cursos = doc.createElement("Cursos");
			doc.appendChild(cursos);
			System.out.println("Cuantos cursos vas a introducir?");
			int nCursos = validacion();
			reader.nextLine();

			for (int i = 0; i < nCursos; i++) {
				System.out.println("\n- Curs " + (i + 1) + " -");

				Element curs = doc.createElement("Curs");
				Attr codiC = doc.createAttribute("codi");
				Element tutor = doc.createElement("Tutor");
				Element alumnes = doc.createElement("Alumnes");
				Element moduls = doc.createElement("Moduls");

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
					Element alumne = doc.createElement("Alumne");
					alumnes.appendChild(alumne);
					alumne.appendChild(doc.createTextNode(reader.nextLine()));
				}
				System.out.println("Nombre de moduls:");
				int nModuls = validacion();
				reader.nextLine();
				for (int j = 0; j < nModuls; j++) {

					Element modul = doc.createElement("Modul");
					Element titol = doc.createElement("Titol");
					Attr codiM = doc.createAttribute("codi");
					Attr ufs = doc.createAttribute("UFs");
					Element profs = doc.createElement("Professors");

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
						Element prof = doc.createElement("Professor");
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
			StreamResult result = new StreamResult(new File("cursos.xml"));
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
