package io.github.parapata.erde.editor.dialog.relationship;

import static io.github.parapata.erde.Resource.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.ICON;
import io.github.parapata.erde.core.exception.NotFoundException;
import io.github.parapata.erde.core.util.StringUtils;
import io.github.parapata.erde.editor.diagram.model.ColumnModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipMappingModel;
import io.github.parapata.erde.editor.diagram.model.RelationshipModel;
import io.github.parapata.erde.editor.diagram.model.TableModel;
import io.github.parapata.erde.editor.persistent.diagram.ReferenceOption;

/**
 * RelationshipDialog.
 *
 * @author modified by parapata
 */
public class RelationshipDialog extends Dialog implements IRelationshipDialog {

    private Logger logger = LoggerFactory.getLogger(RelationshipDialog.class);

    private static final String[] REFERENCE_OPTION_ITEMS = {
            ReferenceOption.NO_ACTION.value(),
            ReferenceOption.RESTRICT.value(),
            ReferenceOption.CASCADE.value(),
            ReferenceOption.SET_NULL.value()
    };
    private static final String[] CARDINALITY_SOURCE_ITEMS = { "", "1", "0..1" };
    private static final String[] CARDINALITY_TARGET_ITEMS = { "", "1..n", "0..n", "1", "0..1" };

    private RelationshipModel relationshipModel;

    private Text txtForeignKeyName;
    private Combo cmbReferenceKey;

    private Map<String, String> relationMap;

    private Label message;
    private Combo cmbOnUpdate;
    private Combo cmbOnDelete;
    private Combo cmbSourceCardinality;
    private Combo cmbTargetCardinality;

    private RelationshipHelper helper;

    private boolean logicalMode;

