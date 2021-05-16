package io.github.erde.dev;

import java.util.Collection;
import java.util.Iterator;
import io.github.erde.dev.GenResourceBean;

public class GenResourceEnumTemplate
{
  protected static String nl;
  public static synchronized GenResourceEnumTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GenResourceEnumTemplate result = new GenResourceEnumTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package io.github.erde;" + NL + "" + NL + "import java.util.Arrays;" + NL + "import java.util.MissingResourceException;" + NL + "import java.util.ResourceBundle;" + NL + "" + NL + "import io.github.erde.core.util.StringUtils;" + NL + "" + NL + "/**" + NL + " * Resource Enum." + NL + " *" + NL + " * @author parapata" + NL + " * @since 1.0.8" + NL + " */" + NL + "public enum Resource {" + NL;
  protected final String TEXT_2 = NL + "    /** key:";
  protected final String TEXT_3 = ". */";
  protected final String TEXT_4 = NL + "    ";
  protected final String TEXT_5 = "(\"";
  protected final String TEXT_6 = "\"),";
  protected final String TEXT_7 = NL + "    ;" + NL + "" + NL + "    private static final ResourceBundle resource = ResourceBundle.getBundle(\"io.github.erde.messages\");" + NL + "" + NL + "    private String propKey;" + NL + "" + NL + "    private Resource(String propKey) {" + NL + "        this.propKey = propKey;" + NL + "    }" + NL + "" + NL + "    public String getValue() {" + NL + "        try {" + NL + "            return resource.getString(propKey);" + NL + "        } catch (MissingResourceException e) {" + NL + "            return propKey;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public String getValue(String defaultValue) {" + NL + "        try {" + NL + "            String result = resource.getString(propKey);" + NL + "            return StringUtils.isEmpty(result) ? defaultValue : result;" + NL + "        } catch (MissingResourceException e) {" + NL + "            return propKey;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public String createMessage(String... args) {" + NL + "        String message = resource.getString(propKey);" + NL + "        for (int i = 0; i < args.length; i++) {" + NL + "            message = message.replaceAll(String.format(\"\\\\{%d\\\\}\", i), args[i]);" + NL + "        }" + NL + "        return message;" + NL + "    }" + NL + "" + NL + "    public String getPropKey() {" + NL + "        return propKey;" + NL + "    }" + NL + "" + NL + "    public static Resource toResource(String propKey) {" + NL + "        return Arrays.stream(Resource.values())" + NL + "                .filter(key -> key.getPropKey().equals(propKey))" + NL + "                .findFirst()" + NL + "                .orElseThrow();" + NL + "    }" + NL + "}";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     Collection<GenResourceBean> data = (Collection<GenResourceBean>) argument; 
     if (data.size() != 0) { 
         for (Iterator<GenResourceBean> ite = data.iterator(); ite.hasNext(); ) { 
             GenResourceBean obj = ite.next();
    stringBuffer.append(TEXT_2);
    stringBuffer.append(obj.getPropKey());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(obj.getCode());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(obj.getPropKey());
    stringBuffer.append(TEXT_6);
         } 
     } 
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
