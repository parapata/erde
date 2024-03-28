package io.github.parapata.erde.generate.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.parapata.erde.Resource;
import io.github.parapata.erde.core.util.StringUtils;

/**
 * HtmlGenUtils.
 *
 * @author parapata
 */
public class HtmlGenUtils {

    public static String escapeHTML(String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }

        String result = str;
        result = result.replaceAll("&", "&amp;");
        result = result.replaceAll("<", "&lt;");
        result = result.replaceAll(">", "&gt;");
        result = result.replaceAll("\"", "&quote;");
        result = result.replaceAll("\r\n", "\n");
        result = result.replaceAll("\r", "\n");
        result = result.replaceAll("\n", "<br>");
        return result;
    }

    public static String getResource(String key) {
        return Resource.toResource(key).getValue();
    }

    public static String getType(String type, String columnSize, String decimal, String unsigned) {

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(type)) {
            sb.append(type);
            if (StringUtils.isNotEmpty(columnSize)) {
                if (StringUtils.isEmpty(decimal)) {
                    sb.append(String.format("(%s)", columnSize));
                } else {
                    sb.append(String.format("(%s,%s)", columnSize, decimal));
                }
            }
            if (StringUtils.toBoolean(unsigned)) {
                sb.append(" UNSIGNED");
            }
        }
        return sb.toString();
    }

    public static boolean toBoolean(String value) {
        return StringUtils.toBoolean(value);
    }

    public static String toSourceColumeName(Object table, String sourceId, String columeName) {
        StringBuilder sb = new StringBuilder();
        NodeList nodes = ((DTMNodeIterator) table).nextNode().getParentNode().getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (StringUtils.equals("table", node.getNodeName())) {
                String id = node.getAttributes().getNamedItem("id").getNodeValue();
                if (StringUtils.equals(id, sourceId)) {
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (StringUtils.equals("physicalName", childNodes.item(j).getNodeName())) {
                            sb.append(childNodes.item(j).getTextContent());
                            sb.append(".");
                        }
                    }
                }
            }
        }
        sb.append(columeName);
        return sb.toString();
    }

    public static boolean isForeignKey(Object table, String columeName) {
        Node node = ((DTMNodeIterator) table).nextNode();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (StringUtils.equals("foreignKey", childNode.getNodeName())) {
                // targetName
                for (int j = 0; j < childNode.getChildNodes().getLength(); j++) {
                    Node fkMappingNode = childNode.getChildNodes().item(j);
                    for (int k = 0; k < fkMappingNode.getChildNodes().getLength(); k++) {
                        Node columnNode = fkMappingNode.getChildNodes().item(k);
                        if (StringUtils.equals("targetName", columnNode.getNodeName())
                                && StringUtils.equals(columeName, columnNode.getTextContent())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isIndexEmpty(Object indexes) {
        return !getIndexNodes(indexes, "INDEX").isEmpty();
    }

    public static boolean isUniqeIndexEmpty(Object indexes) {
        return !getIndexNodes(indexes, "UNIQUE").isEmpty();
    }

    private static List<Node> getIndexNodes(Object indexes, String targetType) {
        List<Node> result = new ArrayList<>();
        DTMNodeIterator iterator = (DTMNodeIterator) indexes;
        Node node = null;
        while ((node = iterator.nextNode()) != null) {
            Node indexType = node.getAttributes().getNamedItem("indexType");
            if (StringUtils.equals(indexType.getNodeValue(), targetType)) {
                result.add(node);
            }
        }
        return result;
    }
}