    public RelationshipDialog(Shell shell, RelationshipModel relationshipModel, boolean logicalMode) {
        super(shell);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        this.relationshipModel = relationshipModel;
        this.logicalMode = logicalMode;
        this.helper = new RelationshipHelper(this);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DIALOG_MAPPING_TITLE.getValue());
        newShell.setImage(ERDPlugin.getImage(ICON.TABLE.getPath()));
    }

    @Override
    protected void constrainShellSize() {
        Shell shell = getShell();
        shell.pack();
        shell.setSize(400, shell.getSize().y);
    }

    @Override
    protected Control createContents(Composite parent) {
        Control control = super.createContents(parent);
        getButton(OK).setEnabled(!relationMap.containsValue(""));
        return control;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        // TODO メッセージ
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.horizontalSpan = 3;
        message = new Label(composite, SWT.NONE);
        message.setLayoutData(gd);
        if (false) {
            message.setText(ERROR_DIALOG_MAPPING_NO_COLUMNS.getValue());
        }

        // 制約名
        Label constraintNameLabel = new Label(composite, SWT.NONE);
        constraintNameLabel.setText(LABEL_FOREIGN_KEY_NAME.getValue());
        txtForeignKeyName = new Text(composite, SWT.BORDER);
        txtForeignKeyName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        txtForeignKeyName.setText(relationshipModel.getForeignKeyName());

        // 参照キー＋マッピングエリア設定
        createReferredAndRelationMappingArea(composite);

        // Cardinality
        createCardinalitySettingArea(composite);

        // ReferenceOption
        createReferenceOptionSettingArea(composite);

        return composite;
    }

    @SuppressWarnings("unchecked")
    private void createReferredAndRelationMappingArea(Composite composite) {

        Label referredLabel = new Label(composite, SWT.NONE);
        referredLabel.setText(LABEL_REFERRED.getValue());

        cmbReferenceKey = new Combo(composite, SWT.READ_ONLY);
        cmbReferenceKey.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;

        Group mappingGroup = new Group(composite, SWT.NONE);
        mappingGroup.setLayoutData(gd);
        mappingGroup.setText(LABEL_MAPPING.getValue());
        mappingGroup.setLayout(new GridLayout(3, false));

        TableModel referenceTable = relationshipModel.getSource();

        Map<String, List<String>> referrdMap = helper.createReferrdMap(referenceTable);

        referrdMap.entrySet().forEach(item -> {
            cmbReferenceKey.add(item.getKey());
        });

        String referrdName = helper.getReferrdName(relationshipModel, referrdMap);

        logger.info("referred key : {}", referrdName);
        cmbReferenceKey.setText(referrdName);
        cmbReferenceKey.setData(referrdName);
        createRelationMappingArea(mappingGroup, referrdMap.getOrDefault(referrdName, Collections.EMPTY_LIST));

        // 変更イベント
        cmbReferenceKey.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {

                logger.info("change referenceKey : {}", cmbReferenceKey.getText());

                // 古いコンポーネントを削除
                Arrays.stream(mappingGroup.getChildren()).forEach(control -> control.dispose());
                mappingGroup.setLayout(new GridLayout(3, false));

                createRelationMappingArea(mappingGroup, referrdMap.get(cmbReferenceKey.getText()));

                mappingGroup.layout(); // 再描画
                composite.layout();

                Shell shell = getShell();
                // shell.pack();
                shell.setSize(400, shell.getSize().y);
                shell.redraw();
            }
        });
    }

    private void createRelationMappingArea(Group group, List<String> referrdColumns) {

        // 除外リスト作成
        List<String> exclusions = new ArrayList<>();
        String id = relationshipModel.getSource().getId();
        relationshipModel.getTarget().getModelTargetConnections().forEach(conn -> {
            if (conn instanceof RelationshipModel) {
                RelationshipModel model = ((RelationshipModel) conn);
                if (!id.equals(model.getSource().getId())) {
                    model.getMappings().forEach(relationship -> {
                        exclusions.add(helper.getColumnName(relationship.getForeignKey()));
                    });
                }
            }
        });

        List<String> foreignKeyCandidates = helper.getForeignKeyCandidates(relationshipModel, exclusions);

        // マッピング情報をクリア
        relationMap = new LinkedHashMap<>();

        referrdColumns.forEach(columnName -> {
            Label sourceLabel = new Label(group, SWT.NONE);
            sourceLabel.setText(columnName);

            Label equalLabel = new Label(group, SWT.NONE);
            equalLabel.setText("=");

            Combo cmbTarget = new Combo(group, SWT.READ_ONLY);
            cmbTarget.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            cmbTarget.setData(columnName);

            relationMap.put(columnName, "");

            foreignKeyCandidates.forEach(column -> cmbTarget.add(column));

            //
            relationshipModel.getTarget().getModelTargetConnections().forEach(conn -> {
                if (conn instanceof RelationshipModel) {
                    RelationshipModel model = ((RelationshipModel) conn);
                    if (id.equals(model.getSource().getId())) {
                        model.getMappings().forEach(relationship -> {
                            if (helper.getColumnName(relationship.getReferenceKey()).equals(columnName)) {
                                String foreignKey = helper.getColumnName(relationship.getForeignKey());
                                relationMap.put(columnName, foreignKey);
                                cmbTarget.setText(foreignKey);
                            }
                        });
                    }
                }
            });

            cmbTarget.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    logger.info("change fKey : {} = {}", cmbTarget.getData(), cmbTarget.getText());
                    relationMap.put(cmbTarget.getData().toString(), cmbTarget.getText());
                    getButton(OK).setEnabled(!relationMap.containsValue(""));
                }
            });
        });
    }

    private void createCardinalitySettingArea(Composite composite) {

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;

        Group group = new Group(composite, SWT.NONE);
        group.setLayoutData(gd);
        group.setText(LABEL_MULTIPLE.getValue());
        group.setLayout(new GridLayout(3, false));

        cmbSourceCardinality = new Combo(group, SWT.READ_ONLY);
        cmbSourceCardinality.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cmbSourceCardinality.setItems(CARDINALITY_SOURCE_ITEMS);
        cmbSourceCardinality.setText(StringUtils.defaultString(relationshipModel.getSourceCardinality()));

        Label equalLabel = new Label(group, SWT.NONE);
        equalLabel.setText(" : ");

        cmbTargetCardinality = new Combo(group, SWT.READ_ONLY);
        cmbTargetCardinality.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cmbTargetCardinality.setItems(CARDINALITY_TARGET_ITEMS);
        cmbTargetCardinality.setText(StringUtils.defaultString(relationshipModel.getTargetCardinality()));
    }

    private void createReferenceOptionSettingArea(Composite composite) {

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;

        Group group = new Group(composite, SWT.NONE);
        group.setLayoutData(gd);
        group.setText(LABEL_OPTION.getValue());
        group.setLayout(new GridLayout(2, false));

        // ON UPDATE
        Label onUpdateLabel = new Label(group, SWT.NONE);
        onUpdateLabel.setText(LABEL_ON_UPDATE.getValue());
        cmbOnUpdate = new Combo(group, SWT.READ_ONLY);
        cmbOnUpdate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cmbOnUpdate.setItems(REFERENCE_OPTION_ITEMS);
        if (StringUtils.isEmpty(relationshipModel.getOnUpdateOption())) {
            cmbOnUpdate.setText(ReferenceOption.NO_ACTION.value());
        } else {
            cmbOnUpdate.setText(relationshipModel.getOnUpdateOption());
        }

        // ON DELETE
        Label onDeleteLabel = new Label(group, SWT.NONE);
        onDeleteLabel.setText(LABEL_ON_DELETE.getValue());
        cmbOnDelete = new Combo(group, SWT.READ_ONLY);
        cmbOnDelete.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        cmbOnDelete.setItems(REFERENCE_OPTION_ITEMS);
        if (StringUtils.isEmpty(relationshipModel.getOnDeleteOption())) {
            cmbOnDelete.setText(ReferenceOption.NO_ACTION.value());
        } else {
            cmbOnDelete.setText(relationshipModel.getOnDeleteOption());
        }
    }

    @Override
    protected void okPressed() {

        List<RelationshipMappingModel> newRelationshipMappings = new ArrayList<>();

        AtomicInteger index = new AtomicInteger();
        relationMap.entrySet().forEach(entry -> {
            String sourceColumnName = entry.getKey();
            String targetColumnName = entry.getValue();

            logger.info("[{}]{} : {}", index, sourceColumnName, targetColumnName);

            try {
                ColumnModel source = helper.getSelectColumnModel(
                        sourceColumnName,
                        relationshipModel.getSource().getColumns());

                ColumnModel target = helper.getSelectColumnModel(
                        targetColumnName,
                        relationshipModel.getTarget().getColumns());

                RelationshipMappingModel mapping = new RelationshipMappingModel(source, target);
                newRelationshipMappings.add(mapping);

            } catch (NotFoundException e) {
                // TODO
                logger.error("error", e);
            }
            index.incrementAndGet();

        });

        relationshipModel.setForeignKeyName(txtForeignKeyName.getText());
        if (StringUtils.isNotEmpty(cmbOnUpdate.getText())
                && !ReferenceOption.NO_ACTION.name().equals(cmbOnUpdate.getText())) {
            relationshipModel.setOnUpdateOption(cmbOnUpdate.getText());
        } else {
            relationshipModel.setOnUpdateOption(null);
        }
        if (StringUtils.isNotEmpty(cmbOnDelete.getText())
                && !ReferenceOption.NO_ACTION.name().equals(cmbOnDelete.getText())) {
            relationshipModel.setOnDeleteOption(cmbOnDelete.getText());
        } else {
            relationshipModel.setOnDeleteOption(null);
        }
        relationshipModel.setSourceCardinality(cmbSourceCardinality.getText());
        relationshipModel.setTargetCardinality(cmbTargetCardinality.getText());
        relationshipModel.setMappings(newRelationshipMappings);

        validate();

        super.okPressed();
    }

    @Override
    public boolean isLogical() {
        return logicalMode;
    }

    @Override
    public void validate() {
    }
}
