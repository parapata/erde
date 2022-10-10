package io.github.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import io.github.erde.core.util.swt.FontDataWrapper;
import io.github.erde.editor.diagram.figure.NoteFigure;
import io.github.erde.editor.diagram.model.NoteModel;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * This class has been ported from AmaterasUML.
 *
 * @author modified by parapata
 */
public class NoteEditPart extends AbstractERDEntityEditPart {

    private DirectEditManager directManager;
    private Font font;

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NoteDirectEditPolicy());
    }

    @Override
    protected IFigure createFigure() {
        NoteFigure figure = new NoteFigure();
        return figure;
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        if (font != null) {
            font.dispose();
        }

        RootModel root = (RootModel) getParent().getModel();
        FontData[] fontData = FontDataWrapper.toFontData(root.getFontData());
        font = new Font(Display.getDefault(), fontData);
        figure.setFont(font);

        ((NoteFigure) figure).setText(((NoteModel) getModel()).getContent());
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (font != null) {
            font.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
    }

    @Override
    public void performRequest(Request req) {
        if (req.getType().equals(RequestConstants.REQ_DIRECT_EDIT) || req.getType().equals(RequestConstants.REQ_OPEN)) {
            performDirectEdit();
            return;
        }
        super.performRequest(req);
    }

    private void performDirectEdit() {
        if (directManager == null) {
            directManager = new NoteDirectEditManager();
        }
        directManager.show();
    }

    /**
     * DirectEditManager
     */
    private class NoteDirectEditManager extends DirectEditManager {

        public NoteDirectEditManager() {
            super(NoteEditPart.this, MultiLineCellEditor.class, new NoteCellEditorLocator());
        }

        @Override
        protected void initCellEditor() {
            getCellEditor().setValue(((NoteModel) getModel()).getContent());
            Text text = (Text) getCellEditor().getControl();
            text.selectAll();
        }
    }

    /**
     * CellEditorLocator
     */
    private class NoteCellEditorLocator implements CellEditorLocator {
        @Override
        public void relocate(CellEditor celleditor) {
            IFigure figure = getFigure();
            Text text = (Text) celleditor.getControl();
            Rectangle rect = figure.getBounds().getCopy();
            figure.translateToAbsolute(rect);
            // text.setBounds(rect.x + 5, rect.y + 5, rect.width - 5, rect.height - 5);
            text.setBounds(rect.x, rect.y, rect.width, rect.height);
        }
    }

    /**
     * DirectEditCommand
     */
    private class DirectEditCommand extends Command {

        private String oldName;

        private String newName;

        @Override
        public void execute() {
            NoteModel model = (NoteModel) getModel();
            oldName = model.getContent();
            model.setContent(newName);
        }

        public void setName(String name) {
            newName = name;
        }

        @Override
        public void undo() {
            NoteModel model = (NoteModel) getModel();
            model.setContent(oldName);
        }
    }

    private class NoteDirectEditPolicy extends DirectEditPolicy {

        @Override
        protected Command getDirectEditCommand(DirectEditRequest request) {
            DirectEditCommand command = new DirectEditCommand();
            command.setName((String) request.getCellEditor().getValue());
            return command;
        }

        @Override
        protected void showCurrentEditValue(DirectEditRequest request) {
        }
    }
}
