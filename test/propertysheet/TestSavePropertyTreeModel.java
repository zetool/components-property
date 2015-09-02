package propertysheet;

import ds.PropertyTreeModelLoader;
import ds.PropertyTreeModelWriter;
import gui.propertysheet.BasicProperty;
import gui.propertysheet.JOptionsDialog;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringProperty;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestSavePropertyTreeModel {

    public enum PropertyEnum {
        EnumA, EnumB;
    }
    
    public enum PropertyEnumAlt {
        EnumA, EnumB;
    }
    
    
    
    public void testRead() throws IOException {
        PropertyTreeModelLoader loader = new PropertyTreeModelLoader();
        PropertyTreeModel ptm = loader.loadConfigFile(new File("./ptm.txt"));
        iterateNode(ptm.getRoot());
    }
    
    public void iterateNode(PropertyTreeNode node) {
        for( int i = 0; i < node.getChildCount(); ++i) {
            PropertyTreeNode childNode = node.getChildAt(i);
            System.out.println( "Child " + i + ": " + childNode.getDisplayName() + "(use as loc string: " + childNode.isUsedAsLocString() + ")");
            iterateNode(childNode);
        }
        for( BasicProperty bp : node.getProperties()) {
            String out = String.format("%s %s %s %s", bp.getValue(), bp.getShortDescription(), bp.getShortDescriptionTag(), bp.getDisplayName(), bp.getDisplayNameTag());
            System.out.println(out);
        }
    }
    

    @Test
    public void testSimple() throws IOException {
        PropertyTreeNode root = new PropertyTreeNode("my_root_name");
        PropertyTreeModel ptm = new PropertyTreeModel(root);
        root.setDisplayName("my_displayname");
        StringProperty sp = new StringProperty();
        sp.setValue("string_property_name");
        root.addProperty(sp);
        BooleanProperty bp1 = new BooleanProperty();
        root.addProperty(bp1);
        BooleanProperty bp2 = new BooleanProperty();
        bp2.setValue(true);
        root.addProperty(bp2);
        
        PropertyTreeNode child1 = new PropertyTreeNode("child1");
        root.add(child1);
        BooleanProperty cbp1 = new BooleanProperty();
        cbp1.setDisplayName("cbp1");
        cbp1.setShortDescription("cbp1-short");
        cbp1.setName("cbp1-name");
        root.addProperty(cbp1);
        child1.addProperty(cbp1);
        
        PropertyTreeNode child2 = new PropertyTreeNode("child2");
        child1.add(child2);
        
        BooleanProperty cbp2 = new BooleanProperty();
        cbp2.setDisplayName("cbp2");
        cbp2.setShortDescription("cbp2-short");
        cbp2.setName("cbp2-name");
        cbp2.setValue(true);
        child2.addProperty(cbp2);
        
        StringListProperty slp = new StringListProperty();
        slp.add("string1");
        slp.add("kpd");
        ArrayList<String> newStrings = new ArrayList<>();
        newStrings.add("newly created string");
        slp.setValue(newStrings);
        slp.setDisplayName("stringlist");
        slp.setShortDescription("stringshort");
        slp.setName("mystringlist");
        child2.addProperty(slp);
        
        EnumProperty ep = new EnumProperty();
        ep.setDisplayName("enum");
        ep.setShortDescription("enum-short");
        ep.setName("enum");
        ep.setValue(PropertyEnum.EnumA);
        child1.addProperty(ep);
        
        IntegerRangeProperty irp = new IntegerRangeProperty();
        irp.setMinValue(5);
        irp.setMaxValue(45);
        irp.setMinorTick(1);
        irp.setMajorTick(5);
        irp.setPropertyValue(20);
        irp.setName("irp");
        irp.setDisplayName("irp-dn");
        irp.setShortDescription("irp-short");
        child1.addProperty(irp);
        
        ColorProperty cp = new ColorProperty();
        cp.setDisplayName("mycolor");
        cp.setShortDescription("color-desc");
        cp.setName("mycolor-name");
        cp.setValue(Color.RED);
        child1.addProperty(cp);        

        PropertyTreeModelWriter writer = new PropertyTreeModelWriter();
        writer.saveConfigFile(ptm, new File("./ptm.txt"));
        
        JOptionsDialog d = new JOptionsDialog(ptm);
        d.setVisible(true);
    }
    
    public static void main(String[] args) {
        PropertyTreeNode root = new PropertyTreeNode("my_root_name");
        PropertyTreeModel ptm = new PropertyTreeModel(root);
        root.setDisplayName("my_displayname");
        StringProperty sp = new StringProperty();
        sp.setValue("string_property_name");
        root.addProperty(sp);
        BooleanProperty bp1 = new BooleanProperty();
        root.addProperty(bp1);
        BooleanProperty bp2 = new BooleanProperty();
        bp2.setValue(true);
        root.addProperty(bp2);
        
        PropertyTreeNode child1 = new PropertyTreeNode("child1");
        root.add(child1);
        BooleanProperty cbp1 = new BooleanProperty();
        cbp1.setDisplayName("cbp1");
        cbp1.setShortDescription("cbp1-short");
        cbp1.setName("cbp1-name");
        root.addProperty(cbp1);
        child1.addProperty(cbp1);
        
        PropertyTreeNode child2 = new PropertyTreeNode("child2");
        child1.add(child2);
        
        BooleanProperty cbp2 = new BooleanProperty();
        cbp2.setDisplayName("cbp2");
        cbp2.setShortDescription("cbp2-short");
        cbp2.setName("cbp2-name");
        cbp2.setValue(true);
        child2.addProperty(cbp2);
        
        StringListProperty slp = new StringListProperty();
        slp.add("string1");
        slp.add("kpd");
        ArrayList<String> newStrings = new ArrayList<>();
        newStrings.add("newly created string");
        newStrings.add("a second string");
        slp.setValue(newStrings);
        slp.setDisplayName("stringlist");
        slp.setShortDescription("stringshort");
        slp.setName("mystringlist");
        child2.addProperty(slp);
        
        EnumProperty ep = new EnumProperty();
        ep.setDisplayName("enum");
        ep.setShortDescription("enum-short");
        ep.setName("enum");
        ep.setValue(PropertyEnum.EnumA);
        child1.addProperty(ep);
        
        IntegerRangeProperty irp = new IntegerRangeProperty();
        irp.setMinValue(5);
        irp.setMaxValue(45);
        irp.setMinorTick(1);
        irp.setMajorTick(5);
        irp.setPropertyValue(20);
        irp.setName("irp");
        irp.setDisplayName("irp-dn");
        irp.setShortDescription("irp-short");
        child1.addProperty(irp);
        
        ColorProperty cp = new ColorProperty();
        cp.setDisplayName("mycolor");
        cp.setShortDescription("color-desc");
        cp.setName("mycolor-name");
        cp.setValue(Color.RED);
        child1.addProperty(cp);

        PropertyTreeNode child3 = new PropertyTreeNode("child3");
        root.add(child3);
        BooleanProperty cbp3 = new BooleanProperty();
        cbp3.setDisplayName("cbp3");
        cbp3.setShortDescription("cbp3-short");
        cbp3.setName("cbp3-name");
        root.addProperty(cbp3);
        child3.addProperty(cbp3);

        
        JOptionsDialog d = new JOptionsDialog(ptm);
        d.setVisible(true);
    }
}
