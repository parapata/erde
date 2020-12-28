package io.github.erde.core.util;

import org.eclipse.jface.preference.PreferenceConverter;
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
        return chosenFont[0].getName() + " " + chosenFont[0].getHeight();
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

    public static class FontDataWrapper {

        private FontData[] fontData;

        public FontDataWrapper(String fontData) {
            this.fontData = toFontData(fontData);
        }

        public FontDataWrapper(FontData[] fontData) {
            this.fontData = fontData;
        }

        public FontData[] getFontData() {
            return fontData;
        }

        public String getFontDataString() {
            return toFontDataString(fontData);
        }

        @Override
        public String toString() {
            if (fontData == null || fontData.length == 0) {
                return "";
            }
            return fontData[0].getName() + " " + fontData[0].getHeight();
        }
    }

    public static FontData[] toFontData(String font) {
        if (font == null) {
            font = "";
        }
        return PreferenceConverter.basicGetFontData(font);
    }

    public static String toFontDataString(FontData[] fontData) {
        return PreferenceConverter.getStoredRepresentation(fontData);
    }

}
