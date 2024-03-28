package io.github.parapata.erde.editor.validator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import io.github.parapata.erde.ErdePlugin;

/**
 * DiagramError.
 *
 * @author modified by parapata
 */
public class DiagramError {

    private Object target;
    private String message;
    private String level;
    private String id;
    private String name;

    public DiagramError(Object target, String message, String level, String id, String name) {
        this.target = target;
        this.message = message;
        this.level = level;
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the error model
     *
     * @return the error model
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Returns the error message
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the error level
     *
     * @return the error level
     */
    public String getLevel() {
        return this.level;
    }

    /**
     * Returns the error id
     *
     * @return the error id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Returns the error name
     *
     * @return the error name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Add marker to the given file.
     *
     * @param file the ER-Diagram file
     */
    public void addMarker(IFile file) {
        if (level == ErdePlugin.LEVEL_ERROR) {
            addMarker(file, IMarker.SEVERITY_ERROR, message, id, name);
        } else {
            addMarker(file, IMarker.SEVERITY_WARNING, message, id, name);
        }
    }

    /**
     * Adds marker to the specified line.
     *
     * @param resource the target resource
     * @param type the error type that defined by IMaker
     * @param message the error message
     */
    private void addMarker(IResource resource, int type, String message, String id, String name) {
        try {
            Map<String, Object> map = new HashMap<>();
            // marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
            map.put(IMarker.SEVERITY, Integer.valueOf(type));
            map.put(IMarker.MESSAGE, message);
            map.put(IMarker.SOURCE_ID, id);
            map.put(IMarker.LOCATION, name);
            IMarker marker = resource.createMarker(IMarker.PROBLEM);
            marker.setAttributes(map);
        } catch (CoreException e) {
            ErdePlugin.logException(e);
        }
    }
}
