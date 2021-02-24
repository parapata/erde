package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;

import io.github.erde.core.exception.SystemException;
import io.github.erde.dialect.IDialect;
import io.github.erde.editor.diagram.editpart.TableEditPart;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.dialog.DDLDisplayDialog;

/**
 * SelectedTablesDDLAction.
 *
 * @author modified by parapata
 */
public class SelectedTablesDDLAction extends Action implements IERDEAction {

    public SelectedTablesDDLAction() {
        super();
        setId(SELECTED_TABLES_DDL);
        setText(ACTION_SELECTED_TABLES_DDL.getValue());
    }

    @Override
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        ISelection sel = viewer.getSelection();
        if (sel instanceof IStructuredSelection) {
            List<TableModel> tableModels = new ArrayList<>();
            Object[] selected = ((IStructuredSelection) sel).toArray();
            for (Object obj : selected) {
                if (obj instanceof TableEditPart) {
                    tableModels.add((TableModel) ((TableEditPart) obj).getModel());
                }
            }

            RootModel root = ((RootModel) viewer.getContents().getModel());
            IDialect dialect = root.getDialectProvider().getDialect();

            dialect.setSchema(false);
            dialect.setDrop(true);
            dialect.setComment(true);
            dialect.setAlterTable(false);
            dialect.setLineSeparator(System.lineSeparator());

            try (StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw)) {
                if (tableModels.isEmpty()) {
                    dialect.createDDL(root, pw);
                } else {
                    for (TableModel tableModel : tableModels) {
                        dialect.createTableDDL(root, tableModel, pw);
                    }
                }
                pw.flush();
                DDLDisplayDialog dialog = new DDLDisplayDialog(Display.getDefault().getActiveShell(), sw.toString());
                dialog.open();
            } catch (IOException e) {
                throw new SystemException(e);
            }
        }
    }
}
