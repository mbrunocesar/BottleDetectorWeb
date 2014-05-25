package json;

import global.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Parser {


	final static String jsonPath = Path.thisPath + "json/";

	public static String[] readFromLink(String link){
		String[] targets = new String[100];
		String readFully = "";

		int numLinks = 0;	

		if(link == null){
			link = jsonPath+"input.json";
		}else if(!link.startsWith("http")){
			link = jsonPath+link;
		}

		BufferedReader reader = null;
		try {
			if(link.startsWith("http")){
				URL jsonURL = new URL(link);
				reader = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
			}else{
				reader = new BufferedReader(new FileReader(link));
			}

			String line = null;
			while ((line = reader.readLine()) != null) {
				readFully = readFully.concat(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int itens = 0;

		while((itens = readFully.indexOf("items",itens+1)) != -1){
			int itemLink = itens+5;
			while((itemLink = readFully.indexOf("link",itemLink+4))!=-1){
				int linkBegin = readFully.indexOf("\"", itemLink+5);
				int linkEnd = readFully.indexOf("\"", linkBegin+1);

				targets[numLinks] = readFully.substring(linkBegin+1, linkEnd);
				numLinks++;
			}
		}

		return targets;
	}


	public static String saveToLink(String[][] responses, String exitName){
		String fileName = "";

		BufferedWriter writer = null;

		if(exitName == null){
			fileName = System.currentTimeMillis()+".json";
		}else{
			fileName = exitName+".json";
		}

		try{
			writer = new BufferedWriter( new FileWriter(jsonPath+fileName));
			writer.write( "{\"base\": {\"timestamp\": \""+System.currentTimeMillis()+"\", \"items\": [");

			for(String[] response:responses){
				if(response[0]==null){
					break;
				}
				writer.write( "\n{\"status\": \""+response[0]+"\", \"response\": \""+response[1]+"\", \"kooaba-response\": \""+response[3]+"\"},");
			}
			writer.write( "\n{\"response\": \"ends\"} ] }}");			

		}catch ( IOException e)	{
			e.printStackTrace();
		}finally{
			if ( writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return fileName;

	}

	public static String saveAsText(String[][] responses){
		String jsonOutput = "";

		jsonOutput = "{\"base\": {\"timestamp\": \""+System.currentTimeMillis()+"\", \"items\": [";

		for(String[] response:responses){
			if(response[0]==null){
				break;
			}
			jsonOutput += "\n{\"status\": \""+response[0]+"\", \"response\": \""+response[1]+"\", \"kooaba-response\": \""+response[3]+"\"},";
		}
		jsonOutput += "\n{\"response\": \"ends\"} ] }}";			

		return jsonOutput;

	}

}
