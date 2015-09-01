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
import gui.propertysheet.BasicProperty;
import gui.propertysheet.PropertyTreeNode;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DefaultPropertyTreeNodeConverter implements Converter {

    Map<String, ConverterFactory<? extends BasicProperty<?>, ?>> propertyConverterMap;

    public DefaultPropertyTreeNodeConverter(Map<String, ConverterFactory<? extends BasicProperty<?>, ?>> propertyConverterMap) {
        this.propertyConverterMap = propertyConverterMap;
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
        writer.startNode("treeNode");
        writer.addAttribute("name", node.getDisplayNameTag());
        writer.addAttribute("useAsLocString", Boolean.toString(node.isUsedAsLocString()));
        for (int i = 0; i < node.getChildCount(); i++) {
            PropertyTreeNode child = node.getChildAt(i);
            context.convertAnother(child);
        }
        for (BasicProperty<?> property : node.getProperties()) {
            System.out.println( "Looking for converter for " + property);
            for (ConverterFactory cf : propertyConverterMap.values()) {
                if (cf.getPropertyType().equals(property.getClass())) {
                    System.out.println("Known property found: " + cf.getPropertyType());
                    context.convertAnother(property, cf.getConverter());
                    break;
                } else {
                    System.out.println("Different property: " + cf.getPropertyType());
                }
            }
//            } else if (property instanceof StringListProperty) {
//                context.convertAnother(property, new StringListPropertyConverter());
//            } else if (property instanceof ColorProperty) {
//                context.convertAnother(property, new ColorPropertyConverter());
//            }
        }
        writer.endNode();
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

        String name = reader.getAttribute("name");
        PropertyTreeNode node = new PropertyTreeNode(name); // this is the displayName
        node.useAsLocString((reader.getAttribute("useAsLocString").equals("true")));
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            if (nodeName.equals("treeNode")) {
                PropertyTreeNode child = (PropertyTreeNode)context.convertAnother(node, PropertyTreeNode.class, this);
                System.out.println("Converted: " + child.getDisplayName() + " (" + child.getProperties().size() + " properties)");
                node.add(child);
            } else {
                ConverterFactory<? extends BasicProperty<?>, ?> cf = propertyConverterMap.get(nodeName);
                if( cf != null ) {
                    BasicProperty property = (BasicProperty) context.convertAnother(node, cf.getPropertyType(), cf.getConverter());
                    System.out.println("Adding " + property.getValue() + " to node " + node.getDisplayName());
                    node.addProperty(property);
                } else {
                    Logger logger = Logger.getGlobal();
                    logger.log(Level.SEVERE, "No property defined for ''{0}''.", nodeName);
                }
            }
//            switch (nodeName) {
//                case "treeNode":
//                    PropertyTreeNode child = (PropertyTreeNode) context.convertAnother(node, PropertyTreeNode.class, new DefaultPropertyTreeNodeConverter());
//                    node.add(child);
//                    break;
//                case "boolNode":
//                    //BooleanProperty bool = (BooleanProperty) context.convertAnother(node, BooleanProperty.class, new BooleanPropertyConverter());
//                    BooleanProperty bool = (BooleanProperty) context.convertAnother(node, booleanConverterFactory.getPropertyType(), booleanConverterFactory.get());
//                    node.addProperty(bool);
//                    break;
//                case "intNode": {
//                    IntegerProperty intP = (IntegerProperty) context.convertAnother(node, IntegerProperty.class, new IntegerPropertyConverter());
//                    node.addProperty(intP);
//                    break;
//                }
//                case "intRangeNode": {
//                    IntegerRangeProperty intP = (IntegerRangeProperty) context.convertAnother(node, IntegerRangeProperty.class, new IntegerRangePropertyConverter());
//                    node.addProperty(intP);
//                    break;
//                }
//                case "doubleNode":
//                    DoubleProperty doubleP = (DoubleProperty) context.convertAnother(node, DoubleProperty.class, new DoublePropertyConverter());
//                    node.addProperty(doubleP);
//                    break;
//                case "stringNode": {
//                    StringProperty stringP = (StringProperty) context.convertAnother(node, StringProperty.class, new StringPropertyConverter());
//                    node.addProperty(stringP);
//                    break;
//                }
//                case "stringListNode": {
//                    StringListProperty stringP = (StringListProperty) context.convertAnother(node, StringListProperty.class, new StringListPropertyConverter());
//                    node.addProperty(stringP);
//                    break;
//                }
//                case "enum":
//                    EnumProperty qualityP = (EnumProperty) context.convertAnother(node, EnumProperty.class, new EnumConverter());
//                    node.addProperty(qualityP);
//                    break;
//                case "colorNode":
//                    ColorProperty colorP = (ColorProperty) context.convertAnother(node, ColorProperty.class, new ColorPropertyConverter());
//                    node.addProperty(colorP);
//                    break;
//                default:
//                    throw new UnsupportedOperationException("Unknown type: " + nodeName);
//            }
            reader.moveUp();
        }
        return node;
    }

}
