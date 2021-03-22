package io.github.erde.editor.action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;

import io.github.erde.core.util.swt.UIUtils;

/**
 * IERDEAction.
 *
 * @author modified by parapata
 */
public interface IERDEAction {

    String AUTO_LAYOUT = AutoLayoutAction.class.getName();
    String DOMAING_EDIT = DomainEditAction.class.getName();
    String QUICK_OUTLINE = QuickOutlineAction.class.getName();
    String CHANGE_DB_TYPE = ChangeDBTypeAction.class.getName();
    String COPY_AS_IMAGE = CopyAsImageAction.class.getName();
    String DELETE_MARKER = DeleteMarkerAction.class.getName();

    String IMPORT_FROM_DIAGRAM = ImportFromDiagramAction.class.getName();
    String IMPORT_FROM_JDBC = ImportFromJDBCAction.class.getName();

    String GENERATE = GenerateAction.class.getName();
    String SELECTED_TABLES_DDL = SelectedTablesDDLAction.class.getName();
    String TOGGLE_MODEL = ToggleModelAction.class.getName();
    String TOGGLE_NOTATION = ToggleNotationAction.class.getName();
    String VALIDATE = ValidateAction.class.getName();

    default GraphicalViewer getGraphicalViewer() {
        GraphicalViewer viewer = (GraphicalViewer) UIUtils.getActiveEditor().getAdapter(GraphicalViewer.class);
        return viewer;
    }

    default CommandStack getCommandStack2() {
        return getGraphicalViewer().getEditDomain().getCommandStack();
    }

}
