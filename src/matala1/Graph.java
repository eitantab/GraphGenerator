package matala1;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.*;

class Vertex implements Comparable<Vertex> {
	
	public int id, parent;
	public boolean isVisited;
	public double distance;
	public D_Edge[] edges;
	
	public Vertex(int id,D_Edge[] edges) {
		this.id = id;
		this.edges = edges;
		isVisited = false;
		distance = Double.POSITIVE_INFINITY;
		parent = -1;
		for (int i = 0; i < edges.length; i++) {
			edges[i]=new D_Edge(i, 0);
			
		}
	}
	public void updateEdge(D_Edge e){
		edges[e.vertex].iscoonect=true;
		edges[e.vertex].weigth=e.weigth;
	}
	
	
	public Vertex(Vertex vertex) {
		id = vertex.id;
		edges = new D_Edge[vertex.edges.length];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new D_Edge(vertex.edges[i].vertex, vertex.edges[i].weigth);
		}
		isVisited = vertex.isVisited;
		distance = vertex.distance;
		parent = vertex.parent;
	}
	

	@Override
	public String toString() {
		return "	\n"+"id="+id+Arrays.toString(edges) + "]	";
	}
	@Override
	public int compareTo(Vertex v) {
		return ((Double)this.distance).compareTo(v.distance);
	}
}

class D_Edge {
	public int vertex;
	public double weigth;
	boolean iscoonect;
	public D_Edge(int vertex, double weigth) {
		this.vertex = vertex;
		this.weigth = weigth;
		this.iscoonect=false;
	}
	@Override
	public String toString() {
		return "[v=" + vertex + ", weigth=" + weigth + ", IC=" + iscoonect + "]";
	}
	
	
}



public class Graph {
      int v;
      int e;
      Vertex [] arrv;
      int i=0;
	  public  Graph(String path) throws FileNotFoundException
	  {
	    int e1,e2;
	    double  weigth;
	    
	    File inFile = new File ("C:\\Users\\eitan\\github\\matala1\\src\\matala1\\G0.txt");
	    Scanner sc = new Scanner (inFile);
	    this.v=Integer.parseInt(sc.nextLine());
	    this.e=Integer.parseInt(sc.nextLine());
	    arrv= new Vertex [this.v];
	    for (int i = 0; i < arrv.length; i++) {
			arrv[i]=new Vertex(i,new D_Edge[v]);
		}
	    while (sc.hasNextLine())
	    {
	      String line = sc.nextLine();
	      StringTokenizer st = new StringTokenizer(line);
	      while (st.hasMoreTokens()) {
	          e1=Integer.parseInt (st.nextToken());
	          e2=Integer.parseInt (st.nextToken());
	          weigth=Double.parseDouble(st.nextToken());
	          D_Edge e=new D_Edge(e2, weigth);
	          D_Edge ee=new D_Edge(e1, weigth);
	          arrv[e1].updateEdge(e);
	          arrv[e2].updateEdge(ee);
	        
	      }
	      System.out.println (line);
	    }
	    sc.close();
	    for (int i = 0; i < arrv.length; i++) {
	    	for (int k = 0; k < arrv.length; k++) {
				
			
	    	if (arrv[i].edges[k].iscoonect==false)
	    		arrv[i].edges[k].weigth=Double.POSITIVE_INFINITY;
	    	}
		}
	    
	  }
	  
	    public static void WriteFile() throws IOException{
	    {
	      File outFile = new File ("output.txt");
	      FileWriter fWriter = new FileWriter (outFile);
	      PrintWriter pWriter = new PrintWriter (fWriter);
	      pWriter.println ("This is a line.");
	      pWriter.println ("This is another line.");
	      pWriter.close();
	    }
	    }

	
	public static void main(String[] args) {
		try {
			Graph G = new Graph("FJGDKJF");
		System.out.println(Arrays.toString(G.arrv));
		System.out.println((new algo_graph()).pathbetween(0, 5, G));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
}