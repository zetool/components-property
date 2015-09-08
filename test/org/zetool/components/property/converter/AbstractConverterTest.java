/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zetool.components.property.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import gui.propertysheet.GenericProperty;
import java.io.StringWriter;
import java.io.Writer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <U> The property type
 */
@Ignore
public abstract class AbstractConverterTest<U extends GenericProperty> {

    private final static String PROPERTY_NAME = "test-property-name";
    private String xmlLine;
    private U property;
    private Converter converter;
    private XStream xstream;

    @Before
    public void init() {
        this.converter = getConverter();
        buildProperty();
        buildNodeString();
        initXstream();
    }

    protected abstract Converter getConverter();

    private void buildProperty() {
        property = getProperty();
        property.setName(PROPERTY_NAME);
        property.setValue(getPropertyValue());
    }

    protected abstract U getProperty();

    protected abstract Object getPropertyValue();

    protected abstract Class<U> getType();

    private void buildNodeString() {
        final String nodeName = getExpectedNodeName();
        xmlLine = "<" + nodeName
                + " name=\"\" useAsLocString=\"false\" information=\"\" parameter=\""
                + PROPERTY_NAME + "\">" + getPropertyString() + "</" + nodeName + ">";
    }

    protected abstract String getExpectedNodeName();

    protected abstract String getPropertyString();

    private void initXstream() {
        xstream = new XStream();
        xstream.registerConverter(converter);
        xstream.registerConverter(new DataConverter());
        xstream.alias("converterTest", DataHolder.class);
    }

    @Test
    public void assertProperty() {
        DataHolder dh = new DataHolder(property);
        StringWriter sw = new StringWriter();
        xstream.toXML(dh, sw);
        String result = getXml(sw);
        assertThat(result, is(equalTo(String.join("\n", xmlLine))));
    }
    
    private String getXml(Writer sw) {
        String all = sw.toString();        
        int first = all.indexOf("\n");
        int last = all.lastIndexOf("\n");
        return all.substring(first+1, last).trim();        
    }

    @Test
    public void testUnmarshal() {
        String text = "<converterTest>\n  " + xmlLine + "\n</converterTest>";        
        DataHolder dh = (DataHolder) xstream.fromXML(text);
        assertThat(dh.property, is(equalTo(property)));
        assertThat(dh.property.getValue(), is(equalTo(property.getValue())));
    }

    static class DataHolder {

        private GenericProperty property;

        public DataHolder(GenericProperty property) {
            this.property = property;
        }
    }

    private class DataConverter implements Converter {

        @Override
        public boolean canConvert(Class nodeType) {
            return nodeType.equals(DataHolder.class);
        }

        @Override
        public void marshal(Object value, HierarchicalStreamWriter writer,
                MarshallingContext context) {
            DataHolder dataHolder = (DataHolder) value;
            context.convertAnother(dataHolder.property);
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            reader.moveDown();
            DataHolder dataHolder = new DataHolder(null);
            GenericProperty p = (GenericProperty)context.convertAnother(dataHolder, getType());
            reader.moveUp();
            dataHolder.property = p;
            return dataHolder;
        }
    }
}