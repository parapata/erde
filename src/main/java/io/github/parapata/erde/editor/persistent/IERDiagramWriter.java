package io.github.parapata.erde.editor.persistent;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.diagram.model.BaseEntityModel;
import io.github.parapata.erde.editor.diagram.model.DomainModel;
import io.github.parapata.erde.editor.diagram.model.NoteConnectionModel;
import io.github.parapata.erde.editor.diagram.model.NoteModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.RootModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.persistent.diagram.ColumnXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.DiagramXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.DomainXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.DomainsXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.ErdeXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.ForeignKeyMappingXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.ForeignKeyXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.IndexXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.LocationXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.NoteConnectionXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.NoteXmlModel;
import io.github.parapata.erde.editor.persistent.diagram.ObjectFactory;
import io.github.parapata.erde.editor.persistent.diagram.ReferenceOption;
import io.github.parapata.erde.editor.persistent.diagram.TableXmlModel;

/**
 * IERDiagramWriter.
 *
 * @author modified by parapata
 */
public interface IERDiagramWriter {

    default ErdeXmlModel toErde(RootModel rootModel) {
        ErdeXmlModel result = new ErdeXmlModel();

        result.setDialectName(rootModel.getDialectProvider().name());
        if (StringUtils.isNotEmpty(rootModel.getSchemaName())) {
            result.setSchemaName(rootModel.getSchemaName());
        }
        result.setLowerCase(rootModel.isLowercase());
        result.setLogicalMode(rootModel.isLogicalMode());
        result.setIncludeView(rootModel.isIncludeView());
        result.setNotation(rootModel.getNotation());
        if (rootModel.getZoom() != 1.0D) {
            result.setZoom(rootModel.getZoom());
        }

        ObjectFactory factory = new ObjectFactory();

        DiagramXmlModel diagram = toDiagram(factory, rootModel.getChildren());
        result.setDiagram(diagram);
        if (!rootModel.getDomains().isEmpty()) {
            result.setDomains(toDomainsXmlMode(factory, rootModel.getDomains()));
        }
        return result;
    }

    private DiagramXmlModel toDiagram(ObjectFactory factory, List<BaseEntityModel> childs) {

        DiagramXmlModel diagram = new DiagramXmlModel();
        childs.forEach(model -> {
            if (model instanceof TableModel) {
                TableXmlModel table = toTableXmlModel(factory, (TableModel) model);
                diagram.getTables().add(table);

            } else if (model instanceof NoteModel) {
                NoteXmlModel note = toNoteXmlModel(factory, (NoteModel) model);
                diagram.getNotes().add(note);
            }
        });

        return diagram;
    }

    private LocationXmlModel toLocationXmlModel(ObjectFactory factory, Rectangle rectangle) {

        LocationXmlModel location = factory.createLocationXmlModel();
        location.setHeight(rectangle.height);
        location.setWidth(rectangle.width);
        location.setX(rectangle.x);
        location.setY(rectangle.y);

        return location;
    }

