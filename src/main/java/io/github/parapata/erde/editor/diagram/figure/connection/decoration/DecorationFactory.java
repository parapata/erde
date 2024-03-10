package io.github.parapata.erde.editor.diagram.figure.connection.decoration;

import io.github.parapata.erde.editor.diagram.figure.connection.decoration.idef1x.IDEF1XOneDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.idef1x.IDEF1XTargetDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.idef1x.IDEF1XZeroOneSourceDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ie.IEOneDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ie.IEOptionalTargetDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ie.IETargetDecoration;
import io.github.parapata.erde.editor.diagram.figure.connection.decoration.ie.IEZeroOneDecoration;

/**
 * DecorationFactory.
 *
 * @author modified by parapata
 */
public class DecorationFactory {

    public static final String NOTATION_IE = "IE";
    public static final String NOTATION_IDEF1X = "IDEF1X";
    public static final int VIEW_MODE_LOGICAL = 0;
    public static final int VIEW_MODE_PHYSICAL = 1;
    public static final int VIEW_MODE_BOTH = 2;
    public static final int NOTATION_LEVLE_DETAIL = 0;
    public static final int NOTATION_LEVLE_TITLE = 1;
    public static final int NOTATION_LEVLE_COLUMN = 2;
    public static final int NOTATION_LEVLE_KEY = 3;
    public static final int NOTATION_LEVLE_EXCLUDE_TYPE = 4;
    public static final int NOTATION_LEVLE_NAME_AND_KEY = 5;

    public static Decoration getDecoration(String notation, String parentCardinality, String childCardinality) {

        Decoration decoration = new Decoration();
        if ("0..1".equals(parentCardinality)) {
            if (NOTATION_IDEF1X.equals(notation)) {
                decoration.setSourceDecoration(new IDEF1XZeroOneSourceDecoration());
            } else {
                decoration.setSourceDecoration(new IEZeroOneDecoration());
            }
        } else {
            if (NOTATION_IDEF1X.equals(notation)) {
                decoration.setSourceDecoration(new IDEF1XOneDecoration());
            } else {
                decoration.setSourceDecoration(new IEOneDecoration());
            }
        }
        if ("0..n".equals(childCardinality)) {
            if (NOTATION_IDEF1X.equals(notation)) {
                // 添字 なし
                decoration.setTargetDecoration(new IDEF1XTargetDecoration());
            } else {
                decoration.setTargetDecoration(new IEOptionalTargetDecoration());
            }

        } else if ("1".equals(childCardinality)) {
            if (NOTATION_IDEF1X.equals(notation)) {
                decoration.setTargetDecoration(new IDEF1XOneDecoration());
            } else {
                decoration.setTargetDecoration(new IEOneDecoration());
            }
        } else if ("0..1".equals(childCardinality)) {
            if (NOTATION_IDEF1X.equals(notation)) {
                // 添字 Z
                decoration.setTargetDecoration(new IDEF1XTargetDecoration());
                decoration.setTargetLabel("Z");
            } else {
                decoration.setTargetDecoration(new IEZeroOneDecoration());
            }
        } else {
            if (NOTATION_IDEF1X.equals(notation)) {
                // 添字 P
                decoration.setTargetDecoration(new IDEF1XTargetDecoration());
                decoration.setTargetLabel("P");
            } else {
                decoration.setTargetDecoration(new IETargetDecoration());
            }
        }
        return decoration;
    }
}
