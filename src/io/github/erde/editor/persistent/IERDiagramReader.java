package io.github.erde.editor.persistent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXB;

import org.eclipse.draw2d.geometry.Rectangle;

import io.github.erde.Activator;
import io.github.erde.dialect.DialectProvider;
import io.github.erde.dialect.IDialect;
import io.github.erde.dialect.type.IColumnType;
import io.github.erde.dialect.type.IndexType;
import io.github.erde.editor.diagram.model.BaseEntityModel;
import io.github.erde.editor.diagram.model.ColumnModel;
import io.github.erde.editor.diagram.model.DomainModel;
import io.github.erde.editor.diagram.model.IndexModel;
import io.github.erde.editor.diagram.model.NoteConnectionModel;
import io.github.erde.editor.diagram.model.NoteModel;
import io.github.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.erde.editor.diagram.model.RelationshipModel;
import io.github.erde.editor.diagram.model.RootModel;
import io.github.erde.editor.diagram.model.TableModel;
import io.github.erde.editor.persistent.diagram.ColumnXmlModel;
import io.github.erde.editor.persistent.diagram.DbSettingsXmlModel;
import io.github.erde.editor.persistent.diagram.DomainXmlModel;
import io.github.erde.editor.persistent.diagram.ErdeXmlModel;
import io.github.erde.editor.persistent.diagram.IndexXmlModel;
import io.github.erde.editor.persistent.diagram.LocationXmlModel;

/**
 * IERDiagramReader.
 *
 * @author modified by parapata
 */
public interface IERDiagramReader {

    default RootModel toRootModel(InputStream is) {

        ErdeXmlModel erde = JAXB.unmarshal(is, ErdeXmlModel.class);

        RootModel rootModel = new RootModel();
        rootModel.setDialectProvider(DialectProvider.valueOf(erde.getDialectName()));
        rootModel.setSchemaName(erde.getSchemaName());
        rootModel.setLowercase(erde.isLowerCase());
        rootModel.setLogicalMode(erde.isLogicalMode());
        rootModel.setIncludeView(erde.isIncludeView());
        rootModel.setNotation(erde.getNotation());
        rootModel.setZoom(erde.getZoom() == 0D ? 1.0D : erde.getZoom());

        DbSettingsXmlModel dbSettings = erde.getDbSettings();

        rootModel.setJarFile(dbSettings.getJarFile());
        rootModel.setJdbcDriver(dbSettings.getJdbcDriver());
        rootModel.setJdbcUrl(dbSettings.getJdbcUrl());
        rootModel.setJdbcCatalog(dbSettings.getJdbcCatalog());
        rootModel.setJdbcSchema(dbSettings.getJdbcSchema());
        rootModel.setJdbcUser(dbSettings.getJdbcUser());
        rootModel.setJdbcPassword(dbSettings.getJdbcUser());

        addTables(erde, rootModel.getChildren());
        addNotes(erde, rootModel.getChildren());

        // ForeignKeyコネクションの追加
        erde.getDiagram().getTables()
                .stream()
                .filter(predicate -> !predicate.getForeignKeies().isEmpty())
                .forEach(table -> {
                    table.getForeignKeies().forEach(foreignKey -> {
                        RelationshipModel foreignKeyModel = new RelationshipModel();
                        foreignKeyModel.setForeignKeyName(foreignKey.getForeignKeyName());
                        if (foreignKey.getOnUpdateOption() != null) {
                            foreignKeyModel.setOnUpdateOption(foreignKey.getOnUpdateOption().name());
                        }
                        if (foreignKey.getOnDeleteOption() != null) {
                            foreignKeyModel.setOnDeleteOption(foreignKey.getOnDeleteOption().name());
                        }
                        foreignKeyModel.setSourceCardinality(foreignKey.getSourceCardinality());
                        foreignKeyModel.setTargetCardinality(foreignKey.getTargetCardinality());

                        TableModel sourceTableModel = getTableModel(rootModel.getTables(), foreignKey.getSourceId());
                        foreignKeyModel.setSource(sourceTableModel);
                        sourceTableModel.getModelSourceConnections().add(foreignKeyModel);

                        TableModel targetTableModel = getTableModel(rootModel.getTables(), table.getId());
                        foreignKeyModel.setTarget(targetTableModel);
                        targetTableModel.getModelTargetConnections().add(foreignKeyModel);

                        List<RelationshipMappingModel> mappings = new ArrayList<>();
                        foreignKey.getForeignKeyMappings().forEach(mapping -> {
                            RelationshipMappingModel mappingModel = new RelationshipMappingModel();

                            ColumnModel sourceColumnModel = getColumnModel(sourceTableModel.getColumns(),
                                    mapping.getReferenceName());
                            ColumnModel targetColumnModel = getColumnModel(targetTableModel.getColumns(),
                                    mapping.getTargetName());
                            mappingModel.setReferenceKey(sourceColumnModel);
                            mappingModel.setForeignKey(targetColumnModel);
                            mappings.add(mappingModel);
                        });

                        foreignKeyModel.setMappings(mappings);
                    });
                });

        // Noteコネクションの追加
        erde.getDiagram().getNotes().stream().filter(predicate -> predicate.getNoteConnections().size() > 0)
                .forEach(note -> {
                    String sourceId = note.getId();
                    note.getNoteConnections().forEach(target -> {
                        addNoteConnection(sourceId, target.getTargetId(), rootModel.getChildren());
                    });
                });

        addDomains(rootModel.getDialectProvider(), erde, rootModel.getDomains());

        return rootModel;
    }

