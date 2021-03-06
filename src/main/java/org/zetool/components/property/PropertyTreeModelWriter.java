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
package org.zetool.components.property;

import com.thoughtworks.xstream.XStream;
import gui.propertysheet.GenericProperty;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.PropertyTreeNodeConverter;
import gui.propertysheet.abs.PropertyTreeConverter;
import gui.propertysheet.types.StringProperty;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeModelWriter {
    private final PropertyConverterLibrary converterLibrary;

    public PropertyTreeModelWriter() {
        this.converterLibrary = PropertyConverterLibrary.createDefaultConverters();
    }

    public PropertyTreeModelWriter(PropertyConverterLibrary converter) {
        this.converterLibrary = converter;
    }
    
    /**
     * Saves a file containing the configuration given in a {@link PropertyTreeModel} in XML-Format.
     *
     * @param propertyTreeModel the model that should be written to the file
     * @param writer the writer
     * @throws java.io.IOException if an error during writing occurs
     */
    public void saveConfigFile(PropertyTreeModel propertyTreeModel, Writer writer) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class<?>[] {PropertyTreeModel.class, PropertyTreeNode.class});
        xstream.alias("zp", PropertyTreeModel.class);

        PropertyTreeNodeConverter treeNodeConverter = new PropertyTreeNodeConverter(converterLibrary);
        xstream.registerConverter(new PropertyTreeConverter(treeNodeConverter));
        xstream.registerConverter(treeNodeConverter);

        PropertyTreeNode root = propertyTreeModel.getRoot();
        List<GenericProperty> props = root.getProperties();
        if (!props.isEmpty()) {
            StringProperty name = (StringProperty) props.get(0);
            propertyTreeModel.setPropertyName(name.getValue());
        }

        xstream.toXML(propertyTreeModel, writer);
    }
}
