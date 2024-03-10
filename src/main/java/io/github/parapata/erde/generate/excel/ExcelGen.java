package io.github.parapata.erde.generate.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jxls.builder.JxlsOutputFile;
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.core.util.swt.UIUtils;

/**
 * ExcelGen.
 *
 * @author modified by parapata
 */
public class ExcelGen {

    public void export(File outFile, List<TableData> tables) {

        List<String> sheetNames = tables.stream()
                .map(column -> column.getPhysicalTableName())
                .collect(Collectors.toList());

        try (OutputStream out = new JxlsOutputFile(outFile).getOutputStream()) {
            Map<String, Object> data = new HashMap<>();
            data.put("tables", tables);
            data.put("sheetNames", sheetNames);
            JxlsPoiTemplateFillerBuilder.newInstance()
                    .withTemplate("template.xlsx")
                    .build()
                    .fill(data, new JxlsOutputFile(outFile));
        } catch (IOException e) {
            ERDPlugin.logException(e);
        } finally {
            UIUtils.projectRefresh();
        }
    }
}
