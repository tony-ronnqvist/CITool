package Firebase;

/**
 * Contain the data and type. This object is then being sent to Firebase
 */
public class Database {

    public Type type;
    public Data data;

    public Database() {

    }

    /**
     * The object is created before sening to firebase
     * @param type - The class Type
     * @param data - The class Data
     */
    public Database(Type type, Data data) {
        this.type = type;
        this.data = data;

    }
}
