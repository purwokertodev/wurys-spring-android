package jimat.simpleconsumerest.model;

/**
 * Author :) WURIYANTO on 23/03/2016.
 * JMAT UMP.INC
 */
public class Greeting {

    private String id;
    private String content;

    public Greeting() {

    }

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
