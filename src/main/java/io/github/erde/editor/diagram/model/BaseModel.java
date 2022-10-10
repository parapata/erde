package io.github.erde.editor.diagram.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseModel.
 *
 * @author modified by parapata
 */
public abstract class BaseModel implements IModel {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(BaseModel.class);
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        logger.info("登録処理イベント発生 : {}", listener.getClass().getName());
        listeners.addPropertyChangeListener(listener);
    }

    public void firePropertyChange(String propName, Object oldValue, Object newValue) {
        logger.info("更新処理イベント発生 : {}, {}, {}", propName, oldValue, newValue);
        listeners.firePropertyChange(propName, oldValue, newValue);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        logger.info("削除処理イベント発生 : {}", listener.getClass().getName());
        listeners.removePropertyChangeListener(listener);
    }
}
