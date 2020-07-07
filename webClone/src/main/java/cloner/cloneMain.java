package cloner;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

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

		String url2 = "https://URLtoClone";
		
		//Use this if the web Page to clone has their own css /js /img and you cant access to them
		// Ex : String homeHomeUrlToClone2 = "https://es.wikiquote.org";
		String homeHomeUrlToClone2 ="https://domainname";
		
		String path = "C:\\Users\\name\\Desktop";
		String fileName = "filename";

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
		
		//Use only if you have problems with SSL connections and Certs
		//-----------------
		//-----------------
		// Create a trust manager that does not validate certificate chains
//		TrustManager[] trustAllCerts = new TrustManager[]{
//				new X509TrustManager() {
//					public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
//		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
//		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
//			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
//					throws CertificateException {
//				// TODO Auto-generated method stub
//				
//			}
//			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
//					throws CertificateException {
//				// TODO Auto-generated method stub
//				
//			}
//		}};
//
//		// Install the all-trusting trust manager
//		try {
//		    SSLContext sc = SSLContext.getInstance("TLS");
//		    sc.init(null, trustAllCerts, new SecureRandom());
//		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//		} catch (Exception e) {
//		    ;
//		}
//		
		//-----------------
		//-----------------
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
