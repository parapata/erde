package io.github.erde.editor;

import static io.github.erde.Resource.*;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import io.github.erde.editor.action.AutoLayoutAction;
import io.github.erde.editor.action.ChangeDBTypeAction;
import io.github.erde.editor.action.CopyAsImageAction;
import io.github.erde.editor.action.DeleteMarkerAction;
import io.github.erde.editor.action.DomainEditAction;
import io.github.erde.editor.action.GenerateAction;
import io.github.erde.editor.action.IERDEAction;
import io.github.erde.editor.action.ImportFromDiagramAction;
import io.github.erde.editor.action.ImportFromJDBCAction;
import io.github.erde.editor.action.SelectedTablesDDLAction;
import io.github.erde.editor.action.ValidateAction;
import io.github.erde.generate.GeneratorProvider;

/**
 * ERDContextMenuProvider.
 *
 * @author modified by parapata
 */
public class ERDContextMenuProvider extends ContextMenuProvider {

    private ActionRegistry registry;

    public ERDContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
        super(viewer);
        this.registry = registry;
    }

    @Override
    public void buildContextMenu(IMenuManager menu) {
        menu.add(registry.getAction(IERDEAction.QUICK_OUTLINE));
        menu.add(new Separator());
        menu.add(registry.getAction(ActionFactory.UNDO.getId()));
        menu.add(registry.getAction(ActionFactory.REDO.getId()));
        menu.add(new Separator());
        menu.add(registry.getAction(ActionFactory.COPY.getId()));
        menu.add(registry.getAction(ActionFactory.PASTE.getId()));
        menu.add(registry.getAction(ActionFactory.DELETE.getId()));
        menu.add(new Separator());
        menu.add(new AutoLayoutAction());
        menu.add(new DomainEditAction());
        menu.add(registry.getAction(IERDEAction.TOGGLE_MODEL));
        menu.add(registry.getAction(IERDEAction.TOGGLE_NOTATION));
        menu.add(new ChangeDBTypeAction());
        menu.add(new Separator());
        menu.add(registry.getAction(GEFActionConstants.ZOOM_IN));
        menu.add(registry.getAction(GEFActionConstants.ZOOM_OUT));
        menu.add(new Separator());
        menu.add(new CopyAsImageAction());
        menu.add(registry.getAction(ActionFactory.PRINT.getId()));
        menu.add(new Separator());

        // Validation Actions
        MenuManager validationMenuManager = new MenuManager(ACTION_VALI_DATION.getValue());
        validationMenuManager.add(new ValidateAction());
        validationMenuManager.add(new DeleteMarkerAction());
        menu.add(validationMenuManager);

        // Import Actions
        MenuManager importMenuManager = new MenuManager(ACTION_IMPORT.getValue());
        importMenuManager.add(new ImportFromJDBCAction());
        importMenuManager.add(new ImportFromDiagramAction());
        menu.add(importMenuManager);

        // Export Actions
        DefaultEditDomain domain = (DefaultEditDomain) getViewer().getEditDomain();
        ERDiagramEditor editor = (ERDiagramEditor) domain.getEditorPart();

        MenuManager exportMenuManager = new MenuManager(ACTION_EXPORT.getValue());
        GeneratorProvider.getGeneraters().forEach(generater -> {
            exportMenuManager.add(new GenerateAction(generater, editor));
        });
        menu.add(exportMenuManager);
        menu.add(new SelectedTablesDDLAction());
    }
}
