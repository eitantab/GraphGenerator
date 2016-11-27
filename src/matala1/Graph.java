package ready;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
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

class ChechBlack
{
	int numOfQueries,vertex1,vertex2,numOfBlackVertexes;
	String answer="";
	int[] blackVertexes;
	File file;
	BufferedWriter bw;
	public ChechBlack(String blackListQuerisList,Graph graph,long startTime) throws IOException
	{
		String content = "";
		File inFile = new File (blackListQuerisList); //path
		Scanner sc = new Scanner (inFile);

		numOfQueries=Integer.parseInt(sc.nextLine());

		/////write to file
		try {
			file = new File("/home/efi/Desktop/מבנה תוכנה/Ex1/output.txt");
			//EITAN'S ADRESS : 	"C:\\Users\\eitan\\github\\matala1\\src\\matala1\\output.txt"
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);


		} catch (IOException e) {
			e.printStackTrace();
		}
		///end write to file 
		System.out.println("//////////");
		for (int i = 0; i <numOfQueries; i++) 
		{

			String line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) 
			{
				vertex1=Integer.parseInt (st.nextToken());
				vertex2=Integer.parseInt (st.nextToken());
				numOfBlackVertexes=Integer.parseInt(st.nextToken());
				blackVertexes= new int[numOfBlackVertexes];
				for (int j = 0; j <numOfBlackVertexes; j++)
				{
					blackVertexes[j]=Integer.parseInt(st.nextToken());
				}
				content=vertex1+ "  "+ vertex2+" "+ numOfBlackVertexes+ "  " +Arrays.toString(blackVertexes);
				content=content.replace(",", "").replace("[", "").replace("]", "").trim(); 

				answer=new Graph_algo().distancebetweenBL(vertex1, vertex2,graph,blackVertexes);
				bw.write(content+" "+answer+"\n");

			}
		}
		
		String info="Graph: ";
		String tieStr="";
		Graph_algo GA=new Graph_algo(graph);
		
		boolean b=GA.checkTriangleInequality(graph);

		tieStr= (b==true) ? tieStr="TIE" : "!TIE";
		info=info+" " +"|V|=" +graph.howmanyVs();
		String info1=" |E|=" +graph.howmanyEs() ;
		String info2=" , "+tieStr ;
		
	
		String info3= " , Radius= "+GA.radius();
		
		String info4=", Diameter: "+GA.diameter()+",  "  ;
		long endTime = System.currentTimeMillis();
		String info5=+(endTime - startTime) +" ms";
		bw.write(info+info1+info2+info3+info4+info5);
		bw.close();
		sc.close();
		System.out.println("Done writing output file.");


	}


}

public class Graph {
	int v;
	int e;
	private int numofedg;
	Vertex [] arrv;
	int i=0;

	public Graph(Graph g) {

		this.e=g.e;
		this.v=g.v;
		this.arrv=new Vertex [g.v];
		for (int i = 0; i < arrv.length; i++) {
			arrv[i]=new Vertex(g.arrv[i]);
		}
		this.numofedg=g.numofedg;
	}
	public  Graph(String vertexesTxtList) throws FileNotFoundException
	{
		int e1,e2;
		double  weigth;
		numofedg=0;
		File inFile = new File (vertexesTxtList);
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
		}
		sc.close();
		for (int i = 0; i < arrv.length; i++) 
		{
			for (int k = 0; k < arrv.length; k++) 
			{
				if (arrv[i].edges[k].iscoonect==false)
				{
					arrv[i].edges[k].weigth=Double.POSITIVE_INFINITY;
				}
				else
					numofedg=numofedg+1;
			}
		}


	}
	public int howmanyVs()
	{
		return this.v;
	}
	
	public int howmanyEs()
	{
		return (this.numofedg)/2;
	}

	public static void WriteFile() throws IOException
	{
		
			File outFile = new File ("output.txt");
			FileWriter fWriter = new FileWriter (outFile);
			PrintWriter pWriter = new PrintWriter (fWriter);
			pWriter.println ("This is a line.");
			pWriter.println ("This is another line.");
			pWriter.close();
		
	}

	public static void main(String[] args) throws IOException 
	{
		
		long startTime =System.currentTimeMillis();
		Graph G = new Graph("/home/efi/Desktop/מבנה תוכנה/Ex1/Graph_2000_30945_1480078366845.txt");
		String queriesBlackListTxtFile="/home/efi/Desktop/מבנה תוכנה/Ex1/t.txt";
		
		////EITAN'S ADRESS : 	"C:\\Users\\eitan\\github\\matala1\\src\\matala1\\
		try {
			ChechBlack CB= new ChechBlack(queriesBlackListTxtFile,G,startTime);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
