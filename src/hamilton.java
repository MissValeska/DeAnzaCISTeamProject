/*

Written entirely by Valeska Victoria, both classes

 */


import java.io.PrintWriter;
import java.util.*;

public class hamilton<E> extends Graph<E> {

    private ArrayList<Vertex<E>> vArray;
    private int vNum;
    public LinkedStack<Pair<E, Pair<E, Double>>> removedEdges;

    public hamilton() {
        vArray = new ArrayList<>();
        vNum = vertexSet.entrySet().size();
        removedEdges = new LinkedStack<>();
        // Fix undo everywhere
    }

    public boolean isEmpty() {
        return vertexSet.isEmpty();
    }

    public boolean removeEdge(E src, E end) {

        Vertex<E> startVertex = vertexSet.get(src);
        double cost = startVertex.adjList.get(end).second;
        removedEdges.push(new Pair(src, new Pair(end, cost)));
        return remove(src, end);

    }

    public void undoLastRemove()
    {
        // undo the last remove
        Pair<E, Pair<E, Double>> temp = removedEdges.pop();

        insert(temp.first, temp.second.first, temp.second.second);
    }

    public boolean contains(Vertex<E> vrt, HashMap<E, Pair<Vertex<E>, Double>> adjList) {

        Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> itr = adjList.entrySet().iterator();

        while(itr.hasNext()) {

            Vertex<E> tmp = itr.next().getValue().first;

            if(tmp.equals(vrt)) {
                return true;
            }

        }
        return false;

    }

    public void writeSolution(PrintWriter wrt) {

        if(wrt == null) {
            return;
        }

        StringBuilder strB = new StringBuilder();

        for (Vertex<E> tmp: vArray) {
            Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> itr = tmp.iterator();
            strB.append(tmp.data.toString() + " : ");
            boolean first = true;
            while(itr.hasNext()) {

                String str = itr.next().getValue().first.data.toString();

                if(!first) {
                    strB.append(", " + str);
                }
                else {
                    strB.append(str);
                }
                first = false;

            }
            strB.append("\n");
            //strB.append(" " + tmp.data.toString() + " ");
        }

        wrt.print(strB.toString());
        wrt.close();
    }

    private void printSolution() {

        System.out.println("A solution exists, here is the path:\n");

        for (Vertex<E> tmp: vArray) {
            System.out.print(" " + tmp.data.toString() + " ");
        }
        System.out.println();

    }

    public void solve() {

        Iterator<Map.Entry<E, Vertex<E>>> itr = vertexSet.entrySet().iterator();

        Vertex<E> startVertex = itr.next().getValue();

        startVertex.visit();

        vArray.add(startVertex);

        if(!solution()) {
            System.out.println("Solution does not exist");
        }
        else {
            printSolution();
        }

    }


    public Vertex<E> getVertex(E data) {
        return vertexSet.get(data);
    }

    public Iterator<Map.Entry<E, Vertex<E>>> iterator() {
        return vertexSet.entrySet().iterator();
    }

    private boolean solution() {

        //Object[] arr = vertexSet.entrySet().toArray();
        Iterator<Map.Entry<E, Vertex<E>>> itr = vertexSet.entrySet().iterator();
        itr.next();

        if(vArray.size() == vNum) {

            return true;

        }

        while(itr.hasNext()) {

            Vertex<E> cur = itr.next().getValue();

            int pos = vArray.size() - 1;
            boolean flag = contains(cur, vArray.get(pos).adjList) && !vArray.contains(cur);
            if(flag) {

                vArray.add(cur);
                vArray.get(vArray.size() - 1).visit();

                if(solution()) {
                    return true;
                }

                else {
                    vArray.get(vArray.size() - 1).unvisit();
                    vArray.remove(vArray.size() - 1);
                }

            }

        }

        return false;

    }

    public void displayAdj() {
        showAdjTable();
    }

   public void insert(E source, E dest, double cost) {

        addEdge(source, dest, cost);
        vNum = vertexSet.entrySet().size();

   }


}


class Visit<E> implements Visitor<E> {

    hamilton<E> ham;

    public Visit (hamilton<E> ham) {
        this.ham = ham;
    }

    // visits the first appearance of the object in the vertex set.

    @Override
    public void visit(E obj) {

        Iterator<Map.Entry<E, Vertex<E>>> itr = ham.iterator();

        while(itr.hasNext()) {
            Map.Entry<E, Vertex<E>> tmp = itr.next();

            if(tmp.getValue().data.equals(obj)) {
                tmp.getValue().visit();
                System.out.println(obj.toString());
                break;
            }

        }

    }
}
