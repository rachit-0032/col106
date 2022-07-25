import java.util.Random;

public class Tester_Random {
    public static void main(String[] args){
        MMBurgers mm = new MMBurgers();
        System.out.println("\n--Started simulation--");

        // Set number of counters and griddle capacity
        try{
            mm.setK(3);
            mm.setM(10000);
        }
        catch(IllegalNumberException e){
            System.out.println(e);
        }
        int num = 100000;
        System.out.println(num);
        Random rand = new Random();
        rand.setSeed(1234);
        try 
        {
            int a = 0;
            int b = 100000;
            int i =1;
            for (i = 1;i<=num;i++){
              if (a==b)
                  break;
              int x = Math.abs(rand.nextInt());
              x = x%(b-a)+a;
              mm.arriveCustomer(i,x,1);
              a=x;
            }
            mm.advanceTime(Integer.MAX_VALUE);
            num = i-1;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // End of simulation
        System.out.println("\n--End of simulation--");
        mm.isEmpty();

        // Query wait times
        try{
            for(int i=1;i<=num;i++)
                System.out.println(mm.customerWaitTime(i));
//            System.out.println(mm.customerWaitTime(2));
//            System.out.println(mm.customerWaitTime(3));
//            System.out.println(mm.customerWaitTime(4));
            System.out.println(mm.avgWaitTime());
        }
        catch(IllegalNumberException e){
            System.out.println(e);
        }

    }
}