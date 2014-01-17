package org.fuzzyanalysis.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Shot {

	private String _name;
	private Elements _elements;
	private String _url;
	private Document _doc;
	
	public Shot(){}
	
	public Shot(String name, Elements elements, String url){
		this._name = name;
		this._elements = elements;
		this._url = url;	
		createDocument();
	}
	
	public void createDocument(){
		
		try {
			this._doc =	Jsoup.connect(this._url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setElements(Elements elements){
		this._elements = elements;
	}
	
	public Elements getElements(){
		return this._elements;
	}
	
	public Document getDocument(){
		return this._doc;
	}
		
	public void getStatistics(){

		float totalPrices = 0;
		float lowestPrice = -1;
		float highestPrice = -1;
		int numberOfItems = this._elements.size();
		List<Float> priceList = new ArrayList<Float>();
		
		for(Element price: this._elements){
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
		
		System.out.println(this._name);
		System.out.println("================================");
		System.out.println("Highest price: " + highestPrice);
		System.out.println("Lowest price: " + lowestPrice);
		System.out.println("Average price: " + mean);
		System.out.println("Standard Deviation: " + standardDeviation);
		System.out.println("Population Standard Deviation: " + populationStandardDeviation);
		System.out.println("================================");
	}
	
	
}

