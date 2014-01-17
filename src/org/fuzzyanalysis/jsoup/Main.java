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
		
		String searchTerm = "9781933988405";
		
		searchTerm = searchTerm.replace(" ", "%20");
		
		getEbayPriceStatistics(searchTerm);
		//getGumtreePriceStatistics(searchTerm);
		getAmazonPriceStatistics(searchTerm);
		//getGoogleShoppingPriceStatistics(searchTerm);
		getShoppingSquarePriceStatistics(searchTerm);
		//getNextTagPriceStatistics();
		getPriceGrabberPriceStatistics(searchTerm);
		
		getDealsDirectPriceStatistics(searchTerm);
		getGumtreePriceStatistics(searchTerm);
		
		
	}
	
	private static void getDealsDirectPriceStatistics(String searchTerm) {
		
		Shot dealsDirectShot = new Shot(
				"DEALS DIRECT",
				null,
				"http://www.shoppingsquare.com.au/search.php?view=list&q="+searchTerm);
		
		dealsDirectShot.setElements(dealsDirectShot.getDocument().select("span itemprop.price"));					
		dealsDirectShot.getStatistics();
		
	}

	public static void getShoppingSquarePriceStatistics(String searchTerm){
		//https://www.google.com/search?hl=en&tbm=shop&q=9780471998037
		
		
		Shot squareShot = new Shot(
				"SHOPPING SQUARE",
				null,
				"http://www.shoppingsquare.com.au/search.php?view=list&q="+searchTerm);
		
		squareShot.setElements(squareShot.getDocument().select("span.price"));					
		squareShot.getStatistics();
	}

	//http://www.amazon.com/s/ref=nb_sb_noss/182-6218273-9600557?url=search-alias%3Daps&field-keywords=9781933988023
	
	public static void getAmazonPriceStatistics(String searchTerm){

		Shot amazonShot = new Shot(
				"AMAZON",
				null,
				"http://www.amazon.com/gp/offer-listing/1933988029/ref=sr_1_1_olp?ie=UTF8&qid=1383429268&sr=8-1&keywords=" + 			
				searchTerm + 
				"&condition=used");
		
		amazonShot.setElements(amazonShot.getDocument().select("span.a-size-large.a-color-price.olpOfferPrice.a-text-bold"));
		amazonShot.getStatistics();
		
	}
	
	public static void getAllClassifiedsPriceStatistics(String searchTerm){
		
	}
	
	public static void getPriceGrabberPriceStatistics(String searchTerm){
		//http://www.pricegrabber.com/dildo-products/?form_keyword=Dildo
		
			Shot priceGrabberShot = new Shot(
				"PRICE GRABBER",
				null,
				"http://www.pricegrabber.com/dildo-products/?form_keyword=" +searchTerm +"&layout=list");				
				
			priceGrabberShot.setElements(priceGrabberShot.getDocument().select("p a"));			
			
	}
	
				
	public static void getGumtreePriceStatistics(String searchTerm) {
		
		Shot gumtreeShot = new Shot(
				"GUMTREE",
				null,
				"http://www.gumtree.com.au/s-" + searchTerm + "/k0?sort=price_asc");
			
			gumtreeShot.setElements(gumtreeShot.getDocument().select("#left_container > div.eq(1) > table > tbody > tr:eq(1) > td.eq(4)"));					
			gumtreeShot.getStatistics();			

	}

	
	
	public static void getEbayPriceStatistics(String searchTerm) {

		Shot ebayShot = new Shot(
			"EBAY",
			null,
			"http://www.ebay.com.au/sch/i.html?_sacat=0&_from=R40&_nkw=" + 
			searchTerm + "&rt=nc&_dmd=1");
			
		ebayShot.setElements(ebayShot.getDocument().getElementsByClass("g-b"));			
		ebayShot.getStatistics();

	}


}
