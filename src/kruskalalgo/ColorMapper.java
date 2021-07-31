
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ColorMapper {

    public static void main(String[] args) {
        HashMap<String, Color> colorMapper = new HashMap<>();
        colorMapper.put("Anna", Color.RED);
        colorMapper.put("Joan", Color.RED);
        colorMapper.put("Joe", Color.GREEN);
        colorMapper.put("Rick", Color.GREEN);
        colorMapper.put("Mart", Color.BLUE);
        colorMapper.put("Josh", Color.BLACK);
        colorMapper.put("Jess", Color.CYAN);
        colorMapper.put("Andy", Color.PINK);
        colorMapper.put("All", Color.PINK);
        
        Iterator iter = colorMapper.entrySet().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        colorMapper.put("Andy", Color.CYAN);
        System.out.println("After updating Andy to CYAN:");
        iter = colorMapper.entrySet().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        
        System.out.println("After removing all colors except RED, GREEN, and BLUE:");
        iter = colorMapper.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry currentElement = (Map.Entry)iter.next();
            if(currentElement.getValue()!=Color.RED && currentElement.getValue()!= Color.BLUE && currentElement.getValue()!=Color.GREEN){
                colorMapper.remove(currentElement.getKey());
                iter = colorMapper.entrySet().iterator();
            }
        }
        
        
        iter = colorMapper.entrySet().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
    
}
