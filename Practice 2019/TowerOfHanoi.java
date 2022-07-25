// import pkg1.MyStack;
import java.io.*;

public class TowerOfHanoi extends MyStack{
    
    public static void  toh_with_recursion(int num_disks, int start_pos, int end_pos){
        //Base Case | For 1 disk:
        if (num_disks == 1){
            //Since there is only one disk to be transferred to the destination rod, the solution is trivial
            System.out.println(start_pos + " " + end_pos);
            return ;
        }

        /* Case of 2 disks:
            You send (n-1) = 1 disk to the extra rod that we have been given.
            Then we have only 1 disk in the start_pos rod which is the trivial case.   
        */
        
        /* Case of 3 disks:
            For a recursive solution, we need to break down the problem into smaller sub-problems and solve them.
            We first solve the problem of pushing two disks onto the extra rod from the 2 disk problem
            Then, we use the 1-disk problem to move this disk to the destination rod.
            Then, we only need to move back those 2 disks, using the 2-disk approach, to the actual destination rod    
        */

        //Getting the other rod that is available apart frmo start_pos and end_pos
        int other_pos = 0;
        if(start_pos + end_pos == 5)
            other_pos = Math.abs(end_pos - start_pos);
        else if(start_pos + end_pos == 4)
            other_pos = Math.abs(end_pos - start_pos);
        else if(start_pos + end_pos == 3)
            other_pos = Math.abs(end_pos + start_pos);
        
        //Moving the first n-1 disks onto the other_pos rod
        toh_with_recursion(num_disks-1, start_pos, other_pos);
        
        //Moving the n^{th} disk onto the end_pos rod
        System.out.println(start_pos + " " + end_pos);
        
        //Moving the n-1 disks from other_pos rod to end_pos rod
        toh_with_recursion(num_disks-1, other_pos, end_pos);
    }

    public static void  toh_without_recursion(int num_disks, int start_pos, int end_pos){
        //Non-Recursive, iterative approach

        //Stacks:
        
    }

    public static void main(String[] args) {
        // toh_with_recursion(2, 1, 3);
        // toh_without_recursion(num_disks, start_pos, end_pos);
        MyStack <Integer> m = new MyStack <Integer> ();
        m.push(5);
        
    }

}
