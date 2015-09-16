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
package org.zetool.components.property;

import com.thoughtworks.xstream.XStream;
import ds.PropertyContainer;
import gui.propertysheet.GenericProperty;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.PropertyTreeNodeConverter;
import gui.propertysheet.abs.PropertyTreeConverter;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.DoubleProperty;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringProperty;
import java.awt.Color;
import java.io.Reader;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeModelLoader {
    private final PropertyConverterLibrary converterLibrary;

    public PropertyTreeModelLoader() {
        this.converterLibrary = PropertyConverterLibrary.createDefaultConverters();
    }

    public PropertyTreeModelLoader(PropertyConverterLibrary converter) {
        this.converterLibrary = converter;
    }
    
    /**
     * Loads a file containing the configuration in XML-Format.
     *
     * @param file the property file
     * @return the tree model containing the properties
     * @throws PropertyLoadException if an xstream error occured when the file is read
     */
    public PropertyTreeModel loadConfigFile(Reader file) throws PropertyLoadException {
        PropertyTreeModel propertyTreeModel = null;
        try {
            XStream xstream = new XStream();
            xstream.processAnnotations(new Class<?>[] {PropertyTreeModel.class, PropertyTreeNode.class});
            xstream.alias("zp", PropertyTreeModel.class);
            
            PropertyTreeNodeConverter treeNodeConverter = new PropertyTreeNodeConverter(converterLibrary);
            xstream.registerConverter(new PropertyTreeConverter(treeNodeConverter));
            xstream.registerConverter(treeNodeConverter);

            propertyTreeModel = (PropertyTreeModel) xstream.fromXML(file);
        } catch (Exception ex) {
            throw new PropertyLoadException(file, ex);
        }
        return propertyTreeModel;
    }

    /**
     * Loads properties from an XML-file into the {@link PropertyContainer} and returns a {@link PropertyTreeModel} of
     * the properties. This can be used to store the same (maybe changed) data later.
     *
     * @param file the property XML-file
     * @param pc
     * @return a model of the loaded properties
     * @throws PropertyLoadException if an error occurs during loading of the specified file
     */
    public PropertyTreeModel applyParameters(Reader file, PropertyContainer pc) throws PropertyLoadException {
        final PropertyTreeModel ptm = loadConfigFile(file);
        applyParameters(ptm, pc);
        return ptm;
    }

    /**
     * Loads properties from an {@link PropertyTreeModel} into the {@link PropertyContainer}.
     *
     * @param propertyTreeModel the tree model containing the properties
     * @param pc
     */
    public void applyParameters(PropertyTreeModel propertyTreeModel, PropertyContainer pc) {
        applyParameters(propertyTreeModel.getRoot(), pc);
    }

    /**
     * Loads properties stored in an node of an {@link PropertyTreeModel} into the {@link PropertyContainer}.
     *
     * @param node the node at which recursive loading starts.
     * @param pc
     */
    protected void applyParameters(PropertyTreeNode node, PropertyContainer pc) {
        for (int i = 0; i < node.getChildCount(); i++) {
            applyParameters(node.getChildAt(i), pc);
        }
        for (GenericProperty property : node.getProperties()) {
            if (property instanceof BooleanProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Boolean.class, (Boolean) property.getValue());
                } else {
                    pc.set(property.getName(), (Boolean) property.getValue());
                }
            } else if (property instanceof IntegerProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Integer.class, (Integer) property.getValue());
                } else {
                    pc.set(property.getName(), (Integer) property.getValue());
                }
            } else if (property instanceof DoubleProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Double.class, (Double) property.getValue());
                } else {
                    pc.set(property.getName(), (Double) property.getValue());
                }
            } else if (property instanceof StringProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), String.class, (String) property.getValue());
                } else {
                    pc.set(property.getName(), (String) property.getValue());
                }
            } else if (property instanceof StringListProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), List.class, (List) property.getValue());
                } else {
                    pc.set(property.getName(), (List) property.getValue());
                }
            } else if( property instanceof EnumProperty) {
                if( !pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Enum.class, (Enum)property.getValue());
                } else {
                    pc.set(property.getName(), (Enum)property.getValue() );
                }
            } else if (property instanceof ColorProperty) {
                if (!pc.isDefined(property.getName())) {
                    pc.define(property.getName(), Color.class, (Color) property.getValue());
                } else {
                    pc.set(property.getName(), (Color) property.getValue());
                }
            } else {
                throw new UnsupportedOperationException("Type " + property.getName() + " not supported.");
            }
        }
    }
}
