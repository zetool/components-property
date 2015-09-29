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
package gui.propertysheet;

import java.util.Objects;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Martin Groß
 * @author Jan-Philipp Kappmeier
 */
public class PropertyTreeModel {
    private String propertyName = "";
    private final PropertyTreeNode root;

    public PropertyTreeModel(PropertyTreeNode root) {
        this.root = Objects.requireNonNull(root);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = Objects.requireNonNull(propertyName);
    }

    public PropertyTreeNode getRoot() {
        return root;
    }

    public void setLoc(Localization loc) {
        setLocRecursive(root, loc);
    }
    
    private void setLocRecursive(PropertyTreeNode node, Localization loc) {
        System.out.println("Setting for node " + node.getDisplayNameTag());
        node.setLoc(loc);
        for( PropertyTreeNode child : node.children) {
            setLocRecursive(child, loc);
        }
        for(GenericProperty p : node.properties) {
            p.setLoc(loc);
        }
    }
}
