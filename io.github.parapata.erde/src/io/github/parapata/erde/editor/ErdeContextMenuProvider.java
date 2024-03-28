package io.github.parapata.erde.editor;

import static io.github.parapata.erde.Resource.*;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import io.github.parapata.erde.editor.action.AutoLayoutAction;
import io.github.parapata.erde.editor.action.ChangeDialectAction;
import io.github.parapata.erde.editor.action.CopyAsImageAction;
import io.github.parapata.erde.editor.action.DeleteMarkerAction;
import io.github.parapata.erde.editor.action.DomainEditAction;
import io.github.parapata.erde.editor.action.GenerateAction;
import io.github.parapata.erde.editor.action.IErdeAction;
import io.github.parapata.erde.editor.action.ImportFromJDBCAction;
import io.github.parapata.erde.editor.action.SelectedTablesDDLAction;
import io.github.parapata.erde.editor.action.ValidateAction;
import io.github.parapata.erde.generate.GeneratorProvider;

/**
 * ErdeContextMenuProvider.
 *
 * @author modified by parapata
 */
public class ErdeContextMenuProvider extends ContextMenuProvider {

    private ActionRegistry registry;

    public ErdeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
        super(viewer);
        this.registry = registry;
    }

    @Override
    public void buildContextMenu(IMenuManager menu) {
        menu.add(registry.getAction(IErdeAction.QUICK_OUTLINE));
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
        menu.add(registry.getAction(IErdeAction.TOGGLE_MODEL));
        menu.add(registry.getAction(IErdeAction.TOGGLE_NOTATION));
        menu.add(new ChangeDialectAction());
        menu.add(new Separator());
        menu.add(registry.getAction(GEFActionConstants.ZOOM_IN));
        menu.add(registry.getAction(GEFActionConstants.ZOOM_OUT));
        menu.add(new Separator());
        menu.add(new CopyAsImageAction());
        menu.add(registry.getAction(ActionFactory.PRINT.getId()));
        menu.add(new Separator());

        // Validation Actions
        MenuManager validationMenuManager = new MenuManager(ACTION_VALIDATION.getValue());
        validationMenuManager.add(new ValidateAction());
        validationMenuManager.add(new DeleteMarkerAction());
        menu.add(validationMenuManager);

        // Import Actions
        MenuManager importMenuManager = new MenuManager(ACTION_IMPORT.getValue());
        importMenuManager.add(new ImportFromJDBCAction());
        // importMenuManager.add(new ImportFromDiagramAction());
        menu.add(importMenuManager);

        // Export Actions
        DefaultEditDomain domain = (DefaultEditDomain) getViewer().getEditDomain();
        ErdeDiagramEditor editor = (ErdeDiagramEditor) domain.getEditorPart();

        MenuManager exportMenuManager = new MenuManager(ACTION_EXPORT.getValue());
        GeneratorProvider.getGeneraters().forEach(generater -> {
            exportMenuManager.add(new GenerateAction(generater, editor));
        });
        menu.add(exportMenuManager);
        menu.add(new SelectedTablesDDLAction());
    }
}
