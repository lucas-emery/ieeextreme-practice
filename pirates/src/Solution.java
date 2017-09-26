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

        Graph<Integer> map = new Graph<>();

        String input;
        boolean cloned;
        boolean island;
        int k;
        for (int i = 0; i < n; i++) {
            input = in.readLine();
            for (int j = 0; j < m; j++) {
                k = (i*m)+j;
                cloned = false;

                island = input.charAt(j) == 'O';

                map.addNode(k, island);

                map.mergeNode(k - m, k);
                if(j > 0) {
                    map.mergeNode(k - 1, k);
                    map.mergeNode(k - m - 1, k);
                }

            }
        }

        for (int i = 0; i < q; i++) {
            tk = new StringTokenizer(in.readLine());

            int x1 = Integer.parseInt(tk.nextToken()) - 1;
            int y1 = Integer.parseInt(tk.nextToken()) - 1;
            int x2 = Integer.parseInt(tk.nextToken()) - 1;
            int y2 = Integer.parseInt(tk.nextToken()) - 1;

            System.out.println(map.search((x1*m)+y1, (x2*m)+y2));
        }
    }

    private static class Graph<V> {

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
            if(n1 == null || n2 == null || n1 == n2)
                return;

            if(n1.island == n2.island) {
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
            else {
                n1.neighbors.add(n2);
                n2.neighbors.add(n1);
            }
        }

        public void addArc(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            n1.neighbors.add(n2);
            n2.neighbors.add(n1);
        }

        public void removeArc(V v, V w) {
            Node n1 = nodes.get(v);
            Node n2 = nodes.get(w);
            n1.neighbors.remove(n2);
            n2.neighbors.remove(n1);
        }

        public int search(V from, V to) {
            PriorityQueue<Node> pq = new PriorityQueue<>(100, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    if(o1 == null || o2 == null || o1.island == o2.island)
                        return 0;

                    if(o1.island)
                        return 1;
                    else
                        return -1;
                }
            });

            for (Node n : nodes.values()) {
                n.visited = false;
            }

            Node origin = nodes.get(from);
            Node target = nodes.get(to);
            Node current;
            int count = 0;

            pq.add(origin);

            while((current = pq.poll()) != target) {
                if(!current.visited) {
                    current.visited = true;
                    if (current.island)
                        count++;
                    pq.addAll(current.neighbors);
                }
            }

            return count;
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

            sb.append('\n');

            for (Node n : labels.keySet()) {
                sb.append(labels.get(n));
                sb.append(" -> ");
                for (Node ady : n.neighbors) {
                    sb.append(labels.get(ady));
                    sb.append(", ");
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }
}
