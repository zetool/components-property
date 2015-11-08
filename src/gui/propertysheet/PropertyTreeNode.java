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
package gui.propertysheet;

import gui.propertysheet.abs.PropertyElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.zetool.common.localization.AbstractLocalization;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 * The user property is overridden with string.
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeNode implements PropertyElement {

    private Localization loc = CommonLocalization.LOC;
    boolean useAsLocString = false;
    String name;
    List<GenericProperty> properties = new ArrayList<>();
    List<PropertyTreeNode> children = new ArrayList<>();

    public PropertyTreeNode(String name) {
        this.name = name;
    }

    public void addProperty(BasicProperty<?> property) {
        properties.add(property);
    }

    public void clearProperties() {
        properties.clear();
    }

    public List<GenericProperty> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void reloadFromPropertyContainer() {
        for (GenericProperty apv : properties) {
            BasicProperty.reloadFromPropertyContainer(apv);
        }
        if (children != null) {
            for (Object ptn : children) {
                ((PropertyTreeNode) ptn).reloadFromPropertyContainer();
            }
        }
    }

    /**
     * Returns {@code true} if the strings for the name, information and description in the XML-file shall be tags used
     * for localization.
     *
     * @return {@code true} if the XML-file contains localization tags, {@code false} otherwise
     * @see AbstractLocalization
     */
    @Override
    public boolean isUsedAsLocString() {
        return useAsLocString;
    }

    /**
     *
     * @param useAsLocString
     */
    @Override
    public void useAsLocString(boolean useAsLocString) {
        this.useAsLocString = useAsLocString;
    }

    /**
     * Returns the name of the property stored in this node. If it {@link #isUsedAsLocString()}, the localized string is
     * returned.
     *
     * @return the name of the property stored in this node
     */
    @Override
    public String getDisplayName() {
        return isUsedAsLocString() ? loc.getString(name) : name;
    }

    @Override
    public String getDisplayNameTag() {
        return name;
    }

    /**
     * Assigns a new name to the property stored in this node.
     *
     * @param name the new name
     */
    @Override
    public void setDisplayName(String name) {
        this.name = name;
    }

    public int getChildCount() {
        return children.size();
    }

    public PropertyTreeNode getChildAt(int i) {
        return children.get(i);
    }

    public void add(PropertyTreeNode child) {
        children.add(child);
    }

    public void setLoc(Localization loc) {
        this.loc = loc;
    }
}