    private TableXmlModel toTableXmlModel(ObjectFactory factory, TableModel tableModel) {

        TableXmlModel table = factory.createTableXmlModel();
        table.setId(tableModel.getId());
        table.setLocation(toLocationXmlModel(factory, tableModel.getConstraint()));
        table.setPhysicalName(tableModel.getPhysicalName());
        if (StringUtils.isNotEmpty(tableModel.getLogicalName())) {
            table.setLogicalName(tableModel.getLogicalName());
        }
        if (StringUtils.isNotEmpty(tableModel.getDescription())) {
            table.setDescription(tableModel.getDescription());
        }

        // Column
        tableModel.getColumns().forEach(columnModel -> {
            ColumnXmlModel column = factory.createColumnXmlModel();
            column.setPhysicalName(columnModel.getPhysicalName());
            if (StringUtils.isNotEmpty(columnModel.getLogicalName())) {
                column.setLogicalName(columnModel.getLogicalName());
            }
            if (StringUtils.isNotEmpty(columnModel.getDescription())) {
                column.setDescription(columnModel.getDescription());
            }
            if (columnModel.getColumnType() instanceof DomainModel) {
                DomainModel domain = (DomainModel) columnModel.getColumnType();
                column.setDomainId(domain.getId());
            } else {
                column.setType(columnModel.getColumnType().getPhysicalName());
                if (columnModel.getColumnType().isSizeSupported()) {
                    if (columnModel.getColumnSize() != null) {
                        column.setColumnSize(columnModel.getColumnSize());
                    }
                    if (columnModel.getDecimal() != null) {
                        column.setDecimal(columnModel.getDecimal());
                    }
                }
                if (columnModel.getColumnType().isUnsignedSupported()) {
                    column.setUnsigned(columnModel.isUnsigned());
                }
            }
            columnModel.getEnumNames().forEach(enumName -> {
                column.getEnumNames().add(enumName);
            });
            if (columnModel.isNotNull()) {
                column.setNotNull(columnModel.isNotNull());
            }
            if (columnModel.isPrimaryKey()) {
                column.setPrimaryKey(columnModel.isPrimaryKey());
            }
            if (columnModel.isUniqueKey()) {
                column.setUniqueKey(columnModel.isUniqueKey());
            }
            if (columnModel.isAutoIncrement()) {
                column.setAutoIncrement(columnModel.isAutoIncrement());
            }
            if (StringUtils.isNotEmpty(columnModel.getDefaultValue())) {
                column.setDefaultValue(columnModel.getDefaultValue());
            }
            table.getColumns().add(column);
        });

        // index
        tableModel.getIndices().forEach(indexModel -> {
            IndexXmlModel index = factory.createIndexXmlModel();
            index.setIndexName(indexModel.getIndexName());
            if (indexModel.getIndexType() != null) {
                index.setIndexType(indexModel.getIndexType().getName());
            }

            indexModel.getColumns().forEach(columnName -> {
                index.getColumnNames().add(columnName);
            });
            table.getIndices().add(index);
        });

        // foreignKey
        tableModel.getModelTargetConnections().forEach(connectionModel -> {
            if (connectionModel instanceof RelationshipModel) {

                ForeignKeyXmlModel foreignKey = factory.createForeignKeyXmlModel();
                RelationshipModel foreignKeyModel = (RelationshipModel) connectionModel;

                if (StringUtils.isNotEmpty(foreignKeyModel.getSource().getId())) {
                    foreignKey.setSourceId(foreignKeyModel.getSource().getId());
                }
                if (StringUtils.isNotEmpty(foreignKeyModel.getForeignKeyName())) {
                    foreignKey.setForeignKeyName(foreignKeyModel.getForeignKeyName());
                }
                if (StringUtils.isNotEmpty(foreignKeyModel.getOnUpdateOption())
                        && !ReferenceOption.NO_ACTION.name().equals(foreignKeyModel.getOnUpdateOption())) {
                    foreignKey.setOnUpdateOption(ReferenceOption.fromValue(foreignKeyModel.getOnUpdateOption()));
                }
                if (StringUtils.isNotEmpty(foreignKeyModel.getOnDeleteOption())
                        && !ReferenceOption.NO_ACTION.name().equals(foreignKeyModel.getOnDeleteOption())) {
                    foreignKey.setOnDeleteOption(ReferenceOption.fromValue(foreignKeyModel.getOnDeleteOption()));
                }
                if (StringUtils.isNotEmpty(foreignKeyModel.getSourceCardinality())) {
                    foreignKey.setSourceCardinality(foreignKeyModel.getSourceCardinality());
                }
                if (StringUtils.isNotEmpty(foreignKeyModel.getTargetCardinality())) {
                    foreignKey.setTargetCardinality(foreignKeyModel.getTargetCardinality());
                }

                foreignKeyModel.getMappings().forEach(mapping -> {
                    ForeignKeyMappingXmlModel foreignKeyMappin = new ForeignKeyMappingXmlModel();
                    if (StringUtils.isNotEmpty(mapping.getReferenceKey().getPhysicalName())) {
                        foreignKeyMappin.setReferenceName(mapping.getReferenceKey().getPhysicalName());
                    }
                    if (StringUtils.isNotEmpty(mapping.getForeignKey().getPhysicalName())) {
                        foreignKeyMappin.setTargetName(mapping.getForeignKey().getPhysicalName());
                    }
                    if (StringUtils.isNotEmpty(mapping.getReferenceKey().getPhysicalName())
                            || StringUtils.isNotEmpty(mapping.getForeignKey().getPhysicalName())) {
                        foreignKey.getForeignKeyMappings().add(foreignKeyMappin);
                    }
                });
                table.getForeignKeies().add(foreignKey);
            }
        });

        return table;
    }

    private NoteXmlModel toNoteXmlModel(ObjectFactory factory, NoteModel noteModel) {

        NoteXmlModel note = factory.createNoteXmlModel();
        note.setId(noteModel.getId());
        note.setLocation(toLocationXmlModel(factory, noteModel.getConstraint()));
        note.setText(noteModel.getContent());

        noteModel.getModelSourceConnections().forEach(connectionModel -> {
            if (connectionModel instanceof NoteConnectionModel) {
                // AnchorModel
                if (noteModel == connectionModel.getSource()) {
                    NoteConnectionXmlModel conn = factory.createNoteConnectionXmlModel();
                    BaseEntityModel mainModel = connectionModel.getTarget();
                    conn.setTargetId(mainModel.getId());
                    note.getNoteConnections().add(conn);
                }
            }
        });

        noteModel.getModelTargetConnections().forEach(connection -> {
            if (connection instanceof NoteConnectionModel && !(connection.getTarget() instanceof NoteModel)) {
                // AnchorModel
                if (noteModel == connection.getTarget()) {
                    NoteConnectionXmlModel conn = factory.createNoteConnectionXmlModel();
                    BaseEntityModel mainModel = connection.getSource();
                    conn.setTargetId(mainModel.getId());
                    note.getNoteConnections().add(conn);
                }
            }
        });

        return note;
    }

    private DomainsXmlModel toDomainsXmlMode(ObjectFactory factory, List<DomainModel> fromDomains) {
        DomainsXmlModel result = factory.createDomainsXmlModel();
        fromDomains.forEach(domain -> {
            DomainXmlModel xmlModel = factory.createDomainXmlModel();
            xmlModel.setId(domain.getId());
            xmlModel.setDomainName(domain.getDomainName());
            xmlModel.setType(domain.getPhysicalName());
            xmlModel.setColumnSize(domain.getColumnSize());
            xmlModel.setDecimal(domain.getDecimal());
            if (domain.isUnsigned()) {
                xmlModel.setUnsigned(true);
            }
            result.getDomains().add(xmlModel);
        });
        return result;
    }
}
