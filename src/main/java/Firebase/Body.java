package Firebase;

public class Body {

    public String updated_at;
    public String closed_at;
    public String merged_at;

    public Body() {

    }

    public Body(String updated_at, String closed_at, String merged_at) {
        this.updated_at = updated_at;
        this.closed_at = closed_at;
        this.merged_at = merged_at;

    }

    public Body(String updated_at, String closed_at) {
        this.updated_at = updated_at;
        this.closed_at = closed_at;
        this.merged_at = null;
    }

    public Body(String updated_at) {
        this.updated_at = updated_at;
        this.closed_at = null;
        this.merged_at = null;
    }
}
