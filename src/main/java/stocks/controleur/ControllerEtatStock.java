package stocks.controleur;

public class ControllerEtatStock extends ControllerBase{

    public String getEtatStock(){
        return getCatalogue().toString();
    }
}
