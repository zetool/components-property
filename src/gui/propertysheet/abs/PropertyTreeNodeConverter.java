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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package gui.propertysheet.abs;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.zetool.components.property.PropertyConverterLibrary;
import gui.propertysheet.BasicProperty;
import gui.propertysheet.PropertyTreeNode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeNodeConverter implements Converter {
    static final String ATTRIBUTE_LOC_STRING = "useAsLocString";
    static final String ATTRIBUTE_NAME = "name";
    static final String ATTRIBUTE_PROPERTY_NAME = "propertyName";
    static final String ATTRIBUTE_INFORMATION = "information";
    static final String ATTRIBUTE_PARAMETER = "parameter";
    public static final String NODE_NAME = "treeNode";
    private static final Logger LOG = Logger.getGlobal();

    private final PropertyConverterLibrary converter;

    public PropertyTreeNodeConverter(PropertyConverterLibrary converter) {
        this.converter = converter;
    }

    /**
     * Returns true, if the given type can be converted. {@link PropertyTreeConverter}
     *
     * @param type
     * @return true, if the given type can be converted.
     */
    @Override
    public boolean canConvert(Class type) {
        return type.equals(PropertyTreeNode.class);
    }

    /**
     * Writes a node to the XML-file, beginning with writing the start node.
     *
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        PropertyTreeNode node = (PropertyTreeNode) source;
        writer.startNode(NODE_NAME);
        writeAttributes(node, writer);
        writeChildren(node, context);
        writeNodeProperties(node, context);
        writer.endNode();
    }
    
    private void writeAttributes(PropertyTreeNode node, HierarchicalStreamWriter writer) {
        writer.addAttribute(ATTRIBUTE_NAME, node.getDisplayNameTag());
        writer.addAttribute(ATTRIBUTE_LOC_STRING, Boolean.toString(node.isUsedAsLocString()));        
    }
    
    private void writeChildren(PropertyTreeNode node, MarshallingContext context) {
        for (int i = 0; i < node.getChildCount(); i++) {
            PropertyTreeNode child = node.getChildAt(i);
            context.convertAnother(child);
        }
    }

    private void writeNodeProperties(PropertyTreeNode node, MarshallingContext context) {
        node.getProperties().stream().forEach(property -> marshalProperty(property, context));
    }
    
    private void marshalProperty(PropertyElement property, MarshallingContext context) {
        for (ConverterFactory cf : converter) {
            if (cf.getPropertyType().equals(property.getClass())) {
                context.convertAnother(property, cf.getConverter());
                break;
            }
        }
    }

    /**
     * Reads a treeNode element from a XML-file.
     *
     * @param reader
     * @param context
     * @return the converted property tree node
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

        String name = reader.getAttribute(ATTRIBUTE_NAME);
        PropertyTreeNode node = new PropertyTreeNode(name); // this is the displayName
        node.useAsLocString("true".equals(reader.getAttribute(ATTRIBUTE_LOC_STRING)));
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            if (nodeName.equals(NODE_NAME)) {
                PropertyTreeNode child = (PropertyTreeNode) context.convertAnother(node, PropertyTreeNode.class, this);
                node.add(child);
            } else {
                ConverterFactory cf = converter.getFactoryFor(nodeName);
                if (cf != null) {
                    BasicProperty property = (BasicProperty) context.convertAnother(node, cf.getPropertyType(), cf.getConverter());
                    node.addProperty(property);
                } else {
                    LOG.log(Level.SEVERE, "No property defined for ''{0}''.", nodeName);
                }
            }
            reader.moveUp();
        }
        return node;
    }
}
