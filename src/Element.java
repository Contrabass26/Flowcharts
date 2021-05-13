import java.util.ArrayList;
import java.util.List;

public class Element {

    int type;
    int[] position;
    String text;
    List<Element> connections = new ArrayList<>();

    public Element(int type, String text, int[] position) {
        this.type = type;
        this.position = position;
        this.text = text;
    }

    public void connect(Element e) {
        connections.add(e);
    }

    public int get_pos(int index) {
        return position[index];
    }

    public int[] left() {
        int x = position[0];
        int y = position[1] + Drawing.shape_size[1] / 2;
        return new int[]{x, y};
    }

    public int[] right() {
        int x = position[0] + Drawing.shape_size[0];
        int y = position[1] + Drawing.shape_size[1] / 2;
        return new int[]{x, y};
    }

    public int[] top() {
        int x = position[0] + Drawing.shape_size[0] / 2;
        int y = position[1];
        return new int[]{x, y};
    }

    public int[] bottom() {
        int x = position[0] + Drawing.shape_size[0] / 2;
        int y = position[1] + Drawing.shape_size[1];
        return new int[]{x, y};
    }
}
