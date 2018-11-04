import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sarah Amick
 */
public class Main {
    public static void main(String[] args) {

        int[][] transactions1 = new int[][] { { 1, 2, 3, 4, 5 }, { 1, 3, 5 }, { 2, 3, 5 }, { 1, 5 }, { 1, 3, 4 }, { 2, 3, 5 }, { 2, 3, 5 },
                { 3, 4, 5 }, { 4, 5 }, { 2 }, { 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };
        int[][] transactions2 = new int[][] { { 1, 2, 5 }, {2, 4}, { 2, 3 }, { 1, 2, 4 }, { 1, 3 }, { 2, 3 }, { 1, 3 },
                { 1, 2, 3, 5 }, { 1, 2, 3 }};

        Apriori apriori = new Apriori(transactions2, 2);
        List<ItemSet> frequentItemSets = apriori.start();

        for(ItemSet item: frequentItemSets){
            System.out.println("Set of length " + item.set.length + ":");
            String s = "";
            for(int i = 0; i < item.set.length; i++){
              if(i == item.set.length-1) s += item.set[i];
              else s += item.set[i] + ", ";
            }
            System.out.println(s);
        }
    }
}
