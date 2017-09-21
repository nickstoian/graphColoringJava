//Nicolas Stoian
//This program needs 2 command line arguments
//args[0] "input" for text file representing the input graph
//args[1] "output" to write the outputs

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;


public class GraphColoring {
	public static void main(String args[]){
		try{
			Scanner inFile = new Scanner(new FileReader(args[0]));
			int numNodes;
		    numNodes = inFile.nextInt();
			int[][] adjacentMatrix = new int[numNodes][numNodes];
			for(int row = 0; row < numNodes; row++){
		        for(int col = 0; col < numNodes; col++){
		        	adjacentMatrix[row][col] = 0;
		        }
		    }
			int indexStart = loadMatrix(adjacentMatrix, numNodes, inFile);
			inFile.close();
			PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
			printGraph(adjacentMatrix, numNodes, indexStart, outFile);
			outFile.println();
			outFile.println();
			printAdjacentMatrix(adjacentMatrix, numNodes, indexStart, outFile);
			int[] nodeColor = new int [numNodes];
		    for(int i = 0; i < numNodes; i++){
		        nodeColor[i] = 0;
		    }
		    outFile.println();
			outFile.println();
			colorGraph(adjacentMatrix, numNodes, indexStart, outFile, nodeColor);
			outFile.println();
			outFile.println();
			printAdjacentMatrix(adjacentMatrix, numNodes, indexStart, outFile);
			outFile.close();
		}
		catch(NoSuchElementException e){
			System.err.println("Error in input file format, check the input file and try again.");
            return;
		}
		catch(FileNotFoundException e){
			System.err.println("File not found exception, check arguements and try again.");
            return;
		}
		catch(IOException e){
			System.err.println("IO exception, check arguements and try again.");
            return;
		}
	}


	public static int loadMatrix(int[][] adjacentMatrix, int arraySize, Scanner inFile){
	    int n1;
	    int n2;
	    int indexStart = 0;
	    while(inFile.hasNext()){
	    	n1 = inFile.nextInt();
	        n2 = inFile.nextInt();;
	        if(n1 == arraySize && n2 == arraySize){
	            adjacentMatrix[0][0] = 1;
	            indexStart = 1;
	        }
	        else if(n1 == arraySize){
	            adjacentMatrix[0][n2] = 1;
	            indexStart = 1;
	        }
	        else if(n2 == arraySize){
	            adjacentMatrix[n1][0] = 1;
	            indexStart = 1;
	        }
	        else{
	            adjacentMatrix[n1][n2] = 1;
	        }
	    }
	    return indexStart;
	}

	public static void printGraph(int[][] adjacentMatrix, int arraySize, int indexStart, PrintWriter outFile){
	    if(indexStart == 0){
	        for(int row = 0; row < arraySize; row++){
	            for(int col = 0; col < arraySize; col++){
	                if (adjacentMatrix[row][col] != 0){
	                	outFile.println(row + " " + col);
	                }
	            }
	        }
	    }
	    else{
	        for(int row = 1; row <= arraySize; row++){
	            for(int col = 1; col <= arraySize; col++){
	                if(row == arraySize && col == arraySize){
	                    if(adjacentMatrix[0][0] != 0){
	                    	outFile.println(row + " " + col);
	                    }
	                }
	                else if(row == arraySize){
	                    if(adjacentMatrix[0][col] != 0){
	                    	outFile.println(row + " " + col);
	                    }
	                }
	                else if(col == arraySize){
	                    if(adjacentMatrix[row][0] != 0){
	                    	outFile.println(row + " " + col);
	                    }
	                }
	                else{
	                    if (adjacentMatrix[row][col] != 0){
	                    	outFile.println(row + " " + col);
	                    }
	                }
	            }
	        }
	    }
	}

	public static void printAdjacentMatrix(int[][] adjacentMatrix, int arraySize, int indexStart, PrintWriter outFile){

	    outFile.format("%5s", "index");
	    if(indexStart == 0){
	        for(int col = 0; col < arraySize; col++){
	        	outFile.format("%3s", col);
	        }
	        outFile.println();
	        for(int row = 0; row < arraySize; row++){
	        	outFile.format("%5s", row);
	            for(int col = 0; col < arraySize; col++){
	            	outFile.format("%3s", adjacentMatrix[row][col]);
	            }
	            outFile.println();
	        }
	    }
	    else{
	        for(int col = 1; col <= arraySize; col++){
	        	outFile.format("%3s", col);
	        }
	        outFile.println();
	        for(int row = 1; row <= arraySize; row++){
	        	outFile.format("%5s", row);
	            for(int col = 1; col <= arraySize; col++){
	                if(row == arraySize && col == arraySize){
	                	outFile.format("%3s", adjacentMatrix[0][0]);
	                }
	                else if(row == arraySize){
	                	outFile.format("%3s", adjacentMatrix[0][col]);
	                }
	                else if(col == arraySize){
	                	outFile.format("%3s", adjacentMatrix[row][0]);
	                }
	                else{
	                	outFile.format("%3s", adjacentMatrix[row][col]);
	                }
	            }
	            outFile.println();
	        }
	    }
	}


