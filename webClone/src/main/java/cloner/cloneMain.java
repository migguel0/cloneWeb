package cloner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class cloneMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		String url2 = "https://twitter.com/login";
		
		//Use this if the web Page to clone has their own css /js /img and you cant access to them
		// Ex : String homeHomeUrlToClone2 = "https://es.wikiquote.org";
		String homeHomeUrlToClone2 ="";
		
		String path = "C:\\Users\\username\\Desktop";
		String fileName = "twitter";

		ArrayList <String> elementsToChange = new ArrayList<String>();
		elementsToChange.add("script");
		elementsToChange.add("link");
		//Descargar imagenes para todas aquellas que no pertenezcan al homePath de la Web a suplantar
		elementsToChange.add("img");
		
		Document doc = getHTML(url2);
		
		if(!homeHomeUrlToClone2.isEmpty()) {
			for (String currentElement : elementsToChange){
				Elements scripts = doc.select(currentElement);
	
				for (Element currentLink : scripts) {
					Attributes atributes = currentLink.attributes();
					for (Attribute currentAtri : atributes) {
						if (currentAtri.getKey().equals("src") || currentAtri.getKey().equals("href")) {
							String newAttri = homeHomeUrlToClone2 + currentAtri.getValue();
							currentAtri.setValue(newAttri);
						}
					}
				}
			}
		}

		storeAsHTML("UTF-8", path, doc.outerHtml(), fileName);
		System.out.println("Finished");

	}

	public static Document getHTML(String URL) throws IOException {

		Response response = Jsoup.connect(URL).execute();
		return response.parse();

	}

	public static void storeAsHTML(String codification, String pathName, String htmlCode, String name)
			throws IOException {

		String path = pathName;
		if(System.getProperty("os.name").contains("Windows"))
				path += "\\" + name + ".html";
		else
			path += "/" + name + ".html";
		FileUtils.writeStringToFile(new File(path), htmlCode, "UTF-8");

	}
}
