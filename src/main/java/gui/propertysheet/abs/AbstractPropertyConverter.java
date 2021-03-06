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
package gui.propertysheet.abs;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import gui.propertysheet.GenericProperty;

/**
 *
 * @param <P> the property class
 * @author Jan-Philipp Kappmeier
 */
public abstract class AbstractPropertyConverter<P extends GenericProperty> implements Converter {

    protected P prop;

    public abstract String getNodeName();

    public abstract void createNewProp();

    public abstract void writeValue(MarshallingContext context);

    public abstract void readValue(UnmarshallingContext context);

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        prop = getProp(source);
        writer.startNode(getNodeName());
        writeAttributes(writer);
        writeValue(context);
        writer.endNode();
    }
    
    @SuppressWarnings("unchecked")
    private P getProp(Object source) {
        return (P) source;
    }

    public void readAttributes(HierarchicalStreamReader reader) {
        String name = reader.getAttribute(PropertyTreeNodeConverter.ATTRIBUTE_NAME);
        prop.setDisplayName(name);
        prop.useAsLocString("true".equals(reader.getAttribute(PropertyTreeNodeConverter.ATTRIBUTE_LOC_STRING)));
        prop.setShortDescription(reader.getAttribute(PropertyTreeNodeConverter.ATTRIBUTE_INFORMATION));
        prop.setName(reader.getAttribute(PropertyTreeNodeConverter.ATTRIBUTE_PARAMETER));
    }

    public void writeAttributes(HierarchicalStreamWriter writer) {
        writer.addAttribute(PropertyTreeNodeConverter.ATTRIBUTE_NAME, prop.getDisplayNameTag());
        writer.addAttribute(PropertyTreeNodeConverter.ATTRIBUTE_LOC_STRING, Boolean.toString(prop.isUsedAsLocString()));
        writer.addAttribute(PropertyTreeNodeConverter.ATTRIBUTE_INFORMATION, prop.getShortDescriptionTag());
        writer.addAttribute(PropertyTreeNodeConverter.ATTRIBUTE_PARAMETER, prop.getName());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        createNewProp();
        readAttributes(reader);
        readValue(context);
        return prop;
    }

}