	public static void colorGraph(int[][] adjacentMatrix, int arraySize, int indexStart, PrintWriter outFile, int[] nodeColor){
	    if(indexStart == 0){
	        int numColored = 0;
	        int newColor = 0;
	        while(numColored < arraySize){
	            newColor++;
	            for(int newNode = 0; newNode < arraySize; newNode++){
	                if(nodeColor[newNode] == 0 && !(isAdjacentColored(adjacentMatrix, arraySize, newNode, newColor, nodeColor))){
	                    nodeColor[newNode] = newColor;
	                    numColored++;
	                }
	            }
	            outFile.println("newColor = " + newColor);
	            outFile.format("%-11s", "index");
	            for(int i = 0; i < arraySize; i++){
	            	outFile.format("%-3s", i);
	            }

	            outFile.println();
	            outFile.format("%-11s", "nodeColor");
	            for(int i = 0; i < arraySize; i++){
	            	outFile.format("%-3s", nodeColor[i]);
	            }
	            outFile.println();
	            outFile.println();
	        }
	        for(int i = 0; i < arraySize; i++){
	            adjacentMatrix[i][i] = nodeColor[i];
	        }
	        outFile.println("The number of colors used to color the graph = " + newColor);
	    }
	    else{
	        int numColored = 0;
	        int newColor = 0;
	        while(numColored < arraySize){
	            newColor++;
	            for(int newNode = 1; newNode <= arraySize; newNode++){
	                if(newNode == arraySize){
	                    newNode = 0;
	                }
	                if(nodeColor[newNode] == 0 && !(isAdjacentColored(adjacentMatrix, arraySize, newNode, newColor, nodeColor))){
	                    nodeColor[newNode] = newColor;
	                    numColored++;
	                }
	                if(newNode == 0){
	                    newNode = arraySize;
	                }
	            }
	            outFile.println("newColor = " + newColor);
	            outFile.format("%-11s", "index");
	            for(int i = 1; i <= arraySize; i++){
	                if(i == arraySize){
	                	outFile.format("%-3s", arraySize);
	                }
	                else{
	                	outFile.format("%-3s", i);
	                }
	            }
	            outFile.println();
	            outFile.format("%-11s", "nodeColor");
	            for(int i = 1; i <= arraySize; i++){
	                if(i == arraySize){
	                	outFile.format("%-3s", nodeColor[0]);
	                }
	                else{
	                	outFile.format("%-3s", nodeColor[i]);
	                }
	            }
	            outFile.println();
	            outFile.println();
	        }
	        for(int i = 0; i < arraySize; i++){
	            adjacentMatrix[i][i] = nodeColor[i];
	        }
	        outFile.println("The number of colors used to color the graph = " + newColor);
	    }
	}

	/* Simple version for algorithm analysis, no outputs and assumes index always starts at 0
	public static void colorGraph(int[][] adjacentMatrix, int arraySize, int indexStart, PrintWriter outFile, int[] nodeColor){
	        int numColored = 0;
	        int newColor = 0;
	        while(numColored < arraySize){
	            newColor++;
	            for(int newNode = 0; newNode < arraySize; newNode++){
	                if(nodeColor[newNode] == 0 && !(isAdjacentColored(adjacentMatrix, arraySize, newNode, newColor, nodeColor))){
	                    nodeColor[newNode] = newColor;
	                    numColored++;
	                }
	            }
	        }
	        for(int i = 0; i < arraySize; i++){
	            adjacentMatrix[i][i] = nodeColor[i];
	        }
	}*/


	public static boolean isAdjacentColored(int[][] adjacentMatrix, int arraySize, int newNode, int newColor, int[] nodeColor){
	    boolean isColored = false;
	    for(int i = 0; i < arraySize; i++){
	        if(adjacentMatrix[newNode][i] == 1 && nodeColor[i] == newColor){
	            isColored = true;
	        }
	    }
	    return isColored;
	}

}



