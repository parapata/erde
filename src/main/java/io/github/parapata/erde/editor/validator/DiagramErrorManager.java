package io.github.parapata.erde.editor.validator;

import java.util.ArrayList;
import java.util.List;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.IndexModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;

/**
 * This object contains ER-Diagram validation errors.
 *
 * @author modified by parapata
 */
public class DiagramErrorManager {

    private List<DiagramError> errors = new ArrayList<>();

    private static final String ERROR_PREFIX = "[ERROR]";
    private static final String WARNING_PREFIX = "[WARN]";

    /**
     * Add an error messagefor TableModel.
     *
     * @param level the error level
     * @param table the table model
     * @param message the error message
     */
    public void addError(String level, TableModel table, String message) {

        String msg = createTableMessage(table, message);
        DiagramError error = new DiagramError(table, msg, level, table.getId(), table.getPhysicalName());
        if (level.equals(ERDPlugin.LEVEL_ERROR)) {
            this.errors.add(error);
            addErrorMessageToModel(table, ERROR_PREFIX + msg);

        } else if (level.equals(ERDPlugin.LEVEL_WARNING)) {
            this.errors.add(error);
            addErrorMessageToModel(table, WARNING_PREFIX + msg);
        }
    }

    /**
     * Add an error message for ColumnModel.
     *
     * @param level the error level
     * @param table the table model
     * @param column the column model
     * @param message the error message
     */
    public void addError(String level, TableModel table, ColumnModel column, String message) {
        String msg = createColumnMessage(table, column, message);
        DiagramError error = new DiagramError(table, msg, level, table.getId(), table.getPhysicalName());
        if (level.equals(ERDPlugin.LEVEL_ERROR)) {
            this.errors.add(error);
            addErrorMessageToModel(table, ERROR_PREFIX + createColumnMessage(column, message));

        } else if (level.equals(ERDPlugin.LEVEL_WARNING)) {
            this.errors.add(error);
            addErrorMessageToModel(table, WARNING_PREFIX + createColumnMessage(column, message));
        }
    }

    /**
     * Add an error message for IndexModel.
     *
     * @param level the error level
     * @param table the table model
     * @param index the index model
     * @param message the error message
     */
    public void addError(String level, TableModel table, IndexModel index, String message) {
        String msg = createIndexMessage(table, index, message);
        DiagramError error = new DiagramError(table, msg, level, table.getId(), table.getPhysicalName());
        if (level.equals(ERDPlugin.LEVEL_ERROR)) {
            this.errors.add(error);
            addErrorMessageToModel(table, ERROR_PREFIX + createIndexMessage(index, message));

        } else if (level.equals(ERDPlugin.LEVEL_WARNING)) {
            this.errors.add(error);
            addErrorMessageToModel(table, WARNING_PREFIX + createIndexMessage(index, message));
        }
    }

    /**
     * Returns all errors.
     *
     * @return all errors
     */
    public List<DiagramError> getErrors() {
        return this.errors;
    }

    private void addErrorMessageToModel(TableModel table, String message) {
        String error = table.getError();
        if (error.length() > 0) {
            error = error + "\n";
        }
        error = error + message;
        table.setError(error);
    }

    private String createColumnMessage(TableModel table, ColumnModel column, String message) {
        return String.format("[%s.%s]%s", table.getPhysicalName(), column.getPhysicalName(), message);
    }

    private String createColumnMessage(ColumnModel column, String message) {
        return String.format("[%s]%s", column.getPhysicalName(), message);
    }

    private String createIndexMessage(TableModel table, IndexModel index, String message) {
        return String.format("[%s.%s]%s", table.getPhysicalName(), index.getIndexName(), message);
    }

    private String createIndexMessage(IndexModel index, String message) {
        return String.format("[%s]%s", index.getIndexName(), message);
    }

    private String createTableMessage(TableModel table, String message) {
        return String.format("[%s]%s", table.getPhysicalName(), message);
    }
}
