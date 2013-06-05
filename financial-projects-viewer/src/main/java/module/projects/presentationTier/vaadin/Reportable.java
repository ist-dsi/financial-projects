package module.projects.presentationTier.vaadin;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface Reportable {

    public void write(HSSFSheet sheet, HSSFFont headersFont);
}
