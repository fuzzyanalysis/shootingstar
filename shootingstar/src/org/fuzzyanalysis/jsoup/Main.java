package org.fuzzyanalysis.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.function.Sqrt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String searchTerm = "Dildo";
		searchTerm = searchTerm.replace(" ", "%20");
		getEbayPriceStatistics(searchTerm);
		//getGumtreePriceStatistics(searchTerm);
		getAmazonPriceStatistics(searchTerm);
		//getGoogleShoppingPriceStatistics(searchTerm);
		getShoppingSquarePriceStatistics(searchTerm);
		//getNextTagPriceStatistics();
		getPriceGrabberPriceStatistics(searchTerm);
		
		
		
	}
	
	public static void getShoppingSquarePriceStatistics(String searchTerm){
		//https://www.google.com/search?hl=en&tbm=shop&q=9780471998037
		
		Document squareDoc = null;
		try {
			squareDoc = Jsoup.connect(
				"http://www.shoppingsquare.com.au/search.php?view=list&q=" + 
				searchTerm).get();
			
			Elements squarePriceElements = squareDoc.select("span.price");			
			
			System.out.println("SHOPPING SQUARE");
			getStatistics(squarePriceElements);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//http://www.amazon.com/s/ref=nb_sb_noss/182-6218273-9600557?url=search-alias%3Daps&field-keywords=9781933988023
	
	public static void getAmazonPriceStatistics(String searchTerm){
		Document amazonDoc = null;
		try {
			amazonDoc = Jsoup.connect(
				"http://www.amazon.com/gp/offer-listing/1933988029/ref=sr_1_1_olp?ie=UTF8&qid=1383429268&sr=8-1&keywords=" + 			
				searchTerm + 
				"&condition=used").get();
			
			Elements amazonPriceElements = amazonDoc.select("span.a-size-large.a-color-price.olpOfferPrice.a-text-bold");
			
			System.out.println("AMAZON");
			getStatistics(amazonPriceElements);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void getAllClassifiedsPriceStatistics(String searchTerm){
		
	}
	
	public static void getPriceGrabberPriceStatistics(String searchTerm){
		//http://www.pricegrabber.com/dildo-products/?form_keyword=Dildo
		
		Document pgDoc = null;
		try {
			pgDoc = Jsoup.connect(
				"http://www.pricegrabber.com/dildo-products/?form_keyword=" +
				searchTerm + 
				"&layout=list").get();
			
			Elements pgPriceElements = pgDoc.select("p a");			
			
			System.out.println("PRICE GRABBER");
			getStatistics(pgPriceElements);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
				
//	public static void getGumtreePriceStatistics(String searchTerm) {
//		
//		Document gumtreeDoc = null;
//		try {
//			searchTerm = searchTerm.replace("%20",  "+");
//			gumtreeDoc = Jsoup.connect(
//				"http://www.gumtree.com.au/s-" +
//				searchTerm + 
//				"/k0?sort=price_asc").get();
//			
//			Elements gumtreePriceElements = gumtreeDoc.select("#left_container > div.eq(1) > table > tbody > tr:eq(1) > td.eq(4)");		
//			
//			//*[@id="left_container"]/div[1]/table/tbody/tr[1]/td[4]
//			
//			System.out.println("GUMTREE");
//			getStatistics(gumtreePriceElements);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//	}

	
	
	public static void getEbayPriceStatistics(String searchTerm) {
		
		Document ebayDoc = null;
		try {
			ebayDoc = Jsoup.connect(
				"http://www.ebay.com.au/sch/i.html?_sacat=0&_from=R40&_nkw=" + 
				searchTerm + 
				"&rt=nc&_dmd=1").get();
			
			Elements ebayPriceElements = ebayDoc.getElementsByClass("g-b");			
			
			System.out.println("EBAY");
			getStatistics(ebayPriceElements);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getStatistics(Elements elements){

		float totalPrices = 0;
		float lowestPrice = -1;
		float highestPrice = -1;
		int numberOfItems = elements.size();
		List<Float> priceList = new ArrayList<Float>();
		
		for(Element price: elements){
			String priceText = 
					price.text().
					replace("$",  "").
					replace(",", "").
					replace("AU", "").					
					replace("DEAL: ", "").
					replace("FREE", "0").
					replace(" ", "");
			if(!"".equals(priceText)){
				Float currPrice = (float) 0;
				try{
					currPrice = Float.parseFloat(priceText);
				} catch (Exception ex){
					continue;
				}
				priceList.add(currPrice);
				totalPrices += currPrice;	
				if(lowestPrice==-1 || currPrice<lowestPrice){
					lowestPrice = currPrice;
				}
				if(highestPrice==-1 || currPrice>highestPrice){
					highestPrice = currPrice;
				}
			}				
		}

		int mean = (int) (totalPrices/numberOfItems);
		
		// calculate deviations
		List<Float> deviations = new ArrayList<Float>();
		for(float price: priceList){
			float deviation = price - mean;
			deviations.add(deviation);				
		}
		
		//calculate standard deviation (sample and population)
		float sumOfDeviations = (float) 0;
		for(Float deviation: deviations){
			sumOfDeviations += deviation;
		}
		float standardDeviation = (float) Math.sqrt(sumOfDeviations/(numberOfItems - 1));
		float populationStandardDeviation = (float) Math.sqrt(sumOfDeviations/numberOfItems);
		
		System.out.println("================================");
		System.out.println("Highest price: " + highestPrice);
		System.out.println("Lowest price: " + lowestPrice);
		System.out.println("Average price: " + mean);
		System.out.println("Standard Deviation: " + standardDeviation);
		System.out.println("Population Standard Deviation: " + populationStandardDeviation);
		System.out.println("================================");
	}
}
