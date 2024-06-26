package io.github.parapata.erde.dev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.github.parapata.erde.core.util.PropertiesEx;
import io.github.parapata.erde.core.util.StringUtils;

/**
 * Resource generator.
 *
 * @author parapata
 * @since 1.0.12
 */
public class GenResource {

    public static void main(String[] args) throws EncryptedDocumentException, IOException {
        new GenResource().execute();
    }

    private static final String PROP_COMMENT = "ER-Diagram editor for Eclipse.";

    private static final String US_FILE_NAME = "src/io/github/parapata/erde/messages.properties";
    private static final String JA_FILE_NAME = "src/io/github/parapata/erde/messages_ja.properties";
    private static final String SRC_FILE_NAME = "src/io/github/parapata/erde/Resource.java";

    private static final String XLSL_FILE = "gen/properties_list.xlsx";
    private static final int START_ROW = 2;
    private static final int CELL_KEY = 0;
    private static final int CELL_US = 1;
    private static final int CELL_JA = 2;

    public GenResource() {
    }

    public void execute() throws EncryptedDocumentException, IOException {

        List<GenResourceBean> list = getResources();

        // Enumソースコードの作成
        genEnumSrc(list);

        // プロパティファイルの作成
        genProp(list);
    }

    private List<GenResourceBean> getResources() throws IOException {
        List<GenResourceBean> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(XLSL_FILE))) {
            // エクセルファイルへアクセスするためのオブジェクト
            Workbook book = WorkbookFactory.create(fis);

            for (String sheetName : getSheetNames(book)) {
                Sheet sheet = book.getSheet(sheetName);
                addItems(list, sheet);
            }
        }
        return list;
    }

    private List<String> getSheetNames(Workbook book) {
        return IntStream.range(0, book.getNumberOfSheets())
                .mapToObj(book::getSheetAt)
                .filter(sheet -> !sheet.getSheetName().startsWith("#"))
                .map(Sheet::getSheetName)
                .collect(Collectors.toList());
    }

    private void addItems(List<GenResourceBean> list, Sheet sheet) {
        for (int i = START_ROW; i <= sheet.getLastRowNum(); i++) {

            GenResourceBean bean = new GenResourceBean();

            Row row = sheet.getRow(i);

            String propKey = row.getCell(CELL_KEY).getStringCellValue();

            if (StringUtils.isEmpty(propKey)) {
                continue;
            }

            bean.setPropKey(propKey);

            List<String> works = new ArrayList<>();
            Arrays.stream(StringUtils.split(propKey, ".")).forEach(str -> {
                works.add(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(str), "_"));
            });

            bean.setCode(StringUtils.join(works, "_").toUpperCase());
            bean.setUs(row.getCell(CELL_US).getStringCellValue());
            bean.setJa(row.getCell(CELL_JA).getStringCellValue());
            list.add(bean);
        }
    }

    private void genEnumSrc(List<GenResourceBean> list) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(SRC_FILE_NAME))) {
            GenResourceEnumTemplate template = GenResourceEnumTemplate.create(null);
            osw.write(template.generate(list));
        }
    }

    private void genProp(List<GenResourceBean> list) throws IOException {

        // Generate properties file
        try (FileWriter usWriter = new FileWriter(new File(US_FILE_NAME));
                FileWriter jaWriter = new FileWriter(new File(JA_FILE_NAME))) {
            PropertiesEx jaProps = new PropertiesEx();
            PropertiesEx usProps = new PropertiesEx();
            list.forEach(bean -> {
                usProps.setProperty(bean.getPropKey(), bean.getUs());
                jaProps.setProperty(bean.getPropKey(), bean.getJa());
            });
            usProps.store(usWriter, PROP_COMMENT);
            jaProps.store(jaWriter, PROP_COMMENT);
        }
    }
}
