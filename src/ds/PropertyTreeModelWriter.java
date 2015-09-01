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
package ds;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import gui.propertysheet.BasicProperty;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.ConverterFactory;
import gui.propertysheet.abs.DefaultConverterFactory;
import gui.propertysheet.abs.DefaultPropertyTreeNodeConverter;
import gui.propertysheet.abs.ManualConverterFactory;
import gui.propertysheet.abs.PropertyTreeConverter;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.ColorPropertyConverter;
import gui.propertysheet.types.DoubleProperty;
import gui.propertysheet.types.EnumConverter;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.IntegerRangePropertyConverter;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringListPropertyConverter;
import gui.propertysheet.types.StringProperty;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeModelWriter {

    Map<String, ConverterFactory<? extends BasicProperty<?>, ?>> propertyConverterMap = new HashMap<>();

    public PropertyTreeModelWriter() {
        // initialize the default loaders
        registerProperty(new DefaultConverterFactory<>("boolNode", () -> new BooleanProperty(), BooleanProperty.class, Boolean.class));
        registerProperty(new DefaultConverterFactory<>("intNode", () -> new IntegerProperty(), IntegerProperty.class, Integer.class));
        registerProperty(new DefaultConverterFactory<>("doubleNode", () -> new DoubleProperty(), DoubleProperty.class, Double.class));
        registerProperty(new DefaultConverterFactory<>("stringNode", () -> new StringProperty(), StringProperty.class, String.class));
        //registerProperty(new DefaultConverterFactory<>("colorNode", () -> new ColorProperty(), ColorProperty.class, Color.class));
        registerProperty(new ManualConverterFactory<>(new ColorPropertyConverter(), ColorProperty.class));
        registerProperty(new ManualConverterFactory<>(new EnumConverter(), EnumProperty.class));
        registerProperty(new ManualConverterFactory<>(new IntegerRangePropertyConverter(), IntegerRangeProperty.class));
        registerProperty(new ManualConverterFactory<>(new StringListPropertyConverter(), StringListProperty.class));
    }

    public final void registerProperty(ConverterFactory<? extends BasicProperty<?>, ?> factory) {
        propertyConverterMap.put(factory.getName(), factory);
    }

    /**
     * Saves a file containing the configuration given in a {@link PropertyTreeModel} in XML-Format.
     *
     * @param propertyTreeModel the model that should be written to the file
     * @param file the file
     * @throws java.io.IOException if an error during writing occurs
     */
    public void saveConfigFile(PropertyTreeModel propertyTreeModel, File file) throws IOException {
        XStream xstream = new XStream();
        Annotations.configureAliases(xstream, PropertyTreeModel.class);
        Annotations.configureAliases(xstream, PropertyTreeNode.class);

        DefaultPropertyTreeNodeConverter treeNodeConverter = new DefaultPropertyTreeNodeConverter(propertyConverterMap);
        xstream.registerConverter(new PropertyTreeConverter(treeNodeConverter));
        xstream.registerConverter(treeNodeConverter);

        PropertyTreeNode root = propertyTreeModel.getRoot();
        List<BasicProperty<?>> props = root.getProperties();
        if (props.size() > 0) {
            StringProperty name = (StringProperty) props.get(0);
            propertyTreeModel.setPropertyName(name.getValue());
        }

        xstream.toXML(propertyTreeModel, new FileWriter(file));
    }
}
