import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

//http://www.umop.com/rps101/alloutcomes.htm

public class RockPaperScissors {
	
	private String winner, player, computer;
	private int playerNum, computerNum;
	
	private final int NUMOPTIONS = 101;
	private String[] options = new String[NUMOPTIONS];
	private Boolean[][] blankBeats = new Boolean[NUMOPTIONS][NUMOPTIONS];
	
	public RockPaperScissors() throws IOException{ //default constructor
		readInOptions();
	}
	
	
	//players stuff
	public void setPlayer(String player){ //sets players option		
		playerNum = indexOfOption(player);
		this.player = options[playerNum];
	}

	public void randomComputer(){ //pick random option for computer
		
		Random rand = new Random();
		
		computerNum = rand.nextInt(NUMOPTIONS);
		computer = options[computerNum];
		
	}

	
	//options stuff
	public int indexOfOption(String s){ //get number representation of option
		int num = -1;
		
		for(int i = 0; i < NUMOPTIONS; i++)
			if(s.equalsIgnoreCase(options[i]))
				num = i;
		if(num == -1)
			System.out.println(s + " not found");
		
		return num;
	}

	public void readInOptions() throws IOException{ //read in all the options
		FileReader read = new FileReader("src/Options.txt");
		BufferedReader buff = new BufferedReader(read);
		for(int i = 0; i < NUMOPTIONS; i++)
			options[i] = buff.readLine();
		read.close();
		buff.close();
	}
	
	public String[] returnOptions(){ //return options
		return options;
	}
	
	
	//winners array
	public void convertSourceTxtsToArrays() throws IOException{ //read in the source txts and put them in an array for large rock paper scissors

		String currentln = null;
		
		for(int i = 0; i < NUMOPTIONS; i++){	//read thought all the files
			FileReader read = new FileReader("src/SourceCombos/" + options[i] +".txt");
			BufferedReader buff = new BufferedReader(read);
			
			for(int j = 0; j < 50; j++){	//read though all the lines
				currentln = buff.readLine().toLowerCase();
				
				for(int e = 0; e < NUMOPTIONS; e++)		//check what option the line is for
					if(currentln.contains(" " + options[e]))
						currentln = options[e];
				
				blankBeats[i][indexOfOption(currentln)] = true;
				blankBeats[indexOfOption(currentln)][i] = false;
			
			}
			read.close();
			buff.close();
		}
		
		for(int i = 0; i < NUMOPTIONS; i++)
			blankBeats[i][i] = null;
		
		saveWinningCombos();
		
		}		

	public void inputWinningCombos() throws IOException{ //for smaller versions of rock paper scissors
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Are you sure you want to overwrite the winning combos? ");
		if(scan.next().equalsIgnoreCase("yes")){
		
		//ask questions to fill out array
		for(int i = 0; i < NUMOPTIONS; i++)
			for(int j = 0; j < NUMOPTIONS; j++)
				if((blankBeats[i][j] == null || blankBeats[j][i] == null) && i != j){
					System.out.print(options[i] + " beats " + options[j] + "? ");
					if(scan.nextInt() == 0){
						blankBeats[i][j] = false;
						blankBeats[j][i] = true;
					}else{
						blankBeats[i][j] = true;
						blankBeats[j][i] = false;
						}
					}
		
		saveWinningCombos();
			
		}
	}
	
	public void saveWinningCombos() throws IOException{ //save winning combos to txt files
		for(int i = 0; i < NUMOPTIONS; i++){
			FileWriter write = new FileWriter("src/WinningCombos/" + String.valueOf(i) +".txt");
			PrintWriter print = new PrintWriter(write);
			for(int j = 0; j < NUMOPTIONS; j++)
				print.println(String.valueOf(blankBeats[i][j]));
			write.close();
			print.close();
		}			
	}
	
	public void loadWinningCombos() throws IOException{ //read in winning combos from text files to array
		
		for(int i = 0; i < NUMOPTIONS; i++){
			FileReader read = new FileReader("src/WinningCombos/" + String.valueOf(i) +".txt");
			BufferedReader buff = new BufferedReader(read);
			for(int j = 0; j < NUMOPTIONS; j++)
				blankBeats[i][j] = Boolean.valueOf(buff.readLine());
			read.close();
			buff.close();
		}			
				
	}
	
	
	//returning winner stuff
	public void findWinner(){
		if(playerNum == computerNum)
			winner = "draw";
		else if(blankBeats[playerNum][computerNum])
			winner = "player";
		else if(blankBeats[computerNum][playerNum])
			winner = "computer";
	}
	
	public String returnMsg(){
		if(winner.equals("player"))
			return player + " beats " + computer + ", player wins!";
		else if(winner.equals("computer"))
			return computer + " beats " + player + ", computer wins!";
		else
			return player + " vs " + computer + ", draw!";
	}
	
	
	
	
	
	
	
	
	
}
