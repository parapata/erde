package io.github.erde.gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import io.github.erde.core.util.PropertiesEx;

public class GenPropFile {

    public static void main(String[] args) {
        try {
            new GenPropFile().execute();
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final String PROP_COMMENT = "ER-Diagram editor for Eclipse.";

    private static final String US_FILE_NAME = "resources/io/github/erde/messages.properties";
    private static final String JA_FILE_NAME = "resources/io/github/erde/messages_ja.properties";
    private static final String SRC_FILE_NAME = "src/io/github/erde/Resource.java";

    private static final String TEMPLATE_FILE = "gen/ResourceEnum.vm";

    private static final String XLSL_FILE = "gen/properties_list.xlsx";
    private static final String SHEET_NAME = "Sheet1";
    private static final int START_ROW = 2;
    private static final int CELL_KEY = 0;
    private static final int CELL_US = 1;
    private static final int CELL_JA = 2;

    public void execute() throws EncryptedDocumentException, IOException {

        List<GenResourceBean> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(XLSL_FILE))) {
            // エクセルファイルへアクセスするためのオブジェクト
            Workbook excel = WorkbookFactory.create(fis);
            Sheet sheet = excel.getSheet(SHEET_NAME);

            for (int i = START_ROW; i <= sheet.getLastRowNum(); i++) {

                GenResourceBean bean = new GenResourceBean();

                Row row = sheet.getRow(i);

                String propKey = row.getCell(CELL_KEY).getStringCellValue();

                bean.setPropKey(propKey);

                List<String> works = new ArrayList<>();
                Arrays.stream(StringUtils.splitByWholeSeparator(propKey, ".")).forEach(str -> {
                    works.add(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(str), "_"));
                });

                bean.setCode(StringUtils.join(works, "_").toUpperCase());
                bean.setUs(row.getCell(CELL_US).getStringCellValue());
                bean.setJa(row.getCell(CELL_JA).getStringCellValue());

                list.add(bean);
            }
        }

        // Generate code
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("list", list);
        try (FileWriter writer = new FileWriter(new File(SRC_FILE_NAME))) {
            Template template = Velocity.getTemplate(TEMPLATE_FILE);
            template.merge(context, writer);
            writer.flush();
        }

        extracted(list);
    }

    private void extracted(List<GenResourceBean> list) throws IOException {

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
