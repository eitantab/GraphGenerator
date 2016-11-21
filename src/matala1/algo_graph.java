package matala1;

import java.util.PriorityQueue;

public class algo_graph {

	public String pathbetween(int a ,int b , Graph G){

		Dijkstra ans= new Dijkstra(G.arrv,a);
		return ans.getPathTo(b);

	}

	public class Dijkstra {
		private Vertex[] graph;
		private int start;

		public Dijkstra(Vertex[] g, int start) {
			this.start = start;
			this.graph = copy(g);
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

		public String getAllDistance() {
			String ans = "[";
			for (int i = 0; i < graph.length; i++) {
				ans += graph[i].distance + (i != graph.length-1 ? "," : "");
			}
			return ans + "]";
		}

		private Vertex[] copy(Vertex[] g) {
			int n = g.length;
			Vertex[] temp = new Vertex[n];
			for (int i = 0; i < n; i++) {
				temp[i] = new Vertex(g[i]);
			}
			return temp;
		}
		//	
	}
}
