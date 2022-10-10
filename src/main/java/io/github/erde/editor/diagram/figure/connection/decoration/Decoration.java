package io.github.erde.editor.diagram.figure.connection.decoration;

import org.eclipse.draw2d.RotatableDecoration;

/**
 * Decoration.
 *
 * @author modified by parapata
 */
public class Decoration {

    private RotatableDecoration sourceDecoration;
    private RotatableDecoration targetDecoration;
    private String targetLabel;

    public RotatableDecoration getSourceDecoration() {
        return sourceDecoration;
    }

    public void setSourceDecoration(RotatableDecoration sourceDecoration) {
        this.sourceDecoration = sourceDecoration;
    }

    public RotatableDecoration getTargetDecoration() {
        return targetDecoration;
    }

    public void setTargetDecoration(RotatableDecoration targetDecoration) {
        this.targetDecoration = targetDecoration;
    }

    public String getTargetLabel() {
        return targetLabel;
    }

    public void setTargetLabel(String targetLabel) {
        this.targetLabel = targetLabel;
    }
}
