package io.github.erde.editor.action;

import static io.github.erde.Resource.*;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.action.Action;

import io.github.erde.editor.diagram.editpart.AbstractERDEntityEditPart;
import io.github.erde.editor.diagram.editpart.command.LayoutCommand;
import io.github.erde.editor.diagram.model.BaseConnectionModel;
import io.github.erde.editor.diagram.model.BaseEntityModel;

/**
 * The action to layout all entities automatically.
 *
 * @author modified by parapata
 */
public class AutoLayoutAction extends Action implements IERDAction {

    public AutoLayoutAction() {
        super();
        setId(AUTO_LAYOUT);
        setText(ACTION_AUTO_LAYOUT.getValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {

        GraphicalViewer viewer = getGraphicalViewer();

        CompoundCommand commands = new CompoundCommand();
        List<Object> models = viewer.getContents().getChildren();
        NodeList graphNodes = new NodeList();
        EdgeList graphEdges = new EdgeList();
        // assemble nodes
        for (Object obj : models) {
            if (obj instanceof AbstractERDEntityEditPart) {
                AbstractERDEntityEditPart editPart = (AbstractERDEntityEditPart) obj;
                BaseEntityModel model = (BaseEntityModel) editPart.getModel();
                EntityNode node = new EntityNode();
                node.model = model;
                node.width = editPart.getFigure().getSize().width;
                node.height = editPart.getFigure().getSize().height;
                graphNodes.add(node);
            }
        }
        // assemble edges
        for (Object obj : graphNodes) {
            EntityNode node = (EntityNode) obj;
            List<BaseConnectionModel> conns = node.model.getModelSourceConnections();
            CONN_LOOP: for (BaseConnectionModel conn : conns) {
                if (conn.getSource() == conn.getTarget()) {
                    continue;
                }
                // skip if the connection already added
                for (Object graphEdge : graphEdges) {
                    ConnectionEdge edge = (ConnectionEdge) graphEdge;
                    if (edge.model == conn) {
                        continue CONN_LOOP;
                    }
                }
                EntityNode source = getNode(graphNodes, conn.getSource());
                EntityNode target = getNode(graphNodes, conn.getTarget());
                if (source != null && target != null) {
                    ConnectionEdge edge = new ConnectionEdge(source, target);
                    edge.model = conn;
                    graphEdges.add(edge);
                }
            }
        }

        DirectedGraph graph = new DirectedGraph();
        graph.setDefaultPadding(new Insets(40));
        graph.nodes = graphNodes;
        graph.edges = graphEdges;
        new DirectedGraphLayout().visit(graph);
        for (int i = 0; i < graph.nodes.size(); i++) {
            EntityNode node = (EntityNode) graph.nodes.getNode(i);
            commands.add(new LayoutCommand(node.model, node.x, node.y));
        }

        viewer.getEditDomain().getCommandStack().execute(commands);
    }

    private EntityNode getNode(NodeList list, BaseEntityModel model) {
        for (Object element : list) {
            EntityNode node = (EntityNode) element;
            if (node.model == model) {
                return node;
            }
        }
        return null;
    }

    private class EntityNode extends Node {
        private BaseEntityModel model;
    }

    private class ConnectionEdge extends Edge {
        private BaseConnectionModel model;

        public ConnectionEdge(EntityNode source, EntityNode target) {
            super(source, target);
        }
    }
}
