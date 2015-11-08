/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * An abstract test class providing test cases to convert a specialized {@link GenericProperty} to XML and vice versa.
 * The abstract test implementation requires each subclass (which tests a special type of property) to provide a
 * {@link Converter}, a {@code value} and a string that should encode the value in XML.
 * 
 * The implemented tests then convert the value to XML and assert the result is equal to the given string as well as
 * generates a value out of the string and checks if it is equal with the original given value.
 * @author Jan-Philipp Kappmeier
 * @param <U> The property type
 */
@Ignore
public abstract class AbstractConverterTest<U extends GenericProperty> {

    private static final String PROPERTY_NAME = "test-property-name";
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
        final String additionalparameters = getAdditionalParameters().map(s -> " " + s).collect(Collectors.joining());
        xmlLine = "<" + nodeName
                + " name=\"\" useAsLocString=\"false\" information=\"\" parameter=\""
                + PROPERTY_NAME + "\"" + additionalparameters + ">" + getPropertyString() + "</" + nodeName + ">";
    }

    protected abstract String getExpectedNodeName();

    protected abstract String getPropertyString();
    
    protected Stream<String> getAdditionalParameters() {
        return Stream.<String>empty();
    }

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
