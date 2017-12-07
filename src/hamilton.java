import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class hamilton<E> extends Graph<E> {

    private ArrayList<Vertex<E>> vArray = new ArrayList<>();
    private int vNum = vertexSet.entrySet().size();

    public hamilton() {

    }

    public void solutionHelper(Vertex<E> startVertex) {

        Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> itr = startVertex.iterator();


        while(itr.hasNext()) {

            Vertex<E> tmp = itr.next().getValue().first;

        }

    }

    public boolean contains(Vertex<E> vrt, HashMap<E, Pair<Vertex<E>, Double>> adjList) {

        Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> itr = adjList.entrySet().iterator();

        while(itr.hasNext()) {

            if(itr.next().getValue().first.equals(vrt)) {
                return true;
            }

        }
        return false;

    }

    public void printSolution() {

        System.out.println("A solution exists, here is the path");

        for (Vertex<E> tmp: vArray) {
            System.out.print(" " + tmp.data.toString() + " ");
        }

    }

    public void solHelp() {

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

    public boolean solution() {

        //Object[] arr = vertexSet.entrySet().toArray();
        Iterator<Map.Entry<E, Vertex<E>>> itr = vertexSet.entrySet().iterator();
        itr.next();

        if((vArray.size() - 1) == vNum) {

            // check if there is an edge from the last included vertex to the first one
            // potentially unnecessary
            if(true) {
                return true;
            }
            else {
                return false;
            }

        }

        while(itr.hasNext()) {

            Vertex<E> cur = itr.next().getValue();

            if(contains(cur, vArray.get(vArray.size()-2).adjList) && !vArray.contains(cur)) {
                cur.visit();
                vArray.add(cur);

                if(solution()) {
                    return true;
                }

                vArray.get(vArray.size()-1).unvisit();
                vArray.remove(vArray.size()-1);

            }

        }

        return false;

    }

    public void displayAdj() {
        Iterator<Map.Entry<E, Vertex<E>>> itr = vertexSet.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<E, Vertex<E>> tmp1 = itr.next();
            Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> tmp = tmp1.getValue().iterator();
            System.out.println("[" + tmp1.getKey() + "]");
            while(tmp.hasNext()) {
                Map.Entry<E, Pair<Vertex<E>, Double>> tmp3 = tmp.next();
                System.out.println(tmp3.getKey() + ":" + tmp3.getValue().first.data + ":" + tmp3.getValue().second);
            }
        }


    }

   public void insert(E source, E dest, double cost) {
       vArray.add(new Vertex<>(source));
       vArray.add(new Vertex<>(dest));

        addEdge(source, dest, cost);

   }


}
