package bowerbird.common.item;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneProperties extends ItemProperties {

	public enum PhoneOS {
		IPHONE("iPhone"),
		ANDROID("Android");
		
		public final String name;
		public final String lowercase;
		
		PhoneOS(String name) {
			this.name = name;
			lowercase = name.toLowerCase();
		}
		
		public static PhoneOS findMatch(String word) {
			for(PhoneOS os : PhoneOS.values()) {
				if(os.lowercase.equals(word)) {
					return os;
				}
			}
			return null;
		}	
	}
	
	public static final String[] models = new String[]{"3G","3GS","4","5","4S"};
	
	public static final Pattern memoryPattern = Pattern.compile("[0-9]{1,3}GB",Pattern.CASE_INSENSITIVE);
	public static final Pattern modelPattern = Pattern.compile("(3GS|3G|4S|3|4|5)( |/)",Pattern.CASE_INSENSITIVE);
	
	public PhoneOS phoneOS;
	public String model;
	public String memorySize;
	
	public PhoneProperties() {
		
	}
	
	public String signature() {
		return phoneOS.name + " " + model + " " + memorySize;
	}
	
	public static PhoneProperties parse(String title) {
		String temp = title + " ";
		System.out.println("Generating properties for " + title);
		title = title.toLowerCase();
		StringTokenizer tok = new StringTokenizer(title.toLowerCase(), " /");
		PhoneProperties ret = new PhoneProperties();
		while(tok.hasMoreTokens()) {
			String s = tok.nextToken();
			PhoneOS os = PhoneOS.findMatch(s);
			if(os!=null) {
				ret.phoneOS = os;
				continue;
			}
			String model = findMatch(models, s);
			if(model!=null) {
				ret.model = model;
				continue;
			}
			
		}
		ret.model = findModel(temp);
		ret.memorySize = findSize(temp);
		return ret;
	}
	
	public static String findModel(String text) {
		Matcher m = modelPattern.matcher(text);
		if(m.find()) {
			String s = m.group(); 
			return s.substring(0, s.length()-1);
		}
		return null;
	}
	
	public static String findSize(String text) {
		Matcher m = memoryPattern.matcher(text);
		if(m.find()) {
			return m.group();
		}
		return null;
	}
	
	public static String findMatch(String[] items, String text) {
		for(String s: items) {
			if(s.toLowerCase().equals(text)) {
				return s;
			}
		}
		return null;
	}
	
}
