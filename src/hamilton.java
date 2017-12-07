import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class hamilton<E> extends Graph<E> {

    ArrayList<Vertex<E>> vArray = new ArrayList<>();

    public hamilton() {

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
