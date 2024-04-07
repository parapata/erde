package io.github.parapata.erde.generate.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jxls.builder.JxlsOutputFile;
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;

import io.github.parapata.erde.ErdePlugin;
import io.github.parapata.erde.core.util.swt.UIUtils;

/**
 * ExcelGen.
 *
 * @author modified by parapata
 */
public class ExcelGen {

    static String TEMPLATE = "/io/github/parapata/erde/generate/excel/template.xlsx";

    public void export(File outFile, List<TableData> tables) {

        List<String> sheetNames = tables.stream()
                .map(column -> column.getPhysicalTableName())
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("tables", tables);             // テーブル一覧
        data.put("sheetNames", sheetNames);     // 各テーブル定義表のシート名

        try (InputStream in = ErdePlugin.class.getResourceAsStream(TEMPLATE);
                OutputStream out = new JxlsOutputFile(outFile).getOutputStream()) {
            JxlsPoiTemplateFillerBuilder.newInstance()
                    .withTemplate(in)
                    .withCommand(AutoRowHeightCommand.COMMAND_NAME, AutoRowHeightCommand.class)
                    .build()
                    .fill(data, new JxlsOutputFile(outFile));

        } catch (IOException e) {
            ErdePlugin.logException(e);
        } finally {
            UIUtils.projectRefresh();
        }
    }

// jxsl 2.x
// public void export(List<TableData> tables) {
// List<String> sheetNames = tables.stream()
// .map(column -> column.getPhysicalTableName())
// .collect(Collectors.toList());
// XlsCommentAreaBuilder.addCommandMapping(AutoRowHeightCommand.COMMAND_NAME, AutoRowHeightCommand.class);
// try (InputStream in = ExcelGen.class.getResourceAsStream("template.xlsx");
// FileOutputStream out = new FileOutputStream(outFile)) {
// Context context = PoiTransformer.createInitialContext();
// context.putVar("tables", tables);
// context.putVar("sheetNames", sheetNames);
// JxlsHelper.getInstance().processTemplate(in, out, context);
// } catch (Exception e) {
// ERDPlugin.logException(e);
// } finally {
// UIUtils.projectRefresh();
// }
// }
}