    private Rectangle toRectangle(LocationXmlModel location) {
        Rectangle result = new Rectangle();
        result.height = location.getHeight();
        result.width = location.getWidth();
        result.x = location.getX();
        result.y = location.getY();
        return result;
    }

    private void addTables(ErdeXmlModel erde, List<BaseEntityModel> children) {

        erde.getDiagram().getTables().forEach(table -> {
            TableModel model = new TableModel(table.getId());
            model.setPhysicalName(table.getPhysicalName());
            model.setLogicalName(table.getLogicalName());
            model.setDescription(table.getDescription());

            LocationXmlModel location = table.getLocation();
            model.setConstraint(toRectangle(location));

            addColumns(DialectProvider.valueOf(erde.getDialectName()), model, erde.getDialectName(), table.getColumns(),
                    erde.getDomains());
            addIndices(model, table.getIndices());

            children.add(model);
        });
    }

    private void addColumns(DialectProvider dialectProvider, TableModel tableModel, String dialectName,
            List<ColumnXmlModel> columns, List<DomainXmlModel> domains) {

        IDialect dialect = Activator.getDefault().getContributedDialects().get(dialectName);
        columns.forEach(column -> {
            ColumnModel model = new ColumnModel();
            model.setPhysicalName(column.getPhysicalName());
            model.setLogicalName(column.getLogicalName());

            if (column.getDomainId() == null) {
                model.setColumnType(dialect.getColumnType(column.getType()));
                model.setColumnSize(column.getColumnSize());
                model.setDecimal(column.getDecimal());
                if (column.isUnsigned() != null) {
                    model.setUnsigned(column.isUnsigned());
                }
            } else {
                DomainXmlModel domain = domains.stream()
                        .filter(predicate -> column.getDomainId().equals(predicate.getId()))
                        .findFirst()
                        .get();
                IColumnType columnType = dialect.getColumnType(domain.getType());
                boolean unsigned = (domain.isUnsigned() != null && domain.isUnsigned());
                DomainModel domainModel = DomainModel.newInstance(
                        dialectProvider,
                        domain.getId(),
                        domain.getDomainName(),
                        columnType,
                        domain.getColumnSize(),
                        domain.getDecimal(),
                        unsigned);
                model.setColumnType(domainModel);
            }
            model.setEnumNames(new LinkedHashSet<String>(column.getEnumNames()));
            model.setDescription(column.getDescription());
            if (column.isNotNull() != null) {
                model.setNotNull(column.isNotNull());
            }
            if (column.isPrimaryKey() != null) {
                model.setPrimaryKey(column.isPrimaryKey());
            }
            if (column.isUniqueKey() != null) {
                model.setUniqueKey(column.isUniqueKey());
            }
            if (column.isAutoIncrement() != null) {
                model.setAutoIncrement(column.isAutoIncrement());
            }
            model.setDefaultValue(column.getDefaultValue());
            tableModel.getColumns().add(model);
        });
    }

    private void addIndices(TableModel tableModel, List<IndexXmlModel> indices) {
        indices.forEach(index -> {
            IndexModel model = new IndexModel();
            model.setIndexName(index.getIndexName());
            model.setIndexType(IndexType.toIndexType(index.getIndexType()));
            index.getColumnNames().forEach(column -> {
                model.getColumns().add(column);
            });
            tableModel.getIndices().add(model);
        });
    }

    private void addNotes(ErdeXmlModel erde, List<BaseEntityModel> children) {
        erde.getDiagram().getNotes().forEach(note -> {
            NoteModel model = new NoteModel(note.getId());
            model.setContent(note.getText());

            LocationXmlModel location = note.getLocation();
            model.setConstraint(toRectangle(location));
            children.add(model);
        });
    }

    private void addNoteConnection(String sourceId, String targetId, List<BaseEntityModel> list) {

        Optional<BaseEntityModel> source = getModel(sourceId, list);
        Optional<BaseEntityModel> target = getModel(targetId, list);

        NoteConnectionModel anchorModel = new NoteConnectionModel();
        anchorModel.setSource(source.get());
        anchorModel.attachSource();
        anchorModel.setTarget(target.get());
        anchorModel.attachTarget();
    }

    private Optional<BaseEntityModel> getModel(String id, List<BaseEntityModel> list) {
        Optional<BaseEntityModel> result = list.stream()
                .filter(predicate -> id.equals(predicate.getId()))
                .findFirst();
        return result;
    }

    private TableModel getTableModel(List<TableModel> tables, String id) {
        return tables.stream()
                .filter(predicate -> predicate.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private ColumnModel getColumnModel(List<ColumnModel> columns, String columnName) {
        return columns.stream()
                .filter(predicate -> predicate.getPhysicalName().equals(columnName))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private void addDomains(DialectProvider dialectProvider, ErdeXmlModel erde, List<DomainModel> domains) {

        String dialectName = erde.getDialectName();
        IDialect dialect = Activator.getDefault().getContributedDialects().get(dialectName);

        erde.getDomains().forEach(domain -> {
            DomainModel model = new DomainModel(dialectProvider);
            model.setId(domain.getId());
            model.setDomainName(domain.getDomainName());
            model.setColumnType(dialect.getColumnType(domain.getType()));
            model.setColumnSize(domain.getColumnSize());
            model.setDecimal(domain.getDecimal());
            if (domain.isUnsigned() != null && domain.isUnsigned()) {
                model.setUnsigned(true);
            }
            domains.add(model);
        });
    }
}
