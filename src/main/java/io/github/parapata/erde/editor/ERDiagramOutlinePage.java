package io.github.parapata.erde.editor;

import java.util.List;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.diagram.editpart.tree.DBTreeEditPart;
import io.github.parapata.erde.editor.diagram.editpart.tree.DBTreeEditPartFactory;
import io.github.parapata.erde.editor.diagram.editpart.tree.FolderTreeEditPart;
import io.github.parapata.erde.editor.diagram.model.IModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;

/**
 * The outline page of the {@link ERDiagramEditor}.
 *
 * @author modified by parapata
 */
public class ERDiagramOutlinePage extends ContentOutlinePage {

    private static String filterText;

    private GraphicalViewer graphicalViewer;
    private EditDomain editDomain;
    private RootModel rootModel;
    private SelectionSynchronizer selectionSynchronizer;

    private Composite composite;
    private SashForm sashForm;
    private DisposeListener disposeListener;
    private ScrollableThumbnail thumbnail;
    private Text search;

    private ModelEditor modelEditor;

    public ERDiagramOutlinePage(GraphicalViewer viewer, EditDomain domain, RootModel root,
            SelectionSynchronizer selectionSynchronizer) {
        super(new TreeViewer());
        this.graphicalViewer = viewer;
        this.editDomain = domain;
        this.rootModel = root;
        this.selectionSynchronizer = selectionSynchronizer;
        this.modelEditor = new ModelEditor(graphicalViewer, true);
    }

    /**
     * Returns the incremental search text.
     *
     * @return the incremental search text
     */
    public static String getFilterText() {
        return filterText;
    }

    public static void setFilterText(String filterText) {
        ERDiagramOutlinePage.filterText = StringUtils.defaultString(filterText);
    }

    @Override
    public void createControl(Composite parent) {
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        search = new Text(composite, SWT.BORDER);
        search.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        search.addModifyListener(listener -> {
            filterText = search.getText();
            getViewer().getRootEditPart().getContents().refresh();
            if (filterText.length() > 0) {
                EditPart folder = (EditPart) getViewer().getRootEditPart().getContents().getChildren().get(0);

                List<?> tables = folder.getChildren();

                if (!tables.isEmpty()) {
                    getViewer().select((EditPart) tables.get(0));
                }
            }
        });

        sashForm = new SashForm(composite, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

        EditPartViewer viewer = getViewer();
        viewer.createControl(sashForm);
        viewer.setEditDomain(editDomain);
        viewer.setEditPartFactory(new DBTreeEditPartFactory());
        viewer.setContents(rootModel);
        selectionSynchronizer.addViewer(viewer);
        viewer.getControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent event) {
                IStructuredSelection sel = (IStructuredSelection) getViewer().getSelection();
                Object obj = sel.getFirstElement();
                if (obj != null) {
                    if (obj instanceof FolderTreeEditPart) {
                        //
                    } else if (obj instanceof DBTreeEditPart) {
                        DBTreeEditPart editPart = (DBTreeEditPart) obj;
                        IModel model = (IModel) editPart.getModel();
                        modelEditor.editModel(model);
                    }
                }
            }
        });

        Canvas canvas = new Canvas(sashForm, SWT.BORDER);

        LightweightSystem lws = new LightweightSystem(canvas);

        ScalableRootEditPart rootEditPart = (ScalableRootEditPart) graphicalViewer.getRootEditPart();
        thumbnail = new ScrollableThumbnail((Viewport) (rootEditPart).getFigure());
        thumbnail.setSource(rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS));
        lws.setContents(thumbnail);

        disposeListener = event -> {
            if (thumbnail != null) {
                thumbnail.deactivate();
                thumbnail = null;
            }
        };
        graphicalViewer.getControl().addDisposeListener(disposeListener);
        getSite().setSelectionProvider(getViewer());
    }

    @Override
    public Control getControl() {
        return composite;
    }

    @Override
    public void dispose() {
        selectionSynchronizer.removeViewer(getViewer());
        if ((graphicalViewer.getControl() != null) && !graphicalViewer.getControl().isDisposed()) {
            graphicalViewer.getControl().removeDisposeListener(disposeListener);
        }
        super.dispose();
    }

}
