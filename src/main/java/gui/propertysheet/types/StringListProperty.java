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
package gui.propertysheet.types;

import gui.propertysheet.BasicProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class StringListProperty extends BasicProperty<List<String>> implements Iterable<String> {
    private final List<String> list = new ArrayList<>();

    public void add(String string) {
        list.add(string);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(String o) {
        return list.contains(o);
    }

    public boolean remove(String o) {
        return list.remove(o);
    }

    public void clear() {
        list.clear();
    }

    public boolean addAll(Collection<? extends String> c) {
        return list.addAll(c);
    }

    public boolean removeAll(Collection<String> c) {
        return list.removeAll(c);
    }

    @Override
    public void setValue(Object strings) {
        if(strings instanceof Collection) {
            clear();
            ((Collection<?>) strings).stream().filter(object -> object instanceof String).forEach(stringObject ->
                    add((String) stringObject));
        } else {
            throw new IllegalArgumentException("StringListProperty stores Collection, but got : " + strings.getClass());
        }
    }

    @Override
    public List<String> getValue() {
        return list;
    }

    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (this.getClass() != obj.getClass() ) {
          return false;
        }
        return equalsMainProperties((StringListProperty)obj);
    }

    @Override
    public int hashCode() {
        return 19 * super.hashCode();
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }}
