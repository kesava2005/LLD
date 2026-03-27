import java.util.ArrayList;
import java.util.List;

interface Persistance{

    void save(String data);

}

class DBPersistance implements Persistance{

    DBType db;

    DBPersistance(DBType db){

        this.db = db;

    }

    public void save(String data){

        db.save(data);

    }
    
}

interface DBType{

    void save(String data);

}

class MySQL implements DBType{

    public void save(String data){

        System.out.println("Logic to save the doc into mysql database\n" + data);

    }

}

class MongoDB implements DBType{

    public void save(String data){

        System.out.println("Logic to save the doc into mongoDB database\n" + data);

    }

}

class FilePersistance implements Persistance{

    FileType file;

    FilePersistance(FileType file){

        this.file = file;

    }

    public void save(String data){

        file.save(data);

    }

}

interface FileType{

    void save(String data);

}

class TextFile implements FileType{

    public void save(String data){

        System.out.println("Logic to save the doc into file\n" + data);

    }

}

class Document{

    private List<DocumentElement> elements = new ArrayList<>();

    public void addElement(DocumentElement element){

        elements.add(element);

    }
    
    public String render(){

        StringBuilder doc_data = new StringBuilder();

        for(DocumentElement element : elements){

            doc_data.append(element.render());
            doc_data.append("\n");

        }

        return doc_data.toString();

    }

}

interface DocumentElement{

    public String render();


}

class TextElement implements DocumentElement{

    private String data;

    TextElement(String data){

        this.data = data;

    }

    public String render(){

        return "rendered text with value " + data ;

    }

}

class ImageElement implements DocumentElement{

    private String image_path;

    ImageElement(String path){

        image_path = path;

    }

    public String render(){

        return "rendered image with path " + image_path;

    }

}

class VideoElement implements DocumentElement{

    private String video_path;

    VideoElement(String path){

        video_path = path;

    }

    public String render(){

        return "rendered video with path " + video_path;

    }

}

class DocumentEditor {

    private Document document;
    private Persistance persistance;

    DocumentEditor(Document document, Persistance persistance) {
        this.document = document;
        this.persistance = persistance;
    }

    public void setPersistance(Persistance persistance) {
        this.persistance = persistance;
    }

    public void addText(String text) {
        document.addElement(new TextElement(text));
    }
    
    public void addImage(String path) {
        document.addElement(new ImageElement(path));
    }
    
    public void addVideo(String path) {
        document.addElement(new VideoElement(path));
    }

    public void render() {
        System.out.println(document.render());
    }

    public void save() {
        if (persistance == null) {
            System.out.println("No persistence strategy set!");
            return;
        }
        persistance.save(document.render());
    }
}

public class LLD1 {
    public static void main(String[] args) {

        // Step 1: Create Document
        Document doc = new Document();

        // Step 2: Start with File Persistence
        Persistance filePersistence = new FilePersistance(new TextFile());

        // Step 3: Create Editor
        DocumentEditor editor = new DocumentEditor(doc, filePersistence);

        // Step 4: Add Elements
        editor.addText("Hello World");
        editor.addImage("/images/sample.png");
        editor.addVideo("/videos/demo.mp4");

        // Step 5: Render Document
        System.out.println("---- Rendering Document ----");
        editor.render();

        // Step 6: Save to File
        System.out.println("---- Saving to File ----");
        editor.save();

        // Step 7: Switch to MySQL DB
        Persistance mysqlPersistence = new DBPersistance(new MySQL());
        editor.setPersistance(mysqlPersistence);

        System.out.println("---- Saving to MySQL ----");
        editor.save();

        // Step 8: Switch to MongoDB
        Persistance mongoPersistence = new DBPersistance(new MongoDB());
        editor.setPersistance(mongoPersistence);

        System.out.println("---- Saving to MongoDB ----");
        editor.save();

        // Step 9: Add more content
        editor.addText("New Content After Switching DB");

        System.out.println("---- Rendering Updated Document ----");
        editor.render();

        // Step 10: Save again (MongoDB)
        System.out.println("---- Saving Updated Doc to MongoDB ----");
        editor.save();
    }
}