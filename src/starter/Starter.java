package starter;

import global.Path;
import controllers.Commander;

public class Starter {

	Commander commander;

	public static void main(String[] args){
		Path.thisPath = "WebContent/";
		//Logger.setAsDebugger();
		
		String[] images = new String[8];
		images[0] = "http://i.walmartimages.com/i/p/00/07/14/64/20/0007146420060_500X500.jpg";
		images[1] = "http://akimages.shoplocal.com/dyn_li/600.600.90.0/Retailers/Aldi/130213HB_S_43212A_BHF_MangoJuiceSmoothie.JPG";
		images[2] = "http://2.bp.blogspot.com/-atTUfx2FZ4w/TzSf4dVR-OI/AAAAAAAABBg/bI9pKcE1nVc/s1600/IMG_1900.JPG";
		images[3] = "http://www.thegrocerygirls.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/Y/M/YMIXL.jpg.jpg";
		images[4] = "http://akimages.shoplocal.com/dyn_li/600.600.90.0/Retailers/Aldi/130213HB_S_43212A_BHF_MangoJuiceSmoothie.JPG";
		images[5] = "http://karasdealsandsteals.com/wp-content/uploads/2012/06/bolthouse-farms.jpg";
		images[6] = "http://www.megag.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/g/u/guarana_antarctica_lata.jpg";
		images[7] = "images/bases/input-base-1Y.png";
		
		/*
			images = new String[13];			
			images[0] = "http://i.walmartimages.com/i/p/00/07/14/64/20/0007146420060_500X500.jpg";
			images[1] = "http://www.fruit.barakelfoods.com/wp-content/uploads/2011/04/CarrotJuicebg.jpg";
			images[2] = "http://akimages.shoplocal.com/dyn_li/600.600.90.0/Retailers/Aldi/130213HB_S_43212A_BHF_MangoJuiceSmoothie.JPG";
			images[3] = "http://buzzaurus.com/wp-content/uploads/2011/07/bolthouse_carrot_juice.jpg";
			images[4] = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcScxevyznvL-rF3ThxpoS5Zl_Aupc1Y1V4G9mS1yxZ6HNt9H_Dv";
			images[5] = "http://1.bp.blogspot.com/-XBGCzHV68ts/Tym2yabUY4I/AAAAAAAAAgU/N79eoOWTYNE/s1600/bolthouse.jpg";
			images[6] = "http://i1.ytimg.com/vi/0phvYxMUJ4M/hqdefault.jpg?feature=og";
			images[7] = "http://2.bp.blogspot.com/_Qq2Qa1xyoco/TIGsk4ZHNtI/AAAAAAAABkQ/YKVyK4_CF2E/s200/laranja.jpg";
			images[8] = "http://4.bp.blogspot.com/-5fy-_8IIXD0/UOMiIieTVBI/AAAAAAAAHng/tTPrHa0fi3k/s1600/coca_cola.jpg";
			images[9] = "http://nerdsocialista.files.wordpress.com/2012/12/o-senhor-dos-aneis-duas-torres-2107.jpg";
			images[10] = "https://www.google.com.br/images/srpr/logo4w.png";
			images[11] = "http://1.bp.blogspot.com/_na40rdYjiB8/TPkL12hlm7I/AAAAAAAAAAQ/tnlGwqOc9a0/s640/carros-tunados2.jpg";
			images[12] = "http://pipocamoderna.com.br/wp-content/uploads/2011/10/Leandra_leal.jpg";

			images = new String[14];			
			images[0] = "images/traditional-targets/1381113146956.jpg";
			images[1] = "images/traditional-targets/1381113153212.jpg";
			images[2] = "images/traditional-targets/1381113155243.jpg";
			images[3] = "images/traditional-targets/1381113160969.jpg";
			images[4] = "images/traditional-targets/1381113165338.jpg";
			images[5] = "images/traditional-targets/1381113168464.jpg";
			images[6] = "images/traditional-targets/1381113171426.jpg";
			images[7] = "images/traditional-targets/1381113173493.jpg";
			images[8] = "images/traditional-targets/1381113176370.jpg";
			images[9] = "images/traditional-targets/1381113179425.jpg";
			images[10] = "images/traditional-targets/1381113182888.jpg";
			images[11] = "images/traditional-targets/1381113184506.jpg";
			images[12] = "images/traditional-targets/1381113187165.jpg";
			images[13] = "images/traditional-targets/z_bhf_logo.jpg";
		 */

		// String[] images = Parser.readFromLink(null);
		new Starter(images);
	}


	public Starter(String[] images){
		commander = new Commander(images);

		printResults();
	}


	public String[][] showResults() {
		return commander.getResults();
	}
	
	public void printResults() {
		String[][] results = commander.getResults();
		
		for (int i = 0; i<results.length; i++) {
			if (results[i][0] == "1") {
				System.out.println("Garrafa Valida!");
			} else if (results[i][0] == "11") {
				System.out.println("Garrafa Invalida.");
			} else if (results[i][0] == "10") {
				System.out.println("Falha ao carregar..");
			}  
		}
		
	}

}
