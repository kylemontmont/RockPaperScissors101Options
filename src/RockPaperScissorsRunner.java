
import java.io.IOException;
import java.util.Scanner;


public class RockPaperScissorsRunner {

	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);
		RockPaperScissors obj = new RockPaperScissors();
		
		
		for(int i = 0; i < 101; i++)
			if(i % 10 == 0 && i != 0 && i != 100)
				System.out.println(obj.returnOptions()[i] + ", ");
			else if(i == 100)
				System.out.println(obj.returnOptions()[i]);
			else
				System.out.print(obj.returnOptions()[i] + ", ");
		
		System.out.print("Player Choice: ");
		obj.setPlayer(scan.nextLine());
		
		obj.randomComputer();
		
		
		//obj.convertSourceTxtsToArrays(); //uncommnet to input new winning combos via txt files
		//obj.inputWinningCombos(); //uncomment to input new winning combos via console
		obj.loadWinningCombos();
		
		
		obj.findWinner();
		
		System.out.println(obj.returnMsg());
		
	}

}
