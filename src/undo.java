/* Made entirely by Valeska Victoria */

public class undo extends Graph<String> {

    LinkedStack<Pair<Vertex<String>, Pair<Vertex<String>, Double>>> edgeUndo = new LinkedStack<>();
    LinkedStack<Pair<Vertex<String>, Vertex<String>>> edgeAddUndo = new LinkedStack<Pair<Vertex<String>,Vertex<String>>>();

    public void addToEdge(Vertex<String> source, Vertex<String> dest, double cost) {

        edgeAddUndo.push(new Pair<Vertex<String>, Vertex<String>>(source, dest));
        addEdge(source.getData(), dest.getData(), cost);

    }

    public void removeEdge(Vertex<String> source, Vertex<String> dest, Double cost) {

        Pair<Vertex<String>, Double> tmp = new Pair<>(dest, cost);

        edgeUndo.push(new Pair<Vertex<String>, Pair<Vertex<String>, Double>>(source, tmp));
        remove(source.getData(), dest.getData());

    }

    public void undoRemove() {

        Pair<Vertex<String>, Pair<Vertex<String>, Double>> tmp = edgeUndo.pop();
        String source = tmp.first.getData();
        String dest = tmp.second.first.getData();
        Double cost = tmp.second.second;

        addEdge(source, dest, cost);

    }

    public void undoAdd() {

        Pair<Vertex<String>, Pair<Vertex<String>, Double>> tmp = edgeUndo.pop();
        String source = tmp.first.getData();
        String dest = tmp.second.first.getData();

        remove(source, dest);

    }

    public void main() {

        Vertex<String> source = new Vertex<>("Les Invalides");
        Vertex<String> dest = new Vertex<>("Pantheon");

        addToEdge(source, dest, 2.1);
        removeEdge(source, dest, 2.1);
        undoRemove();


    }

}
