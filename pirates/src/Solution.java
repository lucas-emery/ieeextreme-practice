import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) throws IOException{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tk = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(tk.nextToken());
        int m = Integer.parseInt(tk.nextToken());
        int q = Integer.parseInt(tk.nextToken());

        int[][] map = new int[n][m];

        for (int i = 0; i < n; i++) {
            tk = new StringTokenizer(in.readLine());
            for (int j = 0; j < m; j++) {
                //crete map
            }
        }
    }

    private class Graph<V> {

        HashMap<V, Node> nodes;

        private class Node {
            Set<Node> neighbors;
            boolean visited;
            boolean island;

            Node(boolean island) {
                neighbors = new HashSet<>();
                this.island = island;
                visited = false;
            }
        }

        public void addNode(V v, boolean island) {
            if(nodes.containsKey(v))
                return;

            Node n = new Node(island);
            nodes.put(v, n);
        }

        public void cloneNode(V v, V w) {
            Node n = nodes.get(v);
            if(n == null)
                return;

            if(nodes.get(w) != null)
                mergeNode(v, w);
            else
                nodes.put(w, n);


        }

        public void mergeNode(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            if(n1.island == n2.island) {
                n2.neighbors.remove(n1);
                for (Node n : n2.neighbors) {
                    n.neighbors.remove(n2);
                    n.neighbors.add(n1);
                }
                n1.neighbors.addAll(n2.neighbors);
                nodes.remove(w);
                nodes.put(w, n1);
            }
        }

        public void addArc(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            n1.neighbors.add(n1);
            n2.neighbors.add(n2);
        }

        public void removeArc(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            n1.neighbors.remove(n1);
            n2.neighbors.remove(n2);
        }
    }
}
