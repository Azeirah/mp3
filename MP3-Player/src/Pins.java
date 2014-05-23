
public enum Pins{
	RDA(94),
	RDB(95),
	DrukknopL(84),
	DrukknopR(85),
	LCDA0(81),
	LCDSCL(80),
	LCDSI(60),
	LCDRES(59),
	LCDCSA(58),
	Pin16(57),
	Pin17(54),
	Pin18(43),
	Pin19(42),
	Pin20(41),
	Pin21(39),
	Pin22(38);
	
	private int number;
	
	Pins(int number){
		this.number = number;
	}
	
	public int getNumber(){
		return number;
	}
}
