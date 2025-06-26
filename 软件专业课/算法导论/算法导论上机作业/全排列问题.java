import java.util.*;

public class Ann {
    private static int sum=0;
    private static List<List<Integer>> dp=new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        ArrayList<Integer> arr = new ArrayList<>();
        Arrays.stream(s.split(" ")).forEach(str->arr.add(Integer.parseInt(str)));
        Collections.sort(arr);
        LinkedHashSet<ArrayList<Integer>> hashSet = new LinkedHashSet<>();
        ArrayList<Integer> arr1 = new ArrayList<>();
        find(arr,hashSet,arr1);
        System.out.println(dp);
    }
    public static void find(ArrayList<Integer> arr, LinkedHashSet<ArrayList<Integer>> hashSet,ArrayList<Integer> temp) {
        for(int i=0;i<arr.size();i++)
        {
            ArrayList<Integer> newArr = new ArrayList<>(arr);
            temp.add(arr.get(i));
            newArr.remove(i);
            if(hashSet.contains(temp)) {temp.remove(temp.size()-1);continue;}
            hashSet.add(temp);
            find(newArr,hashSet,temp);
            if(newArr.isEmpty())
            {
                dp.add(new ArrayList<>(temp));
                sum++;
            }
            temp.remove(temp.size()-1);
            //hashSet.removeLast();
        }
    }
}
