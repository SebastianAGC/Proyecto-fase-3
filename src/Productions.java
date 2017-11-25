import java.util.ArrayList;

public class Productions {

    private String head="";
    private String body="";
    private int number = 0;


    public Productions(String head, String body) {
        this.head = head;
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return head +
                "=" + body;
    }
}
