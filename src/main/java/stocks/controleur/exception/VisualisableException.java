package stocks.controleur.exception;

public class VisualisableException extends Exception{
    private String title;

    public VisualisableException() {
        this("une erreur est apparue");
    }

    public VisualisableException(String message) {
        this("Erreur", message);
    }

    public VisualisableException(String title, String message){
        super(message);
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
