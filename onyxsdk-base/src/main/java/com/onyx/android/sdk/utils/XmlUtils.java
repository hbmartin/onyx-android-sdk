package com.onyx.android.sdk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/XmlUtils.class */
public class XmlUtils {
    public static <T> List<T> loadBeansFromXml(int xmlResId, Class<T> tClass) {
        ArrayList arrayList = new ArrayList();
        Document documentLoadXmlWithDom = ResManager.loadXmlWithDom(xmlResId);
        if (documentLoadXmlWithDom == null || documentLoadXmlWithDom.getDocumentElement() == null) {
            Debug.e(XmlUtils.class, "null document: " + xmlResId, new Object[0]);
            return arrayList;
        }
        NodeList elementsByTagName = documentLoadXmlWithDom.getDocumentElement().getElementsByTagName("map");
        if (elementsByTagName == null) {
            Debug.e(XmlUtils.class, "null nodeList: " + xmlResId, new Object[0]);
            return arrayList;
        }
        int length = elementsByTagName.getLength();
        for (int i = 0; i < length; i++) {
            HashMap map = new HashMap();
            NodeList childNodes = elementsByTagName.item(i).getChildNodes();
            int length2 = childNodes.getLength();
            for (int i2 = 0; i2 < length2; i2++) {
                Node nodeItem = childNodes.item(i2);
                if (nodeItem.getAttributes() != null && nodeItem.getChildNodes() != null && nodeItem.getChildNodes().getLength() > 0) {
                    map.put(nodeItem.getAttributes().getNamedItem("key").getNodeValue(), nodeItem.getChildNodes().item(0).getNodeValue());
                }
            }
            arrayList.add(JSONUtils.parseObject(map, tClass));
        }
        return arrayList;
    }
}
