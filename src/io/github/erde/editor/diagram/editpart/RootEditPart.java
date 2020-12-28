package io.github.erde.editor.diagram.editpart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.rulers.RulerProvider;

import io.github.erde.editor.diagram.editpart.editpolicy.RootEditPolicy;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.RootModel;

/**
 * RootEditPart.
 *
 * @author modified by parapata
 */
public class RootEditPart extends AbstractERDEditPart {

    @Override
    protected IFigure createFigure() {
        Layer figure = new Layer();
        figure.setLayoutManager(new XYLayout());
        return figure;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new RootEditPolicy());
    }

    @Override
    protected List<BaseEntityModel> getModelChildren() {
        return ((RootModel) getModel()).getChildren();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        if (RootModel.P_CHILDREN.equals(event.getPropertyName())) {
            refreshChildren();
        } else if (RootModel.P_FONT.equals(event.getPropertyName())) {
            List<AbstractERDEntityEditPart> children = getChildren();
            for (AbstractERDEntityEditPart part : children) {
                part.refresh();
                for (Object conn : part.getSourceConnections()) {
                    ((AbstractERDConnectionEditPart) conn).refresh();
                }
            }
        } else if (RootModel.P_MODE.equals(event.getPropertyName())) {
            List<AbstractERDEntityEditPart> children = getChildren();
            for (AbstractERDEntityEditPart part : children) {
                part.refresh();
                List<AbstractERDConnectionEditPart> conns = part.getSourceConnections();
                for (AbstractERDConnectionEditPart conn : conns) {
                    conn.refresh();
                }
            }
        } else if (RootModel.P_NOTATION.equals(event.getPropertyName())) {
            List<AbstractERDEntityEditPart> children = getChildren();
            for (AbstractERDEntityEditPart part : children) {
                List<AbstractERDConnectionEditPart> conns = part.getSourceConnections();
                for (AbstractERDConnectionEditPart conn : conns) {
                    conn.refresh();
                }
            }

        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object getAdapter(Class adapter) {
        if (adapter == SnapToHelper.class) {
            List<SnapToHelper> snapStrategies = new ArrayList<>();
            Boolean val = (Boolean) getViewer().getProperty(RulerProvider.PROPERTY_RULER_VISIBILITY);
            if (val != null && val.booleanValue()) {
                snapStrategies.add(new SnapToGuides(this));
            }
            val = (Boolean) getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
            if (val != null && val.booleanValue()) {
                snapStrategies.add(new SnapToGeometry(this));
            }
            val = (Boolean) getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
            if (val != null && val.booleanValue()) {
                snapStrategies.add(new SnapToGrid(this));
            }

            if (snapStrategies.size() == 0) {
                return null;
            }
            if (snapStrategies.size() == 1) {
                return snapStrategies.get(0);
            }

            SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
            for (int i = 0; i < snapStrategies.size(); i++) {
                ss[i] = snapStrategies.get(i);
            }
            return new CompoundSnapToHelper(ss);
        }
        return super.getAdapter(adapter);
    }
}
