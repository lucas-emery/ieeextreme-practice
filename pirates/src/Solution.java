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

        //int[] map = new int[n*m];

        Graph<Integer> map = new Graph<>();

        String input;
        boolean cloned;
        boolean island;
        int k;
        for (int i = 0; i < n; i++) {
            //tk = new StringTokenizer(in.readLine());
            input = in.readLine();
            for (int j = 0; j < m; j++) {
                k = (i*m)+j;
                cloned = false;

                //island = tk.nextToken().equals("O");
                island = input.charAt(j) == 'O';

                if(j > 0) {
                    cloned = map.cloneNode(k - 1, k, island);

                    if (cloned)
                        map.mergeNode(k - m - 1, k);
                    else
                        cloned = map.cloneNode(k - m - 1, k, island);
                }

                if(cloned)
                    map.mergeNode(k - m, k);
                else
                    cloned = map.cloneNode(k - m, k, island);

                if(!cloned)
                    map.addNode(k, island);
            }
        }

        System.out.println(map.toString(n, m));
    }

    static private class Graph<V> {

        HashMap<V, Node> nodes;

        private class Node {
            Set<Node> neighbors;
            Set<V> keys;
            boolean visited;
            boolean island;

            Node(boolean island) {
                neighbors = new HashSet<>();
                keys = new HashSet<>();
                this.island = island;
                visited = false;
            }
        }

        public Graph() {
            nodes = new HashMap<>();
        }

        public boolean addNode(V v, boolean island) {
            if(nodes.containsKey(v))
                return false;

            Node n = new Node(island);
            nodes.put(v, n);
            n.keys.add(v);

            return true;
        }

        public boolean cloneNode(V v, V w, boolean island) {
            Node n = nodes.get(v);
            if(n == null || n.island != island)
                return false;

            if(nodes.get(w) != null)
                mergeNode(v, w);
            else {
                nodes.put(w, n);
                n.keys.add(w);
            }

            return true;
        }

        public void mergeNode(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            if(n1 != null && n2 != null && n1 != n2 && n1.island == n2.island) {
                n2.neighbors.remove(n1);
                for (Node n : n2.neighbors) {
                    n.neighbors.remove(n2);
                    n.neighbors.add(n1);
                }
                for (V key : n2.keys) {
                    nodes.put(key, n1);
                }
                n1.neighbors.addAll(n2.neighbors);
                n1.keys.addAll(n2.keys);
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

        public String toString(int height, int width) {
            StringBuilder sb = new StringBuilder();
            HashMap<Node, Integer> labels = new HashMap<>();

            int nextLabel = 1;
            int label;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Node n = nodes.get((i*width)+j);
                    if(labels.get(n) == null) {
                        label = nextLabel;
                        labels.put(n, label);
                        nextLabel++;
                    }
                    else {
                        label = labels.get(n);
                    }
                    sb.append(label);
                    sb.append(' ');
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }
}
