/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Joshu
 */
public class ParseQuiz {
    
    public static void main(){
        
    }
    
     /**
         * This nasty little method loads file and creates the array list
         * of nodes for world.
         * @param filename
         * @return NodeList
         */
	private ArrayList<Node> loadNodes(String filename) {
		ArrayList<Node> newNodesList = new ArrayList<>();

		//Node newNode = new Node();

		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));

			String[] edges;
                        String[] items;
			String fileRead = input.readLine();
			while (fileRead != null) {
				StringBuilder description = new StringBuilder();
				tokens = fileRead.split(",");
                                fileRead = input.readLine();
                                items = fileRead.split(",");
                                
				edges = Arrays.copyOfRange(tokens, 1, tokens.length); // edge list

				fileRead = input.readLine();

				while (!fileRead.equals("###")) {
					description.append(fileRead).append("\n");
					fileRead = input.readLine();

				}
				Node newNode = new Node(tokens[0], edges, description.toString(),items);  // node name
				
                                fileRead = input.readLine();
				newNodesList.add(newNode);

			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(NodeList.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(NodeList.class.getName()).log(Level.SEVERE, null, ex);
		}
		return newNodesList;
	}

    
}
