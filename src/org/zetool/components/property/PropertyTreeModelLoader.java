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
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.abs.PropertyTreeNodeConverter;
import gui.propertysheet.abs.PropertyTreeConverter;
import java.io.File;
import java.io.FileReader;

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
    public PropertyTreeModel loadConfigFile(File file) throws PropertyLoadException {
        PropertyTreeModel propertyTreeModel = null;
        try {
            XStream xstream = new XStream();
            xstream.processAnnotations(new Class[] {PropertyTreeModel.class, PropertyTreeNode.class});
            xstream.alias("zp", PropertyTreeModel.class);
            
            PropertyTreeNodeConverter treeNodeConverter = new PropertyTreeNodeConverter(converterLibrary);
            xstream.registerConverter(new PropertyTreeConverter(treeNodeConverter));
            xstream.registerConverter(treeNodeConverter);

            propertyTreeModel = (PropertyTreeModel) xstream.fromXML(new FileReader(file));
        } catch (Exception ex) {
            throw new PropertyLoadException(file, ex);
        }
        return propertyTreeModel;
    }

}
