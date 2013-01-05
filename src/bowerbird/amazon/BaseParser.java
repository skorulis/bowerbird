package bowerbird.amazon;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseParser extends DefaultHandler {

	protected SignedRequestsHelper signingHelper;
	protected SAXParser saxParser;
	
	public String tabLevel;
	
	public BaseParser(SignedRequestsHelper helper) {
		this.signingHelper = helper;
		try  {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		tabLevel = "";
	}
	
	public Map<String,String> baseParameters() {
		Map<String, String> params = new HashMap<String, String>();
        params.put("Service", Constants.AWS_SERVICE);
        params.put("AssociateTag", Constants.ASSOCIATE_TAG);
        params.put("Version", Constants.AWS_VERSION);
        return params;
	}
	
	public void signAndParse(Map<String,String> params) {
		String requestUrl = this.signingHelper.sign(params);
		try {
			String result = this.readStringFromUrl(requestUrl);
			System.out.println(result);
			InputSource s = new InputSource(new StringReader(result)); 
			saxParser.parse(s,this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readStringFromUrl(String urlString) throws Exception {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		java.util.Scanner s = new java.util.Scanner(con.getInputStream()).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	
	//Debugging methods
	
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		System.out.print(tabLevel + "<" + qName + ">");
		tabLevel+="  ";
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		tabLevel = tabLevel.substring(2);
		//System.out.print("</" + qName + ">");
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		String s = new String(ch,start,length);
		System.out.println(s);
	}
	
	
}
