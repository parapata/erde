package io.github.erde.editor.diagram.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.erde.IMessages;
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
public class RootTreeEditPart extends DBTreeEditPart implements IMessages {

    @Override
    protected List<FolderModel> getModelChildren() {
        List<FolderModel> children = new ArrayList<>();
        children.add(new FolderModel(getResource("label.table"), (RootModel) getModel()) {
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
            children.add(new FolderModel(getResource("label.domain"), (RootModel) getModel()) {
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
    public void propertyChange(PropertyChangeEvent event) {
        String propName = event.getPropertyName();
        if (RootModel.P_CHILDREN.equals(propName) || RootModel.P_DOMAINS.equals(propName)) {
            refreshChildren();
        }
    }
}
