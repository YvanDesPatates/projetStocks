package stocks.DAO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.PrintWriter;
import java.util.List;

public abstract class DAOXML {
    private final Document doc;
    private final String path;

    public DAOXML(Document doc, String path){
        this.doc = doc;
        this.path = path;
    }

    public Document getDoc() { return doc; }

    protected Element getCatalogue(String nomCatalogue) {
        Element root = doc.getRootElement();
        List<Element> lProd = root.getChildren("catalogue");
        int i = 0;
        while (i < lProd.size() && !lProd.get(i).getAttributeValue("nomC").equals(nomCatalogue))
            i++;
        if (i < lProd.size())
            return lProd.get(i);
        else
            return null;
    }

    protected boolean save() {
        XMLOutputter out = new XMLOutputter();
        try {
            out.output(doc, new PrintWriter(path));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
