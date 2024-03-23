package io.github.parapata.erde.core.util.swt;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;

/**
 * FontPropertyDescriptor.
 *
 * @author modified by parapata
 */
public class FontPropertyDescriptor extends AbstractDialogPropertyDescriptor {

    public FontPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }

    @Override
    protected String getDisplayText(Object value) {
        FontData[] chosenFont = ((FontDataWrapper) value).getFontData();
        if (chosenFont == null || chosenFont.length == 0) {
            return "";
        }
        return String.format("%s %s", chosenFont[0].getName(), chosenFont[0].getHeight());
    }

    @Override
    protected Object openDialogBox(Object value, Control cellEditorWindow) {
        FontData[] chosenFont = ((FontDataWrapper) value).getFontData();

        FontDialog fontDialog = new FontDialog(cellEditorWindow.getShell());
        if (chosenFont != null) {
            fontDialog.setFontList(chosenFont);
        }
        FontData font = fontDialog.open();
        if (font != null) {
            return new FontDataWrapper(new FontData[] { font });
        }
        return null;
    }
}
