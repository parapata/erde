package io.github.erde.generate.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import io.github.erde.ERDPlugin;
import io.github.erde.core.util.swt.UIUtils;

/**
 * ExcelGen.
 *
 * @author modified by parapata
 */
public class ExcelGen {

    private File outFile;

    public ExcelGen(File outFile) {
        this.outFile = outFile;
    }

    public void export(List<TableData> tables) {
        List<String> sheetNames = tables.stream()
                .map(column -> column.getPhysicalTableName())
                .collect(Collectors.toList());
        XlsCommentAreaBuilder.addCommandMapping(AutoRowHeightCommand.COMMAND_NAME, AutoRowHeightCommand.class);
        try (InputStream in = ExcelGen.class.getResourceAsStream("template.xlsx");
                FileOutputStream out = new FileOutputStream(outFile)) {
            Context context = PoiTransformer.createInitialContext();
            context.putVar("tables", tables);
            context.putVar("sheetNames", sheetNames);
            JxlsHelper.getInstance().processTemplate(in, out, context);
        } catch (Exception e) {
            ERDPlugin.logException(e);
        } finally {
            UIUtils.projectRefresh();
        }
    }
}
