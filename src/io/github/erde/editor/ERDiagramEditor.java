package io.github.erde.editor;

import static io.github.erde.Resource.*;

import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.ERDPlugin;
import io.github.erde.ICON;
import io.github.erde.core.exception.SystemException;
import io.github.erde.editor.action.CopyAction;
import io.github.erde.editor.action.IERDAction;
import io.github.erde.editor.action.PasteAction;
import io.github.erde.editor.action.QuickOutlineAction;
import io.github.erde.editor.action.ToggleModelAction;
import io.github.erde.editor.action.ToggleNotationAction;
import io.github.erde.editor.diagram.editpart.ERDEditPartFactory;
import io.github.erde.editor.diagram.editpart.IDoubleClickSupport;
import io.github.erde.editor.diagram.editpart.RootEditPart;
import io.github.erde.editor.diagram.model.NoteConnectionModel;
import io.github.erde.editor.diagram.model.NoteModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.persistent.ERDiagramSerializer;
import io.github.erde.editor.validator.DiagramError;
import io.github.erde.editor.validator.DiagramErrorManager;
import io.github.erde.editor.validator.DiagramValidator;
import io.github.erde.editor.validator.IERDGotoMarker;
import io.github.erde.preference.ERDPreferenceKey;

/**
 * ERDiagramEditor.
 *
 * @author modified by parapata
 */
