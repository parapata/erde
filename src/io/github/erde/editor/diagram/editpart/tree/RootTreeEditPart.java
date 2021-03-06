package io.github.erde.editor.diagram.editpart.tree;

import static io.github.erde.Resource.*;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.erde.core.util.StringUtils;
import io.github.erde.editor.ERDiagramOutlinePage;
import io.github.erde.editor.action.DomainEditAction;
import io.github.erde.editor.diagram.editpart.tree.FolderTreeEditPart.FolderModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;

/**
 * RootTreeEditPart.
 *
 * @author modified by parapata
 */
public class RootTreeEditPart extends DBTreeEditPart {

    private Logger logger = LoggerFactory.getLogger(RootTreeEditPart.class);

    @Override
    protected List<FolderModel> getModelChildren() {
        List<FolderModel> children = new ArrayList<>();
        children.add(new FolderModel(LABEL_TABLE.getValue(), (RootModel) getModel()) {
            @Override
            public List<TableModel> getChildren() {
                String filterText = ERDiagramOutlinePage.getFilterText();
                if (StringUtils.isEmpty(filterText)) {
                    return root.getTables();
                }
                // filtering
                List<TableModel> result = new ArrayList<>();
                for (TableModel table : root.getTables()) {

                    String physicalName = StringUtils.upperCase(table.getPhysicalName());
                    String logicalName = StringUtils.upperCase(table.getLogicalName());

                    if (StringUtils.startsWithIgnoreCase(physicalName, filterText)) {
                        result.add(table);
                    } else if (StringUtils.startsWithIgnoreCase(logicalName, filterText)) {
                        result.add(table);
                    }
                }
                return result;
            }

            @Override
            public void doEdit() {
                // Nothing to do
            }
        });

        if (StringUtils.isEmpty(ERDiagramOutlinePage.getFilterText())) {
            children.add(new FolderModel(LABEL_DOMAIN.getValue(), (RootModel) getModel()) {
                @Override
                public List<DomainModel> getChildren() {
                    String filterText = ERDiagramOutlinePage.getFilterText();
                    if (StringUtils.isEmpty(filterText)) {
                        return root.getDomains();
                    }
                    // filtering
                    List<DomainModel> result = new ArrayList<>();
                    for (DomainModel domain : root.getDomains()) {
                        String domainName = StringUtils.upperCase(domain.getDomainName());
                        if (StringUtils.startsWithIgnoreCase(domainName, filterText)) {
                            result.add(domain);
                        }
                    }
                    return result;
                }

                @Override
                public void doEdit() {
                    new DomainEditAction().run();
                }
            });
        }
        return children;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        logger.info("更新処理イベント発生(TreeView) : {}", event.getPropertyName());
        String propName = event.getPropertyName();

        if (RootModel.P_CHILDREN.equals(propName)) {
            refreshChildren();

        } else if (RootModel.P_DOMAINS.equals(propName)) {
            children.forEach(child -> {
                FolderTreeEditPart editPart = (FolderTreeEditPart) child;
                if (editPart.isDomain()) {
                    editPart.refresh();
                    ((TreeItem) ((AbstractTreeEditPart) editPart).getWidget()).setExpanded(true);
                    editPart.setFocus(true);
                }
            });
        }
    }
}
