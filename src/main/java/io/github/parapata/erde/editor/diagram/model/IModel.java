package io.github.parapata.erde.editor.diagram.model;

import java.io.Serializable;

/**
 * IModel.
 *
 * @author modified by parapata
 */
public interface IModel extends Cloneable, Serializable {
    default String generateId() {
        return String.valueOf((System.nanoTime()));
    }
}
