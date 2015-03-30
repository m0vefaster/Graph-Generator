import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;

import java.util.Random;
import java.util.Vector;

public class SampleXMLParser {

	public void getAllUserNames(String fileName) {
		
		int nos_nodes,nos_edges;
		String tv1="";
		String tv2="";
		String tv3="";
		String tv4="";
		String tv5="";
		String tv6="";
		try 
			{
			FileWriter f0=new FileWriter("Inter.txt");
			Random XGEN= new Random();
			Random YGEN=new Random();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);
			if (file.exists()) {
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();

				// Print root element of the document
				//System.out.println("Root element of the document: "
				//		+ docEle.getNodeName());
				
						
			
				//f0.close();

				NodeList nodeList = docEle.getElementsByTagName("node");
				nos_nodes=nodeList.getLength();

				// Print total nodes in document
				System.out.println("Total node: " + nos_nodes);
				
				NodeList edgeList = docEle.getElementsByTagName("edge"); 	
				nos_edges=edgeList.getLength();
				
				String source="Name of the Graph:"+"\nNumber of Rows:0\nNumber of Columns:0\nHeight of Rows:0\nWidth of Columns:0"+"\nNumber of Nodes:"+nos_nodes+"\nNumber of Edges:"+nos_edges;
				char buffer[]=new char[160];
				source.getChars(0,source.length(),buffer,0);
				for(int a=0;a<source.length();a++)
					f0.append(buffer[a]);
				
				// Print total edges in document
				System.out.println("Total edges: " + nos_edges);
				
				for(int j=0;j<nos_nodes;j++){
					NamedNodeMap attributes =(nodeList.item(j)).getAttributes();
        		System.out.println("NodeName: " +(nodeList.item(j)).getNodeName());
         		for (int i = 0; i < attributes.getLength(); i++) {
         			tv1="Index:";
					tv2="Label:";
					tv3="Color(RGB):-";
            		Node attribute = attributes.item(i);
             		System.out.println("AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
             
					 if ((attribute.getNodeName()).equals("id"))
							tv1+=((Integer.parseInt((attribute.getNodeValue()).substring(1)))+1);
					else if((attribute.getNodeName()).equals("label"))
						tv2+=attribute.getNodeValue();
					else if((attribute.getNodeName()).equals("color"))
						tv3+=attribute.getNodeValue();
				}
				source="\n"+tv1+"\n"+tv2+"\n"+"X-location:"+(XGEN.nextInt(1279))+"\n"+"Y-location:"+(YGEN.nextInt(1079))+"\n"+tv3;
				source.getChars(0,source.length(),buffer,0);
				for(int a=0;a<source.length();a++)
						f0.append(buffer[a]);
					
				}
				
				String incidentEdgeInfo[]=new String[20];
				int number;
				String edgeNos;
				
				for(int j=0;j<nos_edges;j++){
					NamedNodeMap attributes1 =(edgeList.item(j)).getAttributes();
					tv1="Index:";
					tv2="Label:";
					tv3="Color(RGB):-";
					tv4="Start Node Index:-";
					tv5="End Node Index:-";
					tv6="Is Straight?:true";
        		System.out.println("NodeName: " +(edgeList.item(j)).getNodeName());
         		for(int i = 0; i < attributes1.getLength(); i++) {
            		Node attribute1 = attributes1.item(i);
            		edgeNos=""+(i+1);
             		System.out.println("AttributeName: " + attribute1.getNodeName() + ", attributeValue: " + attribute1.getNodeValue());
             		if((attribute1.getNodeName()).equals("source"))
             		{
             			
             			tv4+=((Integer.parseInt((attribute1.getNodeValue()).substring(1)))+1);
             			number=(Integer.parseInt((attribute1.getNodeValue()).substring(1)));
             			if(incidentEdgeInfo[number]==null)
             			{
             				incidentEdgeInfo[number]=edgeNos;
             			}
             			else
             				incidentEdgeInfo[number]=incidentEdgeInfo[number]+","+edgeNos;
             		}
             		else if((attribute1.getNodeName()).equals("target"))
             		{
             			tv5+=((Integer.parseInt((attribute1.getNodeValue()).substring(1)))+1);
             			number=(Integer.parseInt((attribute1.getNodeValue()).substring(1)));
             		    	if(incidentEdgeInfo[number]==null)
             			{
             				incidentEdgeInfo[number]=edgeNos;
             			}
             			else
             				incidentEdgeInfo[number]=incidentEdgeInfo[number]+","+edgeNos;
             		}
					else if((attribute1.getNodeName()).equals("color"))
						tv3+=attribute1.getNodeValue();
					else if((attribute1.getNodeName()).equals("label"))
						tv2+=attribute1.getNodeValue();
        		}
        		tv1+=(j+1);
        		System.out.println("Test");
        		System.out.println("WTF!!"+tv4);
        			source="\n"+tv1+"\n"+tv4+"\n"+tv5+"\n"+tv3+"\n"+tv6+"\n"+tv2;
        		System.out.println(source);
				source.getChars(0,source.length(),buffer,0);
				for(int a=0;a<source.length();a++)
						f0.append(buffer[a]);
					
				}
				f0.close();
				
				File fileObj=new File("inter.txt");
				int def=13;
				for(int s=0;s<nos_nodes;s++)
				{
					insertStringInFile(fileObj,def,"Incident Edges Index's:"+incidentEdgeInfo[s]);
					def=def+6;
				}
				
			//	printVector(incidentEdgeInfo);
			for(String elements:incidentEdgeInfo)
			System.out.println("\n"+elements);
				
				

						}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {

		SampleXMLParser parser = new SampleXMLParser();
		parser.getAllUserNames("C:/Documents and Settings/PERSONAL/My Documents/Downloads/23.08.09/17.08.09/test1.graphml");
	}
	
	public void insertStringInFile
         (File inFile, int lineno, String lineToBeInserted) 
       throws Exception {
     // temp file
     File outFile = new File("$$$$$$$$.tmp");
     
     // input
     FileInputStream fis  = new FileInputStream(inFile);
     BufferedReader in = new BufferedReader
         (new InputStreamReader(fis));

     // output         
     FileOutputStream fos = new FileOutputStream(outFile);
     PrintWriter out = new PrintWriter(fos);

     String thisLine = "";
     int i =1;
     while ((thisLine = in.readLine()) != null) {
       if(i == lineno) out.println(lineToBeInserted);
       out.println(thisLine);
       i++;
       }
    out.flush();
    out.close();
    in.close();
    
    inFile.delete();
    outFile.renameTo(inFile);
    }

	

}
