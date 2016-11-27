package ready;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.PriorityQueue;


public class Graph_algo {
	double[] ans;

	public Graph_algo(Graph g){
		ans= assinsetivi(g);


	}
	public Graph_algo(){
		ans= new double[0];
	}
	/* public double findDiameter(Graph g){ /// dose not work....
		double max=0;
		int maxi=0;
		double[] a=getalldis(0, g);
		for (int i = 0; i < a.length; i++) {
			if (a[i]>max){
				maxi=i;
				max=a[i];
			}
		}
		a=getalldis(maxi, g);
		for (int i = 0; i < a.length; i++) {
			if (a[i]>max){
				maxi=i;
				max=a[i];
			}
		}
		return max;
		
	 }*/
	private double[] assinsetivi(Graph g)
	{
		double max;
		double[] ans=new double [g.arrv.length];
		for (int i = 0; i < ans.length; i++) {
			//System.out.println(i);
			double[] a=getalldis(i, g);
			//System.out.println(Arrays.toString(a));		
			////////////////////
			max= 0;
			for (int j = 0; j < a.length; j++) {
				if ((a[j]>max)&&(a[j]<Double.POSITIVE_INFINITY)&&a[j]!=0){
					max=a[j];
				}
			}
			ans[i]=max;

		}
		return ans;
	}

	public double radius()
	{
		OptionalDouble a=Arrays.stream(ans).min();

		return a.getAsDouble();
	}
	public double diameter()
	{
		OptionalDouble a=Arrays.stream(ans).max();

		return a.getAsDouble();
	}
	public String distancebetweenBL(int a ,int b , Graph G, int[] BL){
		Graph temp=new Graph(G);
		for (int i = 0; i < BL.length; i++) {
			for (int j = 0; j < G.arrv.length; j++) {
				if (BL[i]==j){
					for (int j2 = 0; j2 < G.arrv.length; j2++) 

						temp.arrv[j].edges[j2].weigth=Double.POSITIVE_INFINITY;;
				}
				else
					temp.arrv[j].edges[BL[i]].weigth=Double.POSITIVE_INFINITY;;
			}
		}
		double g=this.distancebetween(a, b, temp);
		if (g==Double.POSITIVE_INFINITY){
			return "no possiple path without going throgh Black List ";
		}
		return Double.toString(g);

	}

	public String pathbetween(int a ,int b , Graph G){

		Dijkstra ans= new Dijkstra(G.arrv,a);
		return ans.getPathTo(b);

	}
	public double[] getalldis(int a , Graph G ){

		Dijkstra ans= new Dijkstra(G.arrv,a);
		return ans.getAllDistance();


	}
	public double distancebetween(int a, int b,Graph G){
		if( a==b) return 0;
		Dijkstra ans= new Dijkstra(G.arrv,a);
		double[] s=ans.getAllDistance();
		//		StringTokenizer st = new StringTokenizer(s,",]");
		//		String ans1=st.nextToken();
		//		for (int i = 1; i <= b; i++) {
		//			ans1=st.nextToken();
		//		}
		//
		//		return Double.parseDouble(ans1);
		return s[b];

	}

	public class Dijkstra {
		private Vertex[] graph;
		private int start;

		public Dijkstra(Vertex[] g, int start) {
			this.start = start;
			
			this.graph = copy(g);      // The problem is here!!!!!!!!!!!!!!!!!
			//this.graph=g.clone();
			//this.graph=g;
			PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
			graph[start].distance = 0;
			queue.add(graph[start]);
			while(!queue.isEmpty()) {
				int v = queue.poll().id;
				graph[v].isVisited = true;
				for(D_Edge edge : graph[v].edges) {
					//if (edge.iscoonect){
					int u = edge.vertex;
					if(!graph[u].isVisited  ) {

						if(graph[u].distance > graph[v].distance + edge.weigth) {
							queue.remove(graph[u]);
							graph[u].parent = v;
							graph[u].distance = graph[v].distance + edge.weigth;
							queue.add(graph[u]);
						}
					}
				}//}
			}
		}

		public String getPathTo(int vertex) {
			if(vertex >= graph.length || vertex < 0) return "";
			String ans = "" + vertex;
			int v = vertex;
			while(graph[v].parent != -1) {
				ans = graph[v].parent + "->" + ans;
				v = graph[v].parent;
			}
			if(v == start) {
				return ans;
			}
			return "";
		}

		public double[] getAllDistance() {
			double[] arr= new double [this.graph.length];
			//String ans = "[";
			for (int i = 0; i < graph.length; i++) {
				arr[i]=graph[i].distance;
				//ans += graph[i].distance + (i != graph.length-1 ? "," : "");
			}
			return arr;
		}

		public Vertex[] copy(Vertex[] g) 
		{
			int n = g.length;
			Vertex[] temp = new Vertex[n];
			for (int i = 0; i < n; i++) {
				temp[i] = new Vertex(g[i]);
			}
			return temp;
		}
		//	
	}
	public  boolean checkTriangleInequality(Graph g) {
		for (int j = 0; j < g.howmanyVs(); j++) {
			for (int i = 0; i < g.arrv[j].edges.length; i++) {

				if(distancebetween(j,g.arrv[j].edges[i].vertex,g)<=g.arrv[j].edges[i].weigth){
					return false;
				}
			}


		}

		return true;
	}

}