public class ERDiagramEditor extends GraphicalEditorWithPalette
        implements IResourceChangeListener, IPropertyChangeListener, IERDGotoMarker {

    private Logger logger = LoggerFactory.getLogger(ERDiagramEditor.class);

    private boolean savePreviouslyNeeded = false;
    private boolean needViewerRefreshFlag = true;

    public ERDiagramEditor() {
        super();
        setEditDomain(new DefaultEditDomain(this));
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
        ERDPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
    }

    @Override
    protected PaletteRoot getPaletteRoot() {
        logger.info("Call getPaletteRoot");
        PaletteRoot root = new PaletteRoot();

        PaletteGroup group = new PaletteGroup(PALETTE_TOOLS.getValue());
        group.add(new SelectionToolEntry());

        group.add(createEntityEntry(PALETTE_NODE_TABLE.getValue(), TableModel.class, ICON.TABLE.getPath()));
        group.add(createConnectionEntry(PALETTE_NODE_RELATIONSHIP.getValue(), RelationshipModel.class,
                ICON.RELATION_1_N.getPath()));

        group.add(new PaletteSeparator());

        group.add(createEntityEntry(PALETTE_NODE_NOTE.getValue(), NoteModel.class, ICON.NOTE.getPath()));
        group.add(createConnectionEntry(PALETTE_NODE_NOTE_CONNECTOR.getValue(), NoteConnectionModel.class,
                ICON.COMMENT_CONNECTION.getPath()));

        group.add(new PaletteSeparator());

        root.add(group);

        return root;
    }

    @Override
    protected void setInput(IEditorInput input) {
        logger.info("Call setInput : {}", input);
        super.setInput(input);
        setPartName(input.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void createActions() {
        logger.info("Call createActions");
        super.createActions();
        GraphicalViewer viewer = getGraphicalViewer();
        ActionRegistry registry = getActionRegistry();
        IWorkbenchPart workbenchPart = this;

        registry.registerAction(new UndoRetargetAction());
        registry.registerAction(new RedoRetargetAction());

        PasteAction pasteAction = new PasteAction(this);
        registry.registerAction(pasteAction);
        getSelectionActions().add(pasteAction.getId());

        CopyAction copyAction = new CopyAction(this, pasteAction);
        registry.registerAction(copyAction);
        getSelectionActions().add(copyAction.getId());

        DeleteAction deleteAction = new DeleteAction(workbenchPart);
        deleteAction.setSelectionProvider(viewer);
        registry.registerAction(deleteAction);

        PrintAction printAction = new PrintAction(this);
        printAction.setText(ACTION_PRINT.getValue());
        printAction.setImageDescriptor(ERDPlugin.getImageDescriptor(ICON.PRINT.getPath()));
        registry.registerAction(printAction);

        // 整列アクションの作成
        IAction posLeftAction = new AlignmentAction(workbenchPart, PositionConstants.LEFT);
        registry.registerAction(posLeftAction);
        getSelectionActions().add(posLeftAction.getId());

        IAction posCenterAction = new AlignmentAction(workbenchPart, PositionConstants.CENTER);
        registry.registerAction(posCenterAction);
        getSelectionActions().add(posCenterAction.getId());

        IAction posRightAction = new AlignmentAction(workbenchPart, PositionConstants.RIGHT);
        registry.registerAction(posRightAction);
        getSelectionActions().add(posRightAction.getId());

        IAction posTopAction = new AlignmentAction(workbenchPart, PositionConstants.TOP);
        registry.registerAction(posTopAction);
        getSelectionActions().add(posTopAction.getId());

        IAction posMiddleAction = new AlignmentAction(workbenchPart, PositionConstants.MIDDLE);
        registry.registerAction(posMiddleAction);
        getSelectionActions().add(posMiddleAction.getId());

        IAction posBottomAction = new AlignmentAction(workbenchPart, PositionConstants.BOTTOM);
        registry.registerAction(posBottomAction);
        getSelectionActions().add(posBottomAction.getId());

        registry.registerAction(new QuickOutlineAction());
        registry.registerAction(new ToggleModelAction());
        registry.registerAction(new ToggleNotationAction());
    }

    @Override
    protected void configureGraphicalViewer() {
        logger.info("Call configureGraphicalViewer");
        super.configureGraphicalViewer();

        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setEditPartFactory(new ERDEditPartFactory());

        // zoom
        ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
        viewer.setRootEditPart(rootEditPart);

        ZoomManager manager = rootEditPart.getZoomManager();
        manager.addZoomListener(zoom -> getCommandStack().execute(new Command("change in zoom") {
            @Override
            public void execute() {
                if (viewer.getContents() != null) {
                    RootModel model = (RootModel) viewer.getContents().getModel();
                    model.setZoom(zoom);
                }
            }

            @Override
            public void undo() {
            }
        }));

        ActionRegistry registry = getActionRegistry();
        registry.registerAction(new ZoomInAction(manager));
        registry.registerAction(new ZoomOutAction(manager));

        // 可能なスケール(拡大縮小率)のリスト
        double[] zoomLevels = { 0.1, 0.15, 0.25, 0.35, 0.5, 0.65, 0.75, 0.85, 1.0, 1.25, 1.5, 1.75 };
        manager.setZoomLevels(zoomLevels);
        List<String> zoomContributions = Arrays.asList(ZoomManager.FIT_ALL, ZoomManager.FIT_HEIGHT,
                ZoomManager.FIT_WIDTH);
        manager.setZoomLevelContributions(zoomContributions);

        // マウスホイールによるズームハンドラー
        viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL), MouseWheelZoomHandler.SINGLETON);

        // ショートカットキー登録
        KeyHandler handler = new GraphicalViewerKeyHandler(viewer);
        viewer.setKeyHandler(handler);

        //
        handler.put(KeyStroke.getPressed(SWT.F2, 0),
                registry.getAction(GEFActionConstants.DIRECT_EDIT));

        // Cut:Ctrl+O
        handler.put(KeyStroke.getPressed((char) 15, 'o', SWT.CTRL),
                registry.getAction(IERDAction.QUICK_OUTLINE));

        // Cut:Ctrl+D
        handler.put(KeyStroke.getPressed((char) 4, 'd', SWT.CTRL),
                registry.getAction(IERDAction.TOGGLE_MODEL));

        // Cut:Ctrl+F
        handler.put(KeyStroke.getPressed((char) 6, 'f', SWT.CTRL),
                registry.getAction(IERDAction.TOGGLE_NOTATION));

        // Zoom:+
        handler.put(KeyStroke.getPressed('+', SWT.KEYPAD_ADD, 0),
                registry.getAction(GEFActionConstants.ZOOM_IN));
        // Zoom:-
        handler.put(KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT, 0),
                registry.getAction(GEFActionConstants.ZOOM_OUT));
    }

    @Override
    protected void initializeGraphicalViewer() {
        logger.info("Call initializeGraphicalViewer");
        GraphicalViewer viewer = getGraphicalViewer();

        ContextMenuProvider provider = new ERDContextMenuProvider(viewer, getActionRegistry());
        viewer.setContextMenu(provider);

        viewer.addSelectionChangedListener(event -> {
            DeleteAction deleteAction = (DeleteAction) getActionRegistry().getAction(ActionFactory.DELETE.getId());
            deleteAction.update();
        });

        viewer.getControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent event) {
                IStructuredSelection selection = (IStructuredSelection) getGraphicalViewer().getSelection();
                Object obj = selection.getFirstElement();
                if (obj != null && obj instanceof IDoubleClickSupport) {
                    ((IDoubleClickSupport) obj).doubleClicked();
                }
            }
        });

        // desirialize
        ERDiagramSerializer es = new ERDiagramSerializer();
        RootModel root = null;
        try {
            IFile file = ((IFileEditorInput) getEditorInput()).getFile();
            root = es.read(file.getContents());

            ZoomManager zoomManager = ((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager();
            zoomManager.setZoom(root.getZoom());

        } catch (Exception e) {
            ERDPlugin.logException(e);
            throw new SystemException(e);
        }
        viewer.setContents(root);
        applyPreferences();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        logger.info("Call doSave : {}", monitor);
        RootModel model = (RootModel) getGraphicalViewer().getContents().getModel();

        IFile file = ((IFileEditorInput) getEditorInput()).getFile();

        // Validate models
        if (ERDPlugin.getDefault().getPreferenceStore().getBoolean(ERDPreferenceKey.VALIDATE_ON_SAVE)) {
            try {
                file.deleteMarkers(IMarker.PROBLEM, false, 0);
                DiagramErrorManager deManager = new DiagramValidator(model).doValidate();
                for (DiagramError error : deManager.getErrors()) {
                    error.addMarker(file);
                }
            } catch (CoreException e) {
                ERDPlugin.logException(e);
            }
        }

        try {
            needViewerRefreshFlag = false;
            ERDiagramSerializer es = new ERDiagramSerializer();
            file.setContents(es.write(model), true, true, monitor);
        } catch (Exception e) {
            ERDPlugin.logException(e);
            throw new RuntimeException(e);
        }
        getCommandStack().markSaveLocation();
    }

    @Override
    public void doSaveAs() {
        logger.info("Call doSaveAs");
        doSave(new NullProgressMonitor());
    }

    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    @Override
    public void commandStackChanged(EventObject event) {
        logger.info("Call commandStackChanged : {}", event);
        if (isDirty()) {
            if (!savePreviouslyNeeded) {
                savePreviouslyNeeded = true;
                firePropertyChange(IEditorPart.PROP_DIRTY);
            }
        } else {
            savePreviouslyNeeded = false;
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
        super.commandStackChanged(event);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object getAdapter(Class adapter) {
        logger.info("Call getAdapter : {}", adapter);
        GraphicalViewer viewer = getGraphicalViewer();
        if (adapter == IContentOutlinePage.class) {
            RootModel model = (RootModel) getGraphicalViewer().getContents().getModel();
            return new ERDiagramOutlinePage(viewer, getEditDomain(), model, getSelectionSynchronizer());
        } else if (adapter == ZoomManager.class) {
            return ((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager();
        } else if (adapter == IGotoMarker.class) {
            return this;
        } else {
            return super.getAdapter(adapter);
        }
    }

    @Override
    public void resourceChanged(IResourceChangeEvent event) {
        logger.info("Call resourceChanged : {}", event);
        if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
            IEditorInput input = getEditorInput();

            // Do not refresh for changes of markers.
            IMarkerDelta[] deltas = event.findMarkerDeltas(IMarker.PROBLEM, true);
            if (deltas.length > 0) {
                return;
            }

            if (input instanceof IFileEditorInput) {
                Display.getDefault().asyncExec(() -> {
                    IFile file = ((IFileEditorInput) input).getFile();
                    if (!file.exists()) {
                        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                        page.closeEditor(ERDiagramEditor.this, false);
                    } else {
                        if (!getPartName().equals(file.getName())) {
                            setPartName(file.getName());
                        }
                        if (needViewerRefreshFlag) {
                            refreshGraphicalViewer();
                        } else {
                            needViewerRefreshFlag = true;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("Call propertyChange : {}", event);
        applyPreferences();
        RootEditPart root = (RootEditPart) getGraphicalViewer().getRootEditPart().getContents();
        root.propertyChange(new java.beans.PropertyChangeEvent(this, RootModel.P_MODE, null, null));
    }

    @Override
    public void dispose() {
        logger.info("Call dispose");
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        ERDPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
        super.dispose();
    }

    /**
     * Creates <code>PaletteEntry</code> for the connection.
     *
     * @param itemName the display name
     * @param clazz the model class
     * @param icon the icon path
     * @return created <code>PaletteEntry</code>
     */
    private PaletteEntry createConnectionEntry(String itemName, Class<?> clazz, String icon) {
        ImageDescriptor image = null;
        if (icon != null) {
            image = ERDPlugin.getImageDescriptor(icon);
        }
        ConnectionCreationToolEntry entry = new ConnectionCreationToolEntry(itemName, itemName,
                new SimpleFactory(clazz), image, image);
        // entry.setToolClass(SelectionTool.class);
        return entry;
    }

    /**
     * Creates <code>PaletteEntry</code> for the entity.
     *
     * @param itemName the display name
     * @param clazz the model class
     * @param icon the icon path
     * @return created <code>PaletteEntry</code>
     */
    private PaletteEntry createEntityEntry(String itemName, Class<?> clazz, String icon) {
        ImageDescriptor image = null;
        if (icon != null) {
            image = ERDPlugin.getImageDescriptor(icon);
        }
        CreationToolEntry entry = new CreationToolEntry(itemName, itemName, new SimpleFactory(clazz), image, image);

        return entry;
    }

    private void refreshGraphicalViewer() {
        IEditorInput input = getEditorInput();
        if (input instanceof IFileEditorInput) {
            try {
                IFile file = ((IFileEditorInput) input).getFile();
                GraphicalViewer viewer = getGraphicalViewer();

                // desirialize
                RootModel newRoot = null;
                try {
                    ERDiagramSerializer es = new ERDiagramSerializer();
                    newRoot = es.read(file.getContents());
                } catch (Exception e) {
                    ERDPlugin.logException(e);
                    return;
                }

                // copy to editing model
                RootModel root = (RootModel) viewer.getContents().getModel();
                root.copyFrom(newRoot);

            } catch (Exception e) {
                ERDPlugin.logException(e);
            }
        }
    }

    private void applyPreferences() {
        IPreferenceStore store = ERDPlugin.getDefault().getPreferenceStore();

        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
                Boolean.valueOf(store.getBoolean(ERDPreferenceKey.SHOW_GRID)));

        int gridSize = store.getInt(ERDPreferenceKey.GRID_SIZE);
        viewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, new Dimension(gridSize, gridSize));

        if (store.getBoolean(ERDPreferenceKey.SHOW_GRID) && store.getBoolean(ERDPreferenceKey.ENABLED_GRID)) {
            viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, Boolean.TRUE);
        } else {
            viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, Boolean.FALSE);
        }

        viewer.setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
                Boolean.valueOf(store.getBoolean(ERDPreferenceKey.SNAP_GEOMETRY)));
    }
}
