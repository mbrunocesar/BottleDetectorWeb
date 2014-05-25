package tests;

import static org.junit.Assert.*;
import global.Path;

import org.junit.Before;
import org.junit.Test;

import controllers.Commander;

public class ExecutorTest {

	String[] image = new String[1];
	String [][] results;
	Commander c1;


	@Before
	public void setUp() throws Exception {
		Path.thisPath = "WebContent/";
	}

	@Test
	public void testBigBlock() {
		image = new String[17];

		image[0] = "http://i.walmartimages.com/i/p/00/07/14/64/20/0007146420060_500X500.jpg";
		image[1] = "http://www.fruit.barakelfoods.com/wp-content/uploads/2011/04/CarrotJuicebg.jpg";
		image[2] = "http://akimages.shoplocal.com/dyn_li/600.600.90.0/Retailers/Aldi/130213HB_S_43212A_BHF_MangoJuiceSmoothie.JPG";
		image[3] = "http://1.bp.blogspot.com/-WpMGAnyttJ0/T252yLSXlfI/AAAAAAAACjs/eka8I8x_YBI/s1600/Bolthouse.jpg";
		image[4] = "http://distilleryimage1.s3.amazonaws.com/6035157e612c11e393a3120e7f1e83f1_6.jpg";
		image[5] = "http://distilleryimage7.s3.amazonaws.com/1917b4e0606d11e38c200e1bc264082d_6.jpg";
		image[6] = "http://distilleryimage1.s3.amazonaws.com/f80ea550604f11e3ae5e125279676ed1_6.jpg";
		image[7] = "http://2.bp.blogspot.com/-atTUfx2FZ4w/TzSf4dVR-OI/AAAAAAAABBg/bI9pKcE1nVc/s1600/IMG_1900.JPG";
		image[8] = "http://1.bp.blogspot.com/-w0l6NuIf5f4/UAdrhrj9LgI/AAAAAAAAAF0/XhtznsdLgq4/s1600/Bolthouse.jpg";
		
		image[9] = "https://www.google.com.br/images/srpr/logo4w.png";
		image[10] = "http://www.gameguru.in/images/chess-ubisoft-games.jpg";
		image[11] = "http://www.haaretz.com/polopoly_fs/1.422400.1333468580!/image/2115027736.jpg_gen/derivatives/landscape_640/2115027736.jpg";
		image[12] = "http://blogs.estadao.com.br/radar-pop/files/2013/02/coke34.jpg";
		image[13] = "http://www.megag.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/g/u/guarana_antarctica_lata.jpg";
		image[14] = "http://www.paraisodosushi.com.br/vitrine/fotosproduto/945/201381621543-guarana-antarctica.jpg";
		image[15] = "http://www.packworld.com/sites/default/files/images/issues/08_09/Naked.jpg";
		image[16] = "http://www.nakedjuice.com/resources/managed/products/productshots/orange-juice.png";
		
		c1 = new Commander(image); results = c1.getResults();

		for(int i=0; i<9; i++){
			assertTrue(results[i][0]=="1");
		}

		for(int i=9; i<17; i++){
			assertTrue(results[i][0]=="11");
		}
	}

	public void testConclusiveAndTrue() {
		System.out.println("---TRUES TESTS---");

		image[0] = "http://i.walmartimages.com/i/p/00/07/14/64/20/0007146420060_500X500.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://www.fruit.barakelfoods.com/wp-content/uploads/2011/04/CarrotJuicebg.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://akimages.shoplocal.com/dyn_li/600.600.90.0/Retailers/Aldi/130213HB_S_43212A_BHF_MangoJuiceSmoothie.JPG";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://1.bp.blogspot.com/-WpMGAnyttJ0/T252yLSXlfI/AAAAAAAACjs/eka8I8x_YBI/s1600/Bolthouse.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://distilleryimage1.s3.amazonaws.com/6035157e612c11e393a3120e7f1e83f1_6.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://distilleryimage7.s3.amazonaws.com/1917b4e0606d11e38c200e1bc264082d_6.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://distilleryimage1.s3.amazonaws.com/f80ea550604f11e3ae5e125279676ed1_6.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://2.bp.blogspot.com/-atTUfx2FZ4w/TzSf4dVR-OI/AAAAAAAABBg/bI9pKcE1nVc/s1600/IMG_1900.JPG";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

		image[0] = "http://1.bp.blogspot.com/-w0l6NuIf5f4/UAdrhrj9LgI/AAAAAAAAAF0/XhtznsdLgq4/s1600/Bolthouse.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

	}

	public void testConclusiveAndFalse() {
		System.out.println("---FALSES TESTS---");

		image[0] = "https://www.google.com.br/images/srpr/logo4w.png";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.gameguru.in/images/chess-ubisoft-games.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.haaretz.com/polopoly_fs/1.422400.1333468580!/image/2115027736.jpg_gen/derivatives/landscape_640/2115027736.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://blogs.estadao.com.br/radar-pop/files/2013/02/coke34.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.megag.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/g/u/guarana_antarctica_lata.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.paraisodosushi.com.br/vitrine/fotosproduto/945/201381621543-guarana-antarctica.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.packworld.com/sites/default/files/images/issues/08_09/Naked.jpg";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

		image[0] = "http://www.nakedjuice.com/resources/managed/products/productshots/orange-juice.png";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="11");

	}


	@Test
	public void testInconclusive() {
		System.out.println("---INCONCLUSIVES---");
		//for(String pieces:results[0]){ System.out.println(pieces); }

		//image[0] = "http://www.thedieline.com/resource/BolthouseJuices.jpg?fileId=18326559";
		//c1 = new Commander(image); results = c1.getResults();
		//assertTrue(results[0][0]=="1");

		image[0] = "http://bhstorage.blob.core.windows.net/bolthouse/products/mastheads_d_beverages_31_450ml_Daily-Greens_png_cffa26f1-2481-4a17-9e6e-edbf3146d1af.png";
		c1 = new Commander(image); results = c1.getResults();
		assertTrue(results[0][0]=="1");

	}

	public void testJustProcess() {
		System.out.println("---INCONCLUSIVES---");
		//for(String pieces:results[0]){ System.out.println(pieces); }

		//image[0] = "http://www.thedieline.com/resource/BolthouseJuices.jpg?fileId=18326559";
		//c1 = new Commander(image); results = c1.getResults();
		//assertTrue(results[0][0]=="1");

		image[0] = "http://localhost/BottleDetectorWeb/images/middlers/1386476232571.png";
		c1 = new Commander(image); results = c1.getResults();

		image[0] = "http://localhost/BottleDetectorWeb/images/middlers/1386476238393.png";
		c1 = new Commander(image); results = c1.getResults();

		image[0] = "http://localhost/BottleDetectorWeb/images/middlers/1386476242097.png";
		c1 = new Commander(image); results = c1.getResults();

		image[0] = "http://localhost/BottleDetectorWeb/images/middlers/1386476246145.png";
		c1 = new Commander(image); results = c1.getResults();

		image[0] = "http://localhost/BottleDetectorWeb/images/middlers/1386476250698.png";
		c1 = new Commander(image); results = c1.getResults();

	}


}
